package com.github.mvc.processor.impl;

import com.github.annotation.component.Controller;
import com.github.context.impl.AnnotationConfigApplicationContext;
import com.github.mvc.RequestProcessorChain;
import com.github.mvc.annotation.RequestMapping;
import com.github.mvc.annotation.RequestParam;
import com.github.mvc.annotation.ResponseBody;
import com.github.mvc.processor.RequestProcessor;
import com.github.mvc.render.ResultRender;
import com.github.mvc.render.impl.JsonResultRender;
import com.github.mvc.render.impl.ResourceNotFoundRender;
import com.github.mvc.render.impl.ViewResultRender;
import com.github.mvc.type.ControllerMethod;
import com.github.mvc.type.RequestPathInfo;
import com.github.mvc.utils.ConvertUtils;
import com.github.utils.CustomStringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author hangs.zhang
 * @date 2020/06/23 23:06
 * *****************
 * function:Controller请求处理器
 */
public class ControllerRequestRequestProcessor implements RequestProcessor {

    private final AnnotationConfigApplicationContext applicationContext;

    private final Map<RequestPathInfo, ControllerMethod> pathControllerMap = Maps.newConcurrentMap();

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ControllerRequestRequestProcessor(AnnotationConfigApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        Set<Class<?>> requestMappingClasses = this.applicationContext.getClassesByAnnotation(RequestMapping.class);
        initPathControllerMap(requestMappingClasses);
    }

    private void initPathControllerMap(Set<Class<?>> requestMappingClasses) {
        if (requestMappingClasses.isEmpty()) {
            return;
        }

        // 1. 遍历被RequestMapping标记的类
        requestMappingClasses.forEach(requestMappingClass -> {
            RequestMapping clazzRequestMapping = requestMappingClass.getAnnotation(RequestMapping.class);
            String basePath = clazzRequestMapping.value();
            if (!basePath.startsWith("/")) {
                basePath = "/" + basePath;
            }
            // 2. 遍历被RequestMapping标记的方法
            Method[] methods = requestMappingClass.getDeclaredMethods();

            for (Method method : methods) {
                RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                String secondPath = methodRequestMapping.value();
                if (!secondPath.startsWith("/")) {
                    secondPath = "/" + secondPath;
                }
                String url = basePath + secondPath;
                // 3. 解析方法参数
                Map<String, Class<?>> methodParams = Maps.newHashMap();
                Parameter[] parameters = method.getParameters();

                if (!Objects.isNull(parameters)) {
                    for (Parameter parameter : parameters) {
                        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                        // 目前暂定所有参数都需要被@RequestParam标记
                        if (Objects.isNull(requestParam)) {
                            throw new RuntimeException("not found @RequestParam");
                        }
                        methodParams.put(requestParam.value(), parameter.getType());
                    }
                }
                // 4. 封装成RequestPathInfo与ControllerMethod放置到映射表
                String httpMethod = String.valueOf(methodRequestMapping.method());
                RequestPathInfo requestPathInfo = new RequestPathInfo(httpMethod, url);
                if (pathControllerMap.containsKey(requestPathInfo)) {
                    throw new RuntimeException("duplicate url");
                }

                Controller controller = requestMappingClass.getAnnotation(Controller.class);
                String beanName = StringUtils.isNotBlank(controller.value()) ? controller.value() : CustomStringUtils.toLowerCaseFirstOne(requestMappingClass.getSimpleName());
                ControllerMethod controllerMethod = new ControllerMethod(beanName, requestMappingClass, method, methodParams);
                pathControllerMap.put(requestPathInfo, controllerMethod);
            }
        });
    }

    @Override
    public boolean processor(RequestProcessorChain requestProcessorChain) {
        String requestMethod = requestProcessorChain.getRequestMethod();
        String requestPath = requestProcessorChain.getRequestPath();
        ControllerMethod controllerMethod = pathControllerMap.get(new RequestPathInfo(requestMethod, requestPath));
        if (Objects.isNull(controllerMethod)) {
            requestProcessorChain.setResultRender(new ResourceNotFoundRender(requestMethod, requestPath));
            return false;
        }
        // 解析请求参数,传递给ControllerMethod去执行
        Object result = invokeControllerMethod(controllerMethod, requestProcessorChain.getRequest());
        // render渲染
        setResultRender(result, controllerMethod, requestProcessorChain);
        return true;
    }

    private void setResultRender(Object result, ControllerMethod controllerMethod, RequestProcessorChain requestProcessorChain) {
        if (!Objects.isNull(result)) {
            Method invokeMethod = controllerMethod.getInvokeMethod();
            ResultRender resultRender;
            if (invokeMethod.isAnnotationPresent(ResponseBody.class)) {
                resultRender = new JsonResultRender(result);
            } else {
                resultRender = new ViewResultRender(result);
            }
            requestProcessorChain.setResultRender(resultRender);
        }
    }

    private Object invokeControllerMethod(ControllerMethod controllerMethod, HttpServletRequest request) {
        Map<String, String> requestParamMap = Maps.newHashMap();
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k, v) -> {
            // 只支持一个参数对应一个值的情况
            requestParamMap.put(k, v[0]);
        });

        List<Object> params = Lists.newArrayList();
        Map<String, Class<?>> methodParameters = controllerMethod.getMethodParameters();
        methodParameters.forEach((paramName, type) -> {
            String requestValue = requestParamMap.get(paramName);
            Object value;
            // 只支持String与基础类型
            if (StringUtils.isBlank(requestValue)) {
                // 转换成类型对应的控制
                value = ConvertUtils.primitiveNull(type);
            } else {
                value = ConvertUtils.convert(type, requestValue);
            }
            params.add(value);
        });

        Object bean = applicationContext.getBean(controllerMethod.getBeanName());
        Method invokeMethod = controllerMethod.getInvokeMethod();
        invokeMethod.setAccessible(true);
        Object result;
        try {
            if (params.isEmpty()) {
                result = invokeMethod.invoke(bean);
            } else {
                result = invokeMethod.invoke(bean, params.toArray());
            }
        } catch (Exception e) {
            LOGGER.info("invoke controller method error", e);
            throw new RuntimeException(e);
        }
        return result;
    }

}

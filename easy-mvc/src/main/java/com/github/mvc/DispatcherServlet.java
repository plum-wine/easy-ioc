package com.github.mvc;

import com.github.core.BeanContainer;
import com.github.core.DependencyInjector;
import com.github.mvc.processor.RequestProcessor;
import com.github.mvc.processor.impl.ControllerRequestRequestProcessor;
import com.github.mvc.processor.impl.JspRequestProcessor;
import com.github.mvc.processor.impl.PreRequestProcessor;
import com.github.mvc.processor.impl.StaticResourceRequestProcessor;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * @author hangs.zhang
 * @date 2020/06/18 23:46
 * *****************
 * function:
 */
@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {

    private final List<RequestProcessor> PROCESSORS = Lists.newArrayList();

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void init() {
        BeanContainer instance = BeanContainer.getInstance();
        instance.loadBeans("com.github.test");
        new DependencyInjector().doIoc();

        // 初始化请求处理器责任链
        PROCESSORS.add(new PreRequestProcessor());
        PROCESSORS.add(new StaticResourceRequestProcessor());
        PROCESSORS.add(new JspRequestProcessor());
        PROCESSORS.add(new ControllerRequestRequestProcessor());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        RequestProcessorChain requestProcessorChain = new RequestProcessorChain(PROCESSORS.iterator(), req, resp);
        // 对请求进行处理
        requestProcessorChain.doRequestProcessorChain();
        // 对结果进行渲染
        requestProcessorChain.doRender();
    }

}

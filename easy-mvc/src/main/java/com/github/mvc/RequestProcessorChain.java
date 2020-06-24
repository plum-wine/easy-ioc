package com.github.mvc;

import com.github.mvc.processor.RequestProcessor;
import com.github.mvc.render.ResultRender;
import com.github.mvc.render.impl.DefaultRender;
import com.github.mvc.render.impl.InternalErrorResultRender;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author hangs.zhang
 * @date 2020/06/23 23:02
 * *****************
 * function:
 */
@Data
public class RequestProcessorChain {

    private Iterator<RequestProcessor> iterator;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String requestMethod;
    private String requestPath;
    private int responseCode;
    // 请求结果渲染器
    private ResultRender resultRender;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public RequestProcessorChain(Iterator<RequestProcessor> iterator, HttpServletRequest req, HttpServletResponse resp) {
        this.iterator = iterator;
        this.request = req;
        this.response = resp;
        this.requestMethod = request.getMethod();
        this.requestPath = request.getPathInfo();
        this.responseCode = HttpServletResponse.SC_OK;
    }

    public void doRequestProcessorChain() {
        // 1. 通过迭代器遍历注册的处理器
        try {
            while (iterator.hasNext()) {
                RequestProcessor requestProcessor = iterator.next();
                // 2. 直到某个处理器执行返回false
                if (!requestProcessor.processor(this)) {
                    break;
                }
            }
        } catch (Exception e) {
            // 3. 期间如果出现异常, 交给内部异常渲染器处理
            this.resultRender = new InternalErrorResultRender();
            LOGGER.error("doRequestProcessorChain error", e);
        }
    }

    public void doRender() {
        if (Objects.isNull(resultRender)) {
            resultRender = new DefaultRender();
        }
        try {
            resultRender.render(this);
        } catch (Exception e) {
            LOGGER.error("doRender error", e);
            throw new RuntimeException(e);
        }
    }
}

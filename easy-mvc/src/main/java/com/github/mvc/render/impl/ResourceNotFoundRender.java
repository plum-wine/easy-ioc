package com.github.mvc.render.impl;

import com.github.mvc.RequestProcessorChain;
import com.github.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;

/**
 * @author hangs.zhang
 * @date 2020/06/23 23:24
 * *****************
 * function:
 */
public class ResourceNotFoundRender implements ResultRender {

    private final String requestMethod;

    private final String requestPath;

    public ResourceNotFoundRender(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND, "获取不到对应的资源,请求路径:" + requestPath + "请求方法:" + requestMethod);
    }
}

package com.github.mvc.render.impl;

import com.github.mvc.RequestProcessorChain;
import com.github.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;

/**
 * @author hangs.zhang
 * @date 2020/06/23 23:23
 * *****************
 * function:
 */
public class InternalErrorResultRender implements ResultRender {

    private final String errorMessage;

    public InternalErrorResultRender(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        HttpServletResponse response = requestProcessorChain.getResponse();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorMessage);
    }
}

package com.github.mvc.render.impl;

import com.github.mvc.RequestProcessorChain;
import com.github.mvc.render.ResultRender;
import com.github.mvc.utils.JsonUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author hangs.zhang
 * @date 2020/06/23 23:22
 * *****************
 * function:
 */
public class JsonResultRender implements ResultRender {

    private final Object result;

    public JsonResultRender(Object result) {
        this.result = result;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        HttpServletResponse response = requestProcessorChain.getResponse();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JsonUtils.toJson(result));
        writer.flush();
    }
}

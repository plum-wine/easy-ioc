package com.github.mvc.render.impl;

import com.github.mvc.RequestProcessorChain;
import com.github.mvc.render.ResultRender;

/**
 * @author hangs.zhang
 * @date 2020/06/23 23:22
 * *****************
 * function: 默认渲染器
 */
public class DefaultRender implements ResultRender {

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        // 设置响应码
        requestProcessorChain.getResponse().setStatus(requestProcessorChain.getResponseCode());
    }

}

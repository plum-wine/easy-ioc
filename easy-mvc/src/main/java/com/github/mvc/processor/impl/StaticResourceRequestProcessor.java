package com.github.mvc.processor.impl;

import com.github.mvc.RequestProcessorChain;
import com.github.mvc.processor.RequestProcessor;

/**
 * @author hangs.zhang
 * @date 2020/06/23 23:05
 * *****************
 * function: jsp页面请求处理
 */
public class StaticResourceRequestProcessor implements RequestProcessor {

    @Override
    public boolean processor(RequestProcessorChain requestProcessorChain) {
        return true;
    }

}

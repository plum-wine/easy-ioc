package com.github.mvc.processor.impl;

import com.github.mvc.RequestProcessorChain;
import com.github.mvc.processor.RequestProcessor;

/**
 * @author hangs.zhang
 * @date 2020/06/23 23:04
 * *****************
 * function:
 * 请求预处理,包括编码与路径处理
 */
public class PreRequestProcessor implements RequestProcessor {

    @Override
    public boolean processor(RequestProcessorChain requestProcessorChain) {
        return true;
    }

}

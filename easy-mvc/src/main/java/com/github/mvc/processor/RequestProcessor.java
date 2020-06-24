package com.github.mvc.processor;

import com.github.mvc.RequestProcessorChain;

/**
 * @author hangs.zhang
 * @date 2020/06/23 23:02
 * *****************
 * function:
 */
public interface RequestProcessor {

    boolean processor(RequestProcessorChain requestProcessorChain) throws Exception;

}

package com.github.mvc.render;

import com.github.mvc.RequestProcessorChain;

/**
 * @author hangs.zhang
 * @date 2020/06/23 23:17
 * *****************
 * function:
 */
public interface ResultRender {

    void render(RequestProcessorChain requestProcessorChain) throws Exception;

}

package com.github.demo;

import com.github.annotation.Service;

/**
 * @author hangs.zhang
 * @date 2020/06/20 23:05
 * *****************
 * function:
 */
@Service
public class FoobarService {

    public String hello(String message) {
        return "foobar service do hello:" + message;
    }

}

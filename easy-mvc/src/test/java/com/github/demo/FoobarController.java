package com.github.demo;

import com.github.annotation.Controller;
import com.github.annotation.inject.Autowired;

/**
 * @author hangs.zhang
 * @date 2020/06/20 23:04
 * *****************
 * function:
 */
@Controller
public class FoobarController {

    @Autowired
    private FoobarService foobarService;

    public String hello(String message) {
        return foobarService.hello("foobar controller do hello:" + message);
    }

}

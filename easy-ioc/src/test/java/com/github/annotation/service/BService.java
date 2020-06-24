package com.github.annotation.service;

import com.github.annotation.component.Service;

/**
 * @author hangs.zhang
 * @date 2020/6/24 下午8:16
 * *********************
 * function:
 */
@Service
public class BService {

    public String doMessage(String message) {
        return "B:" + message;
    }

}

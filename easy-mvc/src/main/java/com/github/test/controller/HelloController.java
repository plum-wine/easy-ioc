package com.github.test.controller;

import com.github.annotation.Controller;
import com.github.mvc.annotation.RequestMapping;
import com.github.mvc.annotation.RequestParam;
import com.github.mvc.annotation.ResponseBody;
import com.github.test.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2020/06/25 11:23
 * *****************
 * function:
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @RequestMapping("/test1")
    public void test1() {
        LOGGER.info("test1");
    }

    @RequestMapping("/test2")
    public void test2(@RequestParam("name") String name, @RequestParam("id") Integer id) {
        LOGGER.info("test2 name:{}, id:{}", name, id);
    }

    @RequestMapping("/test3")
    public void test3() {
        throw new RuntimeException("test error");
    }

    @ResponseBody
    @RequestMapping("/test4")
    public Student test4() {
        Student student = new Student();
        student.setName("plum-wine");
        student.setId(1);
        return student;
    }

}

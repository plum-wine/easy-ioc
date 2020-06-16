package com.github.service.impl;

import com.github.service.HelloWorldService;
import com.github.service.PrintService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author plum-wine
 */
@Data
@NoArgsConstructor
public class HelloWorldServiceImpl implements HelloWorldService {

    private String text;

    private PrintService printService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void helloWorld() {
        printService.print(text);
    }

}

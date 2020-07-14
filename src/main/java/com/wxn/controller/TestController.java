package com.wxn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
public class TestController {

    @RequestMapping("/test")
    public String test(HttpServletRequest request) throws InterruptedException {
        return "test";
    }

    @ResponseBody
    @RequestMapping("/test1")
    public String test1(HttpServletRequest request) throws InterruptedException {
        while(true) {
            if(LocalDateTime.now().getMinute() % 2 == 0) {
                return ":";
            }
            Thread.sleep(500);
        }
    }
}

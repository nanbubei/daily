package com.wxn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {

    @RequestMapping("/test")
    public String test(HttpServletRequest request) {
        System.out.println("hello~");
        request.setAttribute("name", "w");
        return "test";
    }
}

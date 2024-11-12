package com.example.nodebackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/test-api")
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "Test 입니다.";
    }


    @GetMapping("/test2")
    public String test2(){
        return "a";

    }


}

package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //데이터를 반환하는 놈
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello(){
        return "hello world"; //mustache(view)로 안가는 그냥 데이터 자체
    }


}

package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class FirstController {
    // 어떤 요청 받고 처리(리턴) greetings로 가라
    @GetMapping("/hi") //

    // get방식으로 hi요청을 받아라, 받으면 메소드로 가서 greetings.mustache 실행

    public String nicetomeetyou(Model model){
        model.addAttribute("username","둘리");
        return "greetings"; // greetings.mustache 실행
    }
    @GetMapping("/bye")
    public String seeyounext(Model model){
        model.addAttribute("username","홍길동");
        return "goodbye"; //goodbye.mustache
    }
}

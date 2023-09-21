package com.example.demo.controller;

import com.example.demo.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DemoController {
//    private DemoService demoService;
//
//// Service 객체를 매개변수로 하는 생성자
//    @Autowired
//    public  DemoController(DemoService demoService){
//        this.demoService = demoService;
//    }

    private final DemoService demoService;


}

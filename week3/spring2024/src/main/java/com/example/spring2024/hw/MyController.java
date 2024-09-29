package com.example.spring2024.hw;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyController {

    @Autowired
    private MyService myService;

    public void controllerMethod() {
        System.out.println("controller");
        myService.serviceMethod();
    }
}

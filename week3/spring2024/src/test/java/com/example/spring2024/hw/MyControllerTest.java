package com.example.spring2024.hw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@SpringBootTest
public class MyControllerTest {
    @Autowired
    private MyController myController;
    @Test
    public void controllerTest() {
        myController.controllerMethod();
    }

}

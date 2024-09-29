package com.example.spring2024.hw;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyService {
    @Autowired
    private MyRepository myRepository;

    public void serviceMethod() {
        System.out.println("service");
        myRepository.repositoryMethod();
    }
}

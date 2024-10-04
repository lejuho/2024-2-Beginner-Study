package com.example.spring2024.hw;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRepository {
    public void repositoryMethod(){
        System.out.println("repository");
    }
}

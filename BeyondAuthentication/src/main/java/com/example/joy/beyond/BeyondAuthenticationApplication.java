package com.example.joy.beyond;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = { "com.example.joy" })
public class BeyondAuthenticationApplication{
    @Autowired

    public static void main(String[] args) {
        SpringApplication.run(BeyondAuthenticationApplication.class, args);
    }
    
    
}

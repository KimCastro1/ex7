package com.castro.ex7.security;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JustTest {

    @Value("${org.castro.jwt.secretkey}")
    private String key;
    @Test
    public void testJwtSecretKey(){
        System.out.println(key);
    }
}

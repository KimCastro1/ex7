package com.castro.ex7.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void testEncode(){
        String password = "1111";
        String encodedPw = passwordEncoder.encode(password);
        System.out.println("Encoded Password: "+encodedPw);
        boolean matchResult = passwordEncoder.matches(password,encodedPw);
        System.out.println("Match Result: "+matchResult);
    }
}

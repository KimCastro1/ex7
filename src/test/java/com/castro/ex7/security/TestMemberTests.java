package com.castro.ex7.security;

import com.castro.ex7.entity.TestMember;
import com.castro.ex7.entity.TestMemberRole;
import com.castro.ex7.repositroy.TestMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class TestMemberTests {

    @Autowired
    private TestMemberRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies(){

        IntStream.rangeClosed(1,100).forEach(i->{
            TestMember member = TestMember.builder()
                    .email("user"+i+"@gmail.com")
                    .name("user"+i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();
            member.addMemberRole(TestMemberRole.USER);
            if(i>80){
                member.addMemberRole(TestMemberRole.MANAGER);
            }
            if(i>90){
                member.addMemberRole(TestMemberRole.ADMIN);
            }
            repository.save(member);
        });
    }
    @Test
    public void testRead(){
        Optional<TestMember> result = repository.findByEmail(false, "user95@gmail.com");
        if(result.isPresent()){
            TestMember testMember = result.get();
            System.out.println(testMember);
        }
    }
}

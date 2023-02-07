package com.castro.ex7.security.service;

import com.castro.ex7.entity.TestMember;
import com.castro.ex7.repositroy.TestMemberRepository;
import com.castro.ex7.security.dto.TestAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class TestUserDetailsService implements UserDetailsService {

    private final TestMemberRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("TestUserDetailsService loadUserByUsername "+username);

        Optional<TestMember> result = repository.findByEmail(false, username);
        if(!result.isPresent()){
            throw new UsernameNotFoundException("Check Email or Social");
        }
        TestMember testMember = result.get();
        log.info(testMember);
        TestAuthMemberDTO testAuthMemberDTO = new TestAuthMemberDTO(
                testMember.getEmail(),
                testMember.getPassword(),
                testMember.isFromSocial(),
                testMember.getRoleSet().stream().map(role->new SimpleGrantedAuthority(
                        "ROLE_"+role.name())).collect(Collectors.toList())
        );
        testAuthMemberDTO.setName(testMember.getName());
        testAuthMemberDTO.setFromSocial(testMember.isFromSocial());

        return testAuthMemberDTO;
    }
}

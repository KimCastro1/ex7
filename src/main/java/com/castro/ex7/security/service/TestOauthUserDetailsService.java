package com.castro.ex7.security.service;

import com.castro.ex7.entity.TestMember;
import com.castro.ex7.entity.TestMemberRole;
import com.castro.ex7.repositroy.TestMemberRepository;
import com.castro.ex7.security.dto.TestAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class TestOauthUserDetailsService extends DefaultOAuth2UserService {

    private final TestMemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        log.info("----------------------------------");
        log.info("userRequest: "+userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName: "+clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("----------------------------------");
        oAuth2User.getAttributes().forEach((k,v)->{
            log.info(k+": "+v);
        });

        String email = null;
        if(clientName.equals("Google")){
            email=oAuth2User.getAttribute("email");
        }
        log.info(("EMAIL: "+email));

        TestMember member = saveSocialMember(email);

        TestAuthMemberDTO authMemberDTO = new TestAuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                true,
                member.getRoleSet().stream().map(role->
                        new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        authMemberDTO.setName(member.getName());

        return authMemberDTO;
    }

    private TestMember saveSocialMember(String email) {
        Optional<TestMember> result = repository.findByEmail(true,email);
        if(result.isPresent()){
            return result.get();
        }
        TestMember member = TestMember.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();
        member.addMemberRole(TestMemberRole.USER);
        repository.save(member);

        return member;
    }
}

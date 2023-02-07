package com.castro.ex7.security.handler;

import com.castro.ex7.security.dto.TestAuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class TestLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private PasswordEncoder passwordEncoder;
    public TestLoginSuccessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("onAuthenticationSuccess ");
        TestAuthMemberDTO authMemberDTO = (TestAuthMemberDTO) authentication.getPrincipal();
        boolean fromSocial = authMemberDTO.isFromSocial();
        boolean passwordResult = passwordEncoder.matches("1111", authMemberDTO.getPassword());
        if(fromSocial&&passwordResult){
            redirectStrategy.sendRedirect(request,response,"/member/modify?from=social");
        }

    }
}

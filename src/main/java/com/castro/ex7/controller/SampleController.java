package com.castro.ex7.controller;

import com.castro.ex7.security.dto.TestAuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {
    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public void exAll(){
        log.info(("exAll.............."));
    }
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal TestAuthMemberDTO authMemberDTO){
        log.info("exMember..............");
        log.info("----------------------");
        log.info(authMemberDTO);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin(){
        log.info("exAdmin..............");
    }

    @PreAuthorize("#testAuthMember != null && #testAuthMember.username eq \"user95@gmail.com\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal TestAuthMemberDTO authMemberDTO){

        log.info("exMemberOnly....");
        log.info(authMemberDTO);

        return "/sample/admin";
    }

}

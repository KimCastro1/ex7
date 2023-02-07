package com.castro.ex7.congif;

import com.castro.ex7.security.filter.ApiCheckFilter;
import com.castro.ex7.security.filter.ApiLoginFilter;
import com.castro.ex7.security.handler.ApiLoginFailHandler;
import com.castro.ex7.security.handler.TestLoginSuccessHandler;
import com.castro.ex7.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
//        http.authorizeRequests()
//                .antMatchers("/sample/all").permitAll()
//                .antMatchers("/sample/member").hasRole("USER");
        http.formLogin();
        http.csrf().disable(); //csrf token disable
        http.logout();
        http.oauth2Login().successHandler(successHandler());
        //http.rememberMe().tokenValiditySeconds(60*60*7).userDetailsService(userDetailsService());
        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);

    }
    @Bean
    public TestLoginSuccessHandler successHandler(){
        return new TestLoginSuccessHandler(passwordEncoder());
    }

    @Bean
    public ApiLoginFilter apiLoginFilter() throws Exception{
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login", jwtUtil());
        apiLoginFilter.setAuthenticationManager(authenticationManager());
        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());
        return apiLoginFilter;
    }

    private JWTUtil jwtUtil() {
        return new JWTUtil();
    }

    @Bean
    public ApiCheckFilter apiCheckFilter(){
        return new ApiCheckFilter("/notes/**/*", jwtUtil());
    }
}

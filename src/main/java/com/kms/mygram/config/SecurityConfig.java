package com.kms.mygram.config;

import com.kms.mygram.auth.AjaxAuthenticationEntryPoint;
import com.kms.mygram.auth.FaceBookOAuthService;
import com.kms.mygram.exception.handler.AuthFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FaceBookOAuthService faceBookOAuthService;
    private final AuthFailureHandler authFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(request ->
                        request
                                .antMatchers("/login").permitAll()
                                .antMatchers("/login-error").permitAll()
                                .antMatchers("/oauth-login-error").permitAll()
                                .antMatchers("/signup").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(login ->
                        login
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/", false)
                                .failureHandler(authFailureHandler)
                )
                .oauth2Login(auth ->
                        auth
                                .failureHandler(authFailureHandler)
                                .userInfoEndpoint()
                                .userService(faceBookOAuthService)
                )
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(ajaxAwareAuthenticationEntryPoint())
                );
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/image/**");
        web.ignoring().antMatchers("/js/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private AjaxAuthenticationEntryPoint ajaxAwareAuthenticationEntryPoint() {
        return new AjaxAuthenticationEntryPoint("/login");
    }

}

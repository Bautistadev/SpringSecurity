package com.spring.security.SpringSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.management.MXBean;
/*
* Is a spring security configuration component
* */
@Configuration
@EnableWebSecurity
public class Config {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

      /*  /* http.csrf().disable(); Disable default springSecurity security, is recomended when it is not interactuated with direct form*/
        /*Indicates, witch are the end-point that need authorization */
        http.authorizeHttpRequests().requestMatchers("/swagger-ui/**").permitAll(); /*The authentication is not necesseary in this end-point*/
        http.authorizeHttpRequests().anyRequest().authenticated();/*But, other end-pont, the authentication is necesary*/
        http.formLogin().permitAll();/*Enable form for all user*/
        return http.build();
    }
}

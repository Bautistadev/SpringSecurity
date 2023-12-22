package com.spring.security.SpringSecurity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.management.MXBean;
/*
* Is a spring security configuration component
* */
@Configuration
@EnableWebSecurity
public class Config {

   /** @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

      /*  /* http.csrf().disable(); Disable default springSecurity security, is recomended when it is not interactuated with direct form
        /*Indicates, witch are the end-point that need authorization
        http.authorizeHttpRequests().requestMatchers("/swagger-ui/**").permitAll(); /*The authentication is not necesseary in this end-point
        http.authorizeHttpRequests().anyRequest().authenticated();/*But, other end-pont, the authentication is necesary
        http.formLogin().permitAll();/*Enable form for all user
        return http.build();
    }*/

    /**
     * All endponits that have relation with /swagger-ui, the authentication it is not required
     * but, other url, the authentication is required for all users
     * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/swagger-ui/**",
                                                "/v3/api-docs").permitAll()
                .anyRequest().authenticated()
        ).formLogin()
                .successHandler(authenticationSuccessHandler("/index.html")) /** If the username and password are correct after init session, redirect to main menu*/
                .permitAll()/** All endpoints before the success login, the authentication is required */
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(logoutSuccessHandler("/login")) /**Return to login page, when the user logout*/
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)/**Create a new session, when there is not exist some other session*/
                    .invalidSessionUrl("/login") /**If the username and password are not correct, redirect the user to login */
                    .maximumSessions(1)/**Maximum number of session */
                    .expiredUrl("/login") /** When the session expired, redirect to login page*/
                    .sessionRegistry(sessionRegistry())
                .and()
                .sessionFixation()/**This is another, vulnerability of web application when it's working with session  */
                    .migrateSession()/***/
                ;

        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }


    private AuthenticationSuccessHandler authenticationSuccessHandler(String url){
        return ((request, response, authentication) -> {
            response.sendRedirect(url);
        });
    }
    private LogoutSuccessHandler logoutSuccessHandler(String url){
        return ((request, response, authentication) -> {
            response.sendRedirect(url);
        });
    }

}



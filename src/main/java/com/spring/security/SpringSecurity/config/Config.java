package com.spring.security.SpringSecurity.config;


import com.spring.security.SpringSecurity.Controller.loginController;
import com.spring.security.SpringSecurity.Service.UserDetailsServiceImplements;
import com.spring.security.SpringSecurity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
     * ENDPOINT CONFIGURATION
     * All endponits that have relation with /swagger-ui, the authentication it is not required
     * but, other url, the authentication is required for all users
     * */



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers("/swagger-ui/**","/v3/api-docs","/css/**","/js/**","/login","/logout").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(form ->{
                    form.loginPage("/login").permitAll();
                    form.successHandler(authenticationSuccessHandler("/home")).permitAll();
                })
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler("/login"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .sessionManagement(session ->{
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                    session.invalidSessionUrl("/login");
                    session.maximumSessions(1)
                            .expiredUrl("/login")
                            .sessionRegistry(sessionRegistry());
                    session.sessionFixation().migrateSession();
                })

                .httpBasic();

        return http.build();
    }


    /**
     * AUTHENTICATION CONFIGURATION
     * */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService( userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and().build();

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

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    /**
     * Password encoder class
     * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}



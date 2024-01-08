package com.spring.security.SpringSecurity.config;


import com.spring.security.SpringSecurity.CustomFilter.JwtAuthenticationFilter;
import com.spring.security.SpringSecurity.CustomFilter.JwtAuthorizationFilter;
import com.spring.security.SpringSecurity.Service.JWTService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


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
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           UserDetailsService userDetailsService,
                                           JWTService jwtService,
                                           AuthenticationManager authenticationManager,
                                           JwtAuthorizationFilter jwtAuthorizationFilter) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(userDetailsService,jwtService);

        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        http
                .csrf(config -> config.disable())
                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers("/swagger-ui/**","/v3/api-docs").permitAll();
                    auth.requestMatchers("/v1/hole").hasAuthority("ADMIN");
                    auth.requestMatchers("/v1/addUser").hasAnyAuthority("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session ->{
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);


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
        return new BCryptPasswordEncoder();
    }

}



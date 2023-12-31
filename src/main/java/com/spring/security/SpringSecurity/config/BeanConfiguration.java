package com.spring.security.SpringSecurity.config;

import com.spring.security.SpringSecurity.Controller.Controller;
import com.spring.security.SpringSecurity.Controller.homeController;
import com.spring.security.SpringSecurity.Controller.loginController;
import com.spring.security.SpringSecurity.Repository.UserRepository;
import com.spring.security.SpringSecurity.Service.UserDetailsServiceImplements;
import com.spring.security.SpringSecurity.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserService userService(UserRepository userRepository){
        return new UserService(userRepository);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService){
        return new UserDetailsServiceImplements(userService);
    }
    @Bean
    public Controller controller(){
        return  new Controller();
    }

    @Bean
    public loginController loginController(){
        return new loginController();
    }

    @Bean
    public homeController homeController(){
        return new homeController();
    }

}

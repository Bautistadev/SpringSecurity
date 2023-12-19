package com.spring.security.SpringSecurity.config;

import com.spring.security.SpringSecurity.Repository.UserRepository;
import com.spring.security.SpringSecurity.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserService userService(UserRepository userRepository){
        return new UserService(userRepository);
    }
}

package com.spring.security.SpringSecurity;

import com.spring.security.SpringSecurity.Entity.User;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.spring.security.SpringSecurity"})
public class MainApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ap = SpringApplication.run(MainApplication.class, args);
	}

}

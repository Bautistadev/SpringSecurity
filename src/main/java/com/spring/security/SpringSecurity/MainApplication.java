package com.spring.security.SpringSecurity;

import com.spring.security.SpringSecurity.Entity.Rol;
import com.spring.security.SpringSecurity.Entity.User;
import com.spring.security.SpringSecurity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.spring.security.SpringSecurity"})
public class MainApplication implements CommandLineRunner {

	public static void main(String[] args) {
		ConfigurableApplicationContext ap = SpringApplication.run(MainApplication.class, args);
	}

	@Autowired
	private ApplicationContext appContext;
	@Override
	public void run(String... args) throws Exception {

		UserService defaultUser = appContext.getBean(UserService.class);

		User user = User.builder()
				.id(1)
				.username("admin")
				.password("admin")
				.rol(Rol.ADMIN)
				.build();

		if(defaultUser.existsById(1) == false)
			System.out.println(defaultUser.save(user));

	}
}

package com.spring.security.SpringSecurity;

import com.spring.security.SpringSecurity.Entity.Rol;
import com.spring.security.SpringSecurity.Entity.User;
import com.spring.security.SpringSecurity.Service.UserService;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.spring.security.SpringSecurity"})
public class MainApplication implements CommandLineRunner {

	public static void main(String[] args) {
		ConfigurableApplicationContext ap = SpringApplication.run(MainApplication.class, args);
	}


	@Autowired
	private ApplicationContext appContext;

	/** Properties data, for the default user*/
	@Value("${api.default.username}")
	private String username;
	@Value("${api.default.password}")
	private String password;

	@Override
	public void run(String... args) throws Exception {

		UserService defaultUser = appContext.getBean(UserService.class);

		User user = User.builder()
				.username(username)
				.password(new BCryptPasswordEncoder().encode(password))
				.rol(Rol.ADMIN)
				.build();

		if(defaultUser.findUserByUserName(user.getUsername()) == null)
			System.out.println(defaultUser.save(user));

	}
}

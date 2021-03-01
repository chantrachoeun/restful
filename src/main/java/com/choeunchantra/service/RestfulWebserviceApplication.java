package com.choeunchantra.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.choeunchantra.service.security.AppProperties;


@SpringBootApplication
public class RestfulWebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebserviceApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Not used bean because SpringApplicationContext class already annotated with @Component
	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}

//	@Bean
//	public AppProperties appProperties() {
//		return new AppProperties();
//	}
}

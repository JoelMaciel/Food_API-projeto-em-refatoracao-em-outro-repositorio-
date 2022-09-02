package com.joel.food;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.joel.food.core.io.Base64ProtocolResolver;
import com.joel.food.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)

public class FoodApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
		var app = new SpringApplication(FoodApiApplication.class);
		app.addListeners(new Base64ProtocolResolver());
		app.run(args);
		
		//SpringApplication.run(FoodApiApplication.class, args);
	}

}

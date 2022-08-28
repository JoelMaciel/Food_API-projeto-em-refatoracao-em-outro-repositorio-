package com.joel.food.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;

@Configuration
public class FoodHalConfiguration {

	@Bean
	public HalConfiguration globalPolicy() {
		return new HalConfiguration()
				.withMediaType(MediaType.APPLICATION_JSON)
				.withMediaType(FoodMediaTypes.V1_APPLICATION_JSON)
				.withMediaType(FoodMediaTypes.V2_APPLICATION_JSON);
		
	}
	
}
package com.joel.food.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;


@Validated
@Component
@Getter
@Setter
@ConfigurationProperties("food.email")
public class EmailProperties {

	@NotNull
	private String rementente;
}

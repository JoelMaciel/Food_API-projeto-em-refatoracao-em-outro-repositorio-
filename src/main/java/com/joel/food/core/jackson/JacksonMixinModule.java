package com.joel.food.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.joel.food.api.model.mixin.CidadeMixin;
import com.joel.food.api.model.mixin.CozinhaMixin;
import com.joel.food.api.model.mixin.RestauranteMixin;
import com.joel.food.domain.model.Cidade;
import com.joel.food.domain.model.Cozinha;
import com.joel.food.domain.model.Restaurante;

@Component
public class JacksonMixinModule extends SimpleModule {
	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}
}

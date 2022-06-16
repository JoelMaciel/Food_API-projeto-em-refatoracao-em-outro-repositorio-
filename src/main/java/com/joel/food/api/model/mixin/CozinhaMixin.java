package com.joel.food.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joel.food.domain.model.Restaurante;

public  abstract class CozinhaMixin {
	
	@JsonIgnore
	private List<Restaurante> restaurantes = new ArrayList<>();

}

package com.joel.food.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.joel.food.api.model.RestauranteModel;
import com.joel.food.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(Restaurante.class	, RestauranteModel.class)
		.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setTaxaFrete);
		return modelMapper;
	}

}

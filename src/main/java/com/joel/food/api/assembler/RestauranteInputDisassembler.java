package com.joel.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joel.food.api.model.input.RestauranteInput;
import com.joel.food.domain.model.Cozinha;
import com.joel.food.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteInput restauranteInput) {

		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInput restauranteInput , Restaurante restaurante) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of
		//com.joel.food.domain.model.Cozinha was altered from 1 to 2
		restaurante.setCozinha(new Cozinha());
		
		modelMapper.map(restauranteInput,  restaurante);
	}

}

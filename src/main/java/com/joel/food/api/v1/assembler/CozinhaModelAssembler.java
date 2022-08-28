package com.joel.food.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.joel.food.api.v1.FoodLinks;
import com.joel.food.api.v1.controller.CozinhaController;
import com.joel.food.api.v1.model.CozinhaModel;
import com.joel.food.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler  extends 
 RepresentationModelAssemblerSupport<Cozinha, CozinhaModel>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FoodLinks foodLinks;
	
	public CozinhaModelAssembler() {
		super(CozinhaController.class, CozinhaModel.class);
		
	}

	@Override
	public CozinhaModel toModel(Cozinha cozinha) {
		
		CozinhaModel cozinhaModel = createModelWithId(cozinha.getId() , cozinha);
		modelMapper.map(cozinha, cozinhaModel);
		
		cozinhaModel.add(foodLinks.linkToCozinhas("cozinhas"));
		
		return cozinhaModel;
	}
	
	
	
}






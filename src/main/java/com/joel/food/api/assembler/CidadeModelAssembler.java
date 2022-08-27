package com.joel.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.joel.food.api.FoodLinks;
import com.joel.food.api.controller.CidadeController;
import com.joel.food.api.model.CidadeModel;
import com.joel.food.domain.model.Cidade;

@Component
public class CidadeModelAssembler  extends 
RepresentationModelAssemblerSupport<Cidade, CidadeModel>{

	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
		
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FoodLinks foodLinks;
	
	public CidadeModel toModel(Cidade cidade) {
		CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
		
		modelMapper.map(cidade, cidadeModel);
		
		cidadeModel.add(foodLinks.linkToCidades("cidades"));
		
		cidadeModel.getEstado().add(foodLinks.linkToEstado(cidadeModel.getEstado().getId()));
		
		return cidadeModel;
	}
	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				 .add(foodLinks.linkToCidades());

	}
	
}




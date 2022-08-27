package com.joel.food.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.joel.food.api.model.CidadeModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CidadeModel")
public class CidadesModelOpenApi {
	
	private CidadeEmbeddedModelOpneApi _embedded;
	private Links _links;
	
	@ApiModel("CidadesEmbeddedModel")
	@Data
	public class CidadeEmbeddedModelOpneApi {
		
		private List<CidadeModel> cidades;
	}

}

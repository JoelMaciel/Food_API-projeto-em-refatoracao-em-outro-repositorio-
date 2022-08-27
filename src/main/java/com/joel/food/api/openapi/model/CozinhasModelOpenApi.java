package com.joel.food.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.joel.food.api.model.CozinhaModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi  {
	
	private CozinhasEmbeddedModelOpneApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	@ApiModel("CozinhasEmbeddedModel")
	@Data
	public class CozinhasEmbeddedModelOpneApi {
		
		private List<CozinhaModel> cozinhas;
	}

	
	
	

}











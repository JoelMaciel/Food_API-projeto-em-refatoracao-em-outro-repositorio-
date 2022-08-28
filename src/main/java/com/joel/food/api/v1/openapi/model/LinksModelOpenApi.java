package com.joel.food.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("links")
public class LinksModelOpenApi {
	
	private LinkModel rel;
	
	@Getter
	@Setter
	@ApiModel("Link")
	private class LinkModel {
		
		private String hrel;
		private boolean templated;
	}

}

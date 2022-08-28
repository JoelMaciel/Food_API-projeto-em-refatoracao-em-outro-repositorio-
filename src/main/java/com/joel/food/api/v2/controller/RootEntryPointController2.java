package com.joel.food.api.v2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joel.food.api.v2.FoodLinksV2;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(path = "/v2" , produces = MediaType.APPLICATION_JSON_VALUE )
public class RootEntryPointController2 {
	
	@Autowired
	private FoodLinksV2 foodLinks;
	
	@GetMapping
	public  RootEntryPointModel root () {
		var rootEntryPointModel = new RootEntryPointModel();
		
		rootEntryPointModel.add(foodLinks.linkToCozinhas("cozinhas"));
		rootEntryPointModel.add(foodLinks.linkToCidades("cidades"));
		
		return rootEntryPointModel;
	}
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel>{
		
	}

}

package com.joel.food.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joel.food.api.v1.FoodLinks;

@RestController
@RequestMapping(path = "/v1" , produces = MediaType.APPLICATION_JSON_VALUE )
public class RootEntryPointController {
	
	@Autowired
	private FoodLinks foodLinks;
	
	@GetMapping
	public  RootEntryPointModel root () {
		var rootEntryPointModel = new RootEntryPointModel();
		
		rootEntryPointModel.add(foodLinks.linkToCozinhas("cozinhas"));
		rootEntryPointModel.add(foodLinks.linkToCozinhas("gastronomias"));
		rootEntryPointModel.add(foodLinks.linkToPedidos("pedidos"));
		rootEntryPointModel.add(foodLinks.linkToRestaurantes("restaurantes"));
		rootEntryPointModel.add(foodLinks.linkToGrupos("grupos"));
		rootEntryPointModel.add(foodLinks.linkToUsuarios("usuarios"));
		rootEntryPointModel.add(foodLinks.linkToPermissoes("permissoes"));
		rootEntryPointModel.add(foodLinks.linkToFormasPagamento("formas-pagamento"));
		rootEntryPointModel.add(foodLinks.linkToEstados("estados"));
		rootEntryPointModel.add(foodLinks.linkToCidades("cidades"));
		rootEntryPointModel.add(foodLinks.linkToEstatisticas("estatisticas"));
		
		
		return rootEntryPointModel;
	}
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel>{
		
	}

}

package com.joel.food.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.joel.food.api.FoodLinks;
import com.joel.food.api.assembler.UsuarioModelAssembler;
import com.joel.food.api.model.UsuarioModel;
import com.joel.food.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.joel.food.domain.model.Restaurante;
import com.joel.food.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis" , produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private FoodLinks foodLinks;
	
	public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
	    Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
	    
	    CollectionModel<UsuarioModel> usuariosModel = usuarioModelAssembler
	            .toCollectionModel(restaurante.getResponsaveis())
	                .removeLinks()
	                .add(foodLinks.linkToRestauranteResponsaveis(restauranteId))
	                .add(foodLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

	    usuariosModel.getContent().stream().forEach(usuarioModel -> {
	        usuarioModel.add(foodLinks.linkToRestauranteResponsavelDesassociacao(
	                restauranteId, usuarioModel.getId(), "desassociar"));
	    });
	    
	    return usuariosModel;
	}
	
	@Override
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
	    cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
	    
	    return ResponseEntity.noContent().build();
	}
	
	@Override
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
	    cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
	    
	    return ResponseEntity.noContent().build();
	}  

}




















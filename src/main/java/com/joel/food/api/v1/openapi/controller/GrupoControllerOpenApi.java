package com.joel.food.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.joel.food.api.exceptionhandler.Problem;
import com.joel.food.api.model.input.GrupoInput;
import com.joel.food.api.v1.model.GrupoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Lista os grupos")
	CollectionModel<GrupoModel> listar();

	@ApiOperation("Busca uma grupo por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do grupo é inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	 GrupoModel buscar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);

	
	@ApiOperation("Cadastra um grupo")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Grupo cadastrado", 
			content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = Problem.class))), })
	 GrupoModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) GrupoInput grupoInput);

	
	
	@ApiOperation("Atualiza um grupo por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Grupo atualizado",
			content = @Content(mediaType = "application/json",
			schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado",
			content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = Problem.class))) })
	 GrupoModel atualizar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId,
           @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados") GrupoInput grupoInput);

	
	
	@ApiOperation("Exclui um grupo por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Grupo excluido",
			content = @Content(mediaType = "application/json",
			schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado",
			content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = Problem.class))) })
	 void remover(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);

}

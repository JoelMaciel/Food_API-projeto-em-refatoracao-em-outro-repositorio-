package com.joel.food.api.openapi.controller;

import java.util.List;

import com.joel.food.api.exceptionhandler.Problem;
import com.joel.food.api.model.GrupoModel;
import com.joel.food.api.model.input.GrupoInput;

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
	public List<GrupoModel> listar();

	@ApiOperation("Busca uma grupo por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do grupo é inválido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	public GrupoModel buscar(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);

	
	@ApiOperation("Cadastra um grupo")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Grupo cadastrado", 
			content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = Problem.class))), })
	public GrupoModel adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo grupo") GrupoInput grupoInput);

	
	
	@ApiOperation("Atualiza um grupo por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Grupo atualizado",
			content = @Content(mediaType = "application/json",
			schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado",
			content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = Problem.class))) })
	public GrupoModel atualizar(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId,
           @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados") GrupoInput grupoInput);

	
	
	@ApiOperation("Exclui um grupo por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Grupo excluido",
			content = @Content(mediaType = "application/json",
			schema = @Schema(implementation = Problem.class))),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado",
			content = @Content(mediaType = "application/json", 
			schema = @Schema(implementation = Problem.class))) })
	public void remover(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);

}
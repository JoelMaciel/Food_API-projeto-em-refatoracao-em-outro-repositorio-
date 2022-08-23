package com.joel.food.api.openapi.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.joel.food.api.exceptionhandler.Problem;
import com.joel.food.api.model.RestauranteModel;
import com.joel.food.api.model.input.RestauranteInput;
import com.joel.food.api.model.view.RestauranteView;
import com.joel.food.api.openapi.model.RestauranteBasicoModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
    @ApiImplicitParams({
        @ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
                name = "projecao", paramType = "query", type = "string")
    })
    @JsonView(RestauranteView.Resumo.class)
    List<RestauranteModel> listar();
    
    @ApiOperation(value = "Lista restaurantes", hidden = true)
     List<RestauranteModel> listarApenasNomes();
    
    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "400", description  = "ID do restaurante inválido",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
        @ApiResponse(responseCode = "404",description = "Restaurante não encontrado",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
     RestauranteModel buscar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
    
    @ApiOperation("Cadastra um restaurante")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Restaurante cadastrado" ,
        		content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
    })
     RestauranteModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true)
            RestauranteInput restauranteInput);
    
    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
        @ApiResponse(responseCode = "404" , description = "Restaurante não encontrado",
        		content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    RestauranteModel atualizar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,
            
            @ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados", 
                required = true)
            RestauranteInput restauranteInput);
    
    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
     void ativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
    
    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
        		content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
     void inativar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
    
    @ApiOperation("Ativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
    })
     void ativarMultiplos(
            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
            List<Long> restauranteIds);
    
    @ApiOperation("Inativa múltiplos restaurantes")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")
    })
    void inativarMultiplos(
            @ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
            List<Long> restauranteIds);

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
        		content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
     void abrir(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);
    
    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
        @ApiResponse(responseCode = "404", description ="Restaurante não encontrado",
       content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
     void fechar(
            @ApiParam(value = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);

}
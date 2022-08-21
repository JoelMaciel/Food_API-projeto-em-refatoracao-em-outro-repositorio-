package com.joel.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.joel.food.api.assembler.CidadeInputDisassembler;
import com.joel.food.api.assembler.CidadeModelAssembler;
import com.joel.food.api.exceptionhandler.Problem;
import com.joel.food.api.model.CidadeModel;
import com.joel.food.api.model.input.CidadeInput;
import com.joel.food.domain.exception.EstadoNaoEncontradoException;
import com.joel.food.domain.exception.NegocioException;
import com.joel.food.domain.model.Cidade;
import com.joel.food.domain.repository.CidadeRepository;
import com.joel.food.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cidades")
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeRepository cidadeRepository;

	@ApiOperation("Lista as cidades")
	@GetMapping
	public List<CidadeModel> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();

		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
				content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "400", description = "ID da cidade é inválido", 
		content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))
	})
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(
			@ApiParam("ID de uma cidade")
			@PathVariable Long cidadeId) {

		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

		return cidadeModelAssembler.toModel(cidade);
	}

	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({
		@ApiResponse(responseCode  = "201", description  = "Cidade cadastrada")
	})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(
			@ApiParam(name = "corpo", value = "Representacao de uma nova cidade")
	        @RequestBody @Valid CidadeInput cidadeInput) {
		try {

			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

			cidade = cadastroCidade.salvar(cidade);

			return cidadeModelAssembler.toModel(cidade);

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cidade atualizada", 
				content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
		content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))
	})
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable Long cidadeId , 
			@ApiParam(name = "corpo", value = "Atualizacão de uma cidade com os novos dados")
	        @RequestBody @Valid CidadeInput cidadeInput) {

		try {
		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

		cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

        cidadeAtual = cadastroCidade.salvar(cidadeAtual);
        
        return cidadeModelAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Cidade excluída", 
				content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class))),
		@ApiResponse(responseCode = "404", description = "Cidade não encontrada", 
		content = @Content(mediaType = "application/json",  schema = @Schema(implementation = Problem.class)))
	})
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(
			@ApiParam(value = "ID de uma cidade",example = "1") 
			@PathVariable Long cidadeId) {

		cadastroCidade.excluir(cidadeId);

	}

}

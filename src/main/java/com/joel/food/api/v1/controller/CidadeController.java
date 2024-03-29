package com.joel.food.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.joel.food.api.ResourceUriHelper;
import com.joel.food.api.model.input.CidadeInput;
import com.joel.food.api.v1.assembler.CidadeInputDisassembler;
import com.joel.food.api.v1.assembler.CidadeModelAssembler;
import com.joel.food.api.v1.model.CidadeModel;
import com.joel.food.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.joel.food.core.security.CheckSecurity;
import com.joel.food.domain.exception.EstadoNaoEncontradoException;
import com.joel.food.domain.exception.NegocioException;
import com.joel.food.domain.model.Cidade;
import com.joel.food.domain.repository.CidadeRepository;
import com.joel.food.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "/v1/cidades")
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeRepository cidadeRepository;

	@CheckSecurity.Cidades.PodeConsultar
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<CidadeModel> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();

		return  cidadeModelAssembler.toCollectionModel(todasCidades);
		
	

	}

	@CheckSecurity.Cidades.PodeConsultar
	@GetMapping(path = "/{cidadeId}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeModel buscar(@PathVariable Long cidadeId) {

		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

		CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);
			
		return cidadeModel;
	}

	@CheckSecurity.Cidades.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		
		try {

			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

			cidade = cadastroCidade.salvar(cidade);

		CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);
		
		ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());
		
		
		 return cidadeModel;

		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@CheckSecurity.Cidades.PodeEditar
	@PutMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeModel atualizar(@PathVariable Long cidadeId,
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

	@CheckSecurity.Cidades.PodeEditar
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);

	}

}

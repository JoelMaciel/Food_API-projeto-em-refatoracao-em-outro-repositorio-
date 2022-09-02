package com.joel.food.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.joel.food.api.model.input.RestauranteInput;
import com.joel.food.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.joel.food.api.v1.assembler.RestauranteBasicoModelAssembler;
import com.joel.food.api.v1.assembler.RestauranteInputDisassembler;
import com.joel.food.api.v1.assembler.RestauranteModelAssembler;
import com.joel.food.api.v1.model.RestauranteApenasNomeModel;
import com.joel.food.api.v1.model.RestauranteBasicoModel;
import com.joel.food.api.v1.model.RestauranteModel;
import com.joel.food.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.joel.food.api.v1.openapi.model.RestauranteBasicoModelOpenApi;
import com.joel.food.core.security.CheckSecurity;
import com.joel.food.domain.exception.CidadeNaoEncontradaException;
import com.joel.food.domain.exception.CozinhaNaoEncontradaException;
import com.joel.food.domain.exception.NegocioException;
import com.joel.food.domain.exception.RestauranteNaoEncontradoException;
import com.joel.food.domain.model.Restaurante;
import com.joel.food.domain.repository.RestauranteRepository;
import com.joel.food.domain.service.CadastroRestauranteService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping(value = "/v1/restaurantes")
public class RestauranteController implements RestauranteControllerOpenApi {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;

	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;

	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@ApiOperation(value = "Lista Restaurantes", response = RestauranteBasicoModelOpenApi.class)
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome" ,
				name = "projeção", paramType = "query", type = "string")
	})
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<RestauranteBasicoModel> listar() {
		return restauranteBasicoModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@ApiOperation(value = "Lista restaurantes" , hidden = true)
	@GetMapping(params = "projecao=apenas-nome")
	public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
		return restauranteApenasNomeModelAssembler.
				toCollectionModel(restauranteRepository.findAll());
	}
	

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping(value = "/{restauranteId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RestauranteModel buscar(@PathVariable Long restauranteId) {

		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

		return restauranteModelAssembler.toModel(restaurante);

	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {

		try {

			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping(path = "/{restauranteId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RestauranteModel atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {
		try {

			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException  | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping(path = "/{restauranteId}/ativo", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.ativar(restauranteId);
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		cadastroRestaurante.inativar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping(path = "/ativacoes", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.ativar(restauranteIds);
			
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping(path = "/ativacoes", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
			cadastroRestaurante.inativar(restauranteIds);			
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping(path = "/{restauranteId}/abertura" , produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		cadastroRestaurante.abrir(restauranteId);
		
		return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping(path = "/{restauranteId}/fechamento", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void>fechar(@PathVariable Long restauranteId) {
		cadastroRestaurante.fehcar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}

}










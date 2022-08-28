package com.joel.food.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.joel.food.api.model.input.PedidoInput;
import com.joel.food.api.v1.assembler.PedidoInputDisassembler;
import com.joel.food.api.v1.assembler.PedidoModelAssembler;
import com.joel.food.api.v1.assembler.PedidoResumoModelAssembler;
import com.joel.food.api.v1.model.PedidoModel;
import com.joel.food.api.v1.model.PedidoResumoModel;
import com.joel.food.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.joel.food.core.data.PageWrapper;
import com.joel.food.core.data.PageableTranslator;
import com.joel.food.domain.exception.EntidadeNaoEncontradaException;
import com.joel.food.domain.exception.NegocioException;
import com.joel.food.domain.filter.PedidoFilter;
import com.joel.food.domain.model.Pedido;
import com.joel.food.domain.model.Usuario;
import com.joel.food.domain.repository.PedidoRepository;
import com.joel.food.domain.service.EmissaoPedidoService;
import com.joel.food.infrastructure.repository.spec.PedidoSpecs;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping(value = "/v1/pedidos")
public class PedidoController implements PedidoControllerOpenApi {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
				name = "campos", paramType = "query", type = "string" )
	})
	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, 
	        @PageableDefault(size = 10) Pageable pageable) {
	   Pageable pageableTraduzido = traduzirPageable(pageable);
	    
	    Page<Pedido> pedidosPage = pedidoRepository.findAll(
	            PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);
	    
	    pedidosPage = new PageWrapper<>(pedidosPage, pageable);
	    
	    return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
				name = "campos", paramType = "query", type = "string" )
	})
	@GetMapping(path = "/{codigoPedido}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		
		return pedidoModelAssembler.toModel(pedido);
	}
	
	@PostMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
			
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);
			
			novoPedido = emissaoPedido.emitir(novoPedido);
			
			return pedidoModelAssembler.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	private  Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = ImmutableMap.of(
				"codigo" , "codigo",
				"restaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal"
				);
		return  PageableTranslator.translate(apiPageable, mapeamento);
		
	}
}










 







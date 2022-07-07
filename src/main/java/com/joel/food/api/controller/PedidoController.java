package com.joel.food.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.joel.food.api.assembler.PedidoInputDisassembler;
import com.joel.food.api.assembler.PedidoModelAssembler;
import com.joel.food.api.assembler.PedidoResumoModelAssembler;
import com.joel.food.api.model.PedidoModel;
import com.joel.food.api.model.PedidoResumoModel;
import com.joel.food.api.model.input.PedidoInput;
import com.joel.food.domain.exception.EntidadeNaoEncontradaException;
import com.joel.food.domain.exception.NegocioException;
import com.joel.food.domain.model.Pedido;
import com.joel.food.domain.model.Usuario;
import com.joel.food.domain.repository.PedidoRepository;
import com.joel.food.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@GetMapping
	public List<PedidoResumoModel> listar() {
		List<Pedido> todosPedidos = pedidoRepository.findAll();
		return pedidoResumoModelAssembler.toCollectionModel(todosPedidos);
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoModel buscar(@PathVariable Long pedidoId) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
		
		return pedidoModelAssembler.toModel(pedido);
	}
	
	@PostMapping
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
}


















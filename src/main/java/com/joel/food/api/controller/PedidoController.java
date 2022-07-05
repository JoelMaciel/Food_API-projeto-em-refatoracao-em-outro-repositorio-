package com.joel.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joel.food.api.assembler.PedidoModelAssembler;
import com.joel.food.api.assembler.PedidoResumoModelAssembler;
import com.joel.food.api.model.PedidoModel;
import com.joel.food.api.model.PedidoResumoModel;
import com.joel.food.domain.model.Pedido;
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
}


















package com.joel.food.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.joel.food.api.v1.FoodLinks;
import com.joel.food.api.v1.controller.PedidoController;
import com.joel.food.api.v1.model.PedidoResumoModel;
import com.joel.food.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler
		extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FoodLinks foodLinks;

	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}

	@Override
	public PedidoResumoModel toModel(Pedido pedido) {
		PedidoResumoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);

		pedidoModel.add(foodLinks.linkToPedidos("pedidos"));

		pedidoModel.getRestaurante().add(foodLinks.linkToRestaurante(pedido.getRestaurante().getId()));

		pedidoModel.getCliente().add(foodLinks.linkToUsuario(pedido.getCliente().getId()));

		return pedidoModel;
	}

}
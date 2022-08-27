package com.joel.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.joel.food.api.FoodLinks;
import com.joel.food.api.controller.PedidoController;
import com.joel.food.api.model.PedidoModel;
import com.joel.food.domain.model.Pedido;

@Component
public class PedidoModelAssembler 
        extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private FoodLinks foodLinks;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }
    
    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        pedidoModel.add(foodLinks.linkToPedidos("pedidos"));
        
        if(pedido.podeSerConfirmado()) {
        	pedidoModel.add(foodLinks.linkToConfirmaçaoPedido(pedido.getCodigo(), "confirmar"));        	
        }
        
        if(pedido.podeSerCancelado()) {
        	pedidoModel.add(foodLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));  	
        }
        
        if(pedido.podeSerEntregue()) {
        	pedidoModel.add(foodLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));        	
        }
        
        
        pedidoModel.getRestaurante().add(
                foodLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        
        pedidoModel.getCliente().add(
                foodLinks.linkToUsuario(pedido.getCliente().getId()));
        
        pedidoModel.getFormaPagamento().add(
                foodLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        
        pedidoModel.getEnderecoEntrega().getCidade().add(
                foodLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        
        pedidoModel.getItens().forEach(item -> {
            item.add(foodLinks.linkToProduto(
                    pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });
        
        return pedidoModel;
    }
}

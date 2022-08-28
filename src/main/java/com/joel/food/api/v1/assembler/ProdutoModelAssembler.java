package com.joel.food.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.joel.food.api.v1.FoodLinks;
import com.joel.food.api.v1.controller.RestauranteProdutoController;
import com.joel.food.api.v1.model.ProdutoModel;
import com.joel.food.domain.model.Produto;

@Component
public class ProdutoModelAssembler 
        extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private FoodLinks foodLinks;
    
    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }
    
    @Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());
        
        modelMapper.map(produto, produtoModel);
        
        produtoModel.add(foodLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

        produtoModel.add(foodLinks.linkToFotoProduto(
                produto.getRestaurante().getId(), produto.getId(), "foto"));
        
        return produtoModel;
    }    
}        









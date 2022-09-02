package com.joel.food.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.joel.food.api.v1.FoodLinks;
import com.joel.food.api.v1.model.PermissaoModel;
import com.joel.food.core.security.FoodSecurity;
import com.joel.food.domain.model.Permissao;

@Component
public class PermissaoModelAssembler 
        implements RepresentationModelAssembler<Permissao, PermissaoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private FoodLinks foodLinks;
    
    @Autowired
    private FoodSecurity foodSecurity;

    @Override
    public PermissaoModel toModel(Permissao permissao) {
        PermissaoModel permissaoModel = modelMapper.map(permissao, PermissaoModel.class);
        return permissaoModel;
    }
    
    @Override
    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
        CollectionModel<PermissaoModel> collectionModel 
            = RepresentationModelAssembler.super.toCollectionModel(entities);

        if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(foodLinks.linkToPermissoes());
        }
        
        return collectionModel;            
    }  
}














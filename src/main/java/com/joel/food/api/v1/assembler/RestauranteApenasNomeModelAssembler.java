package com.joel.food.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.joel.food.api.v1.FoodLinks;
import com.joel.food.api.v1.controller.RestauranteController;
import com.joel.food.api.v1.model.RestauranteApenasNomeModel;
import com.joel.food.core.security.FoodSecurity;
import com.joel.food.domain.model.Restaurante;

@Component
public class RestauranteApenasNomeModelAssembler 
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private FoodLinks foodLinks;
    
    @Autowired
    private FoodSecurity foodSecurity;
    
    public RestauranteApenasNomeModelAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeModel.class);
    }
    
    @Override
    public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
        RestauranteApenasNomeModel restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);
        
        modelMapper.map(restaurante, restauranteModel);
        
        if (foodSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(foodLinks.linkToRestaurantes("restaurantes"));
        }
        
        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteApenasNomeModel> collectionModel = super.toCollectionModel(entities);
        
        if (foodSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(foodLinks.linkToRestaurantes());
        }
                
        return collectionModel;
    } 
}

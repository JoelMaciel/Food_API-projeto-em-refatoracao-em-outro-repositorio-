package com.joel.food.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.joel.food.api.FoodLinks;
import com.joel.food.api.controller.EstadoController;
import com.joel.food.api.model.EstadoModel;
import com.joel.food.domain.model.Estado;

@Component
public class EstadoModelAssembler 
        extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private FoodLinks foodLinks;
    
    public EstadoModelAssembler() {
        super(EstadoController.class, EstadoModel.class);
    }
    
    @Override
    public EstadoModel toModel(Estado estado) {
        EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModel);
        
        estadoModel.add(foodLinks.linkToEstados("estados"));        
        return estadoModel;
    }
    
    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
        		.add(foodLinks.linkToEstados());
    }   
} 







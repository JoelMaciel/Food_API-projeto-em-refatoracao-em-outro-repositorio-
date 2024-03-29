package com.joel.food.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.joel.food.api.v1.FoodLinks;
import com.joel.food.api.v1.controller.UsuarioController;
import com.joel.food.api.v1.model.UsuarioModel;
import com.joel.food.core.security.FoodSecurity;
import com.joel.food.domain.model.Usuario;

@Component
public class UsuarioModelAssembler 
        extends RepresentationModelAssemblerSupport<Usuario, UsuarioModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private FoodLinks foodLinks;
    
    @Autowired
    private FoodSecurity foodSecurity;
    
    public UsuarioModelAssembler() {
        super(UsuarioController.class, UsuarioModel.class);
    }
    
    @Override
    public UsuarioModel toModel(Usuario usuario) {
        UsuarioModel usuarioModel = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModel);
        
        if (foodSecurity.podeConsultarUsuariosGruposPermissoes()) {
        	usuarioModel.add(foodLinks.linkToUsuarios("usuarios"));
        	
        	usuarioModel.add(foodLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
        }
        
        usuarioModel.add(foodLinks.linkToUsuarios("usuarios"));
        
        usuarioModel.add(foodLinks.linkToGruposUsuario(usuario.getId(), "grupo-usuario"));
        
        return usuarioModel;
    }
    
    @Override
    public CollectionModel<UsuarioModel> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
        		.add(foodLinks.linkToUsuarios());
    }   
}        
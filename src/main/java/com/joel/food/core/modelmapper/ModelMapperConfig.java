package com.joel.food.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.joel.food.api.model.input.ItemPedidoInput;
import com.joel.food.api.v1.model.EnderecoModel;
import com.joel.food.api.v2.model.input.CidadeInputV2;
import com.joel.food.domain.model.Cidade;
import com.joel.food.domain.model.Endereco;
import com.joel.food.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {

		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
		.addMappings(mapper -> mapper.skip(Cidade::setId));
		
		//quando for fazer a conversao de ItemPedidoInput para ItemPedido o atributo Id será ignorado.
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
		.addMappings(mapper -> mapper.skip(ItemPedido::setId));

		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

		enderecoToEnderecoModelTypeMap.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));

		return modelMapper;
	}

}

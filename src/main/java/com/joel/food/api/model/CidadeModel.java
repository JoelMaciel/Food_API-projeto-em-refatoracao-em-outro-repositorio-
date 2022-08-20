package com.joel.food.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Resprenta uma cidade")
@Setter
@Getter
public class CidadeModel {

	//@ApiModelProperty(value = "ID da cidade", example = "1")
	@ApiModelProperty( example = "1")
	private Long id;
	@ApiModelProperty(example = "Caucaia")
	private String nome;
	private EstadoModel estado;
}

package com.joel.food.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {
	
	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "João da Silva")
	private String nome;

	@ApiModelProperty(example = "jmviana37@gmail.com")
	private String email;

}

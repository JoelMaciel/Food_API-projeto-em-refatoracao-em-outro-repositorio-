package com.joel.food.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class CidadeInput {

	@ApiModelProperty(example = "Uberlandia")
	@NonNull
	private String nome;
	
	@NotNull
	@Valid
	private EstadoIdInput estado;
}

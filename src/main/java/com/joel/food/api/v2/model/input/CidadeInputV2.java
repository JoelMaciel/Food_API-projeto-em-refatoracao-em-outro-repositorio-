package com.joel.food.api.v2.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeInputV2 {

	@ApiModelProperty(example = "Uberlandia", required = true)
	@NotBlank
	private String nomeCidade;
	
	@NotNull
	@ApiModelProperty(example = "1", required = true)
	private Long idEstado;
	
//	@NotNull
//	@Valid
//	private EstadoIdInput estado;
}

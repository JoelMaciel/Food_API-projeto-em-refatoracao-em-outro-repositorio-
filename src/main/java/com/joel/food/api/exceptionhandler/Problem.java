package com.joel.food.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {
	
	@ApiModelProperty(example =  "400")
	private Integer status;
	
	@ApiModelProperty(example =  "2022-08-01T18:09:02.70844Z")
	private OffsetDateTime timestamp;
	
	@ApiModelProperty(example =  "http://food.api.com.br/dados-invalidos")
	private String type;
	
	@ApiModelProperty(example =  "Dados invalidos")
	private String title;
	
	@ApiModelProperty(example =  "Um ou mais campos estão inválidos. Faça o preenchimento correto.")
	private String detail;
	
	@ApiModelProperty(example =  "Um ou mais campos estão inválidos. Faça o preenchimento correto.")
	private String userMessage;
	
	@ApiModelProperty(example = "Objetos os campos que geraram o erro")
	private List<Object> objects;
	
	@ApiModel("Objeto problema")
	@Getter
	@Builder
	public static class Object {
		
		@ApiModelProperty(example = "preco")
		private String name;
		@ApiModelProperty(example = "O preço é obrigatório.")
		private String userMessage;
	}
	

}

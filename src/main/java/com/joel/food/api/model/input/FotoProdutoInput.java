package com.joel.food.api.model.input;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.joel.food.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {

	@NotNull
	@FileSize(max = "500KB")
	private MultipartFile arquivo;
	
	@NotNull
	private String descricao;
}

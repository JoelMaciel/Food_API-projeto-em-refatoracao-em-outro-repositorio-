package com.joel.food.api.model.input;


import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.joel.food.core.validation.FileContentType;
import com.joel.food.core.validation.FileSize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {

	@NotNull
	@FileSize(max = "500KB")
	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	private MultipartFile arquivo;
	
	@NotNull
	private String descricao;
}







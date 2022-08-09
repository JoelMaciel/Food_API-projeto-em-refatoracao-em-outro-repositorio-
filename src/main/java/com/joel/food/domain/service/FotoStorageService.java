package com.joel.food.domain.service;

import com.lowagie.text.pdf.codec.Base64.InputStream;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	void armazernar(NovaFoto novaFoto);
	
	@Getter
	@Builder
	class NovaFoto {
		private String nomeArquivo;
		private InputStream inputStream;
	}

}

package com.joel.food.infrastructure.service.storage;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.joel.food.domain.service.FotoStorageService;

@Service
public class S3FotoStorageService implements FotoStorageService{

	@Override
	public void armazernar(NovaFoto novaFoto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remover(String nomeArquivo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStream recuperar(String nomeArquivo) {
		// TODO Auto-generated method stub
		return null;
	}

}

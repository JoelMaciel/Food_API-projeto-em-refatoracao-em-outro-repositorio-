package com.joel.food.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.bouncycastle.util.StoreException;
import org.flywaydb.core.internal.util.FileCopyUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.joel.food.domain.service.FotoStorageService;

@Service
public class LocalFotoStorageService implements FotoStorageService{
	
	@Value("${food.storage.local.diretorio-fotos}")
	private Path direitorioFotos;

	@Override
	public void armazernar(NovaFoto novaFoto) {
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
		
			FileCopyUtils.copy(novaFoto.getInputStream(),
					Files.newOutputStream(arquivoPath));
		} catch (Exception e) {
			throw new StoreException("Não foi possível armazenar arquivo.", e);
		}
		
	}
	
	private Path getArquivoPath(String nomeArquivo) {
		return direitorioFotos.resolve(Path.of(nomeArquivo));
	}

	

}

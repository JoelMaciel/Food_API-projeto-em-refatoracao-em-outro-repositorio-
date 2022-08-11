package com.joel.food.infrastructure.service.storage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.bouncycastle.util.StoreException;
import org.flywaydb.core.internal.util.FileCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joel.food.api.storage.StorageProperties;
import com.joel.food.domain.service.FotoStorageService;

@Service
public class LocalFotoStorageService implements FotoStorageService {

	
	@Autowired
	private StorageProperties storageProperties;

	private Path getArquivoPath(String nomeArquivo) {
		return storageProperties.getLocal().getDiretorioFotos()
				.resolve(Path.of(nomeArquivo));
	}

	@Override
	public void armazernar(NovaFoto novaFoto) {
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (Exception e) {
			throw new StoreException("Não foi possível armazenar arquivo.", e);
		}

	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			Path arquivoPath = getArquivoPath(nomeArquivo);
			Files.deleteIfExists(arquivoPath);
		} catch (Exception e) {
			throw new StoreException("Não foi possível excluir o arquivo", e);
		}

	}

	@Override
	public InputStream recuperar(String nomeArquivo) {
		try {
		Path arquivoPath = getArquivoPath(nomeArquivo);
			return  Files.newInputStream(arquivoPath);
		} catch (Exception e) {
			throw new StoreException("Não foi possível recuperar arquivo.", e);
		}
	}

}




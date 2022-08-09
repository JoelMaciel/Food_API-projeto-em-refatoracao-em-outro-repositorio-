package com.joel.food.infrastructure.service.storage;

import com.joel.food.domain.exception.EntidadeNaoEncontradaException;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public FotoProdutoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public FotoProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
		this(String.format("Não existe um cadastro de foto de produto com o códico %d para p restaurante de código %d",
				produtoId, restauranteId));
	}

}

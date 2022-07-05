package com.joel.food.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
	static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public PedidoNaoEncontradoException(Long pedidoId) {
		this(String.format("Não existe um pedido com cõdigo %d", pedidoId));
	}

}

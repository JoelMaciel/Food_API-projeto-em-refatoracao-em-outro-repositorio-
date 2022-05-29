package com.joel.food.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	ENTIDADE_NAO_ENCONTRADA("/entidade-não-encontrada", "Entidade não encontrada"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensível", "Mensagem incompreensível"),
	ENITDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");

	private String tile;
	private String uri;

	ProblemType(String path, String title) {
		this.uri = "https://food_api.com.br" + path;
		this.tile = title;
	}

}

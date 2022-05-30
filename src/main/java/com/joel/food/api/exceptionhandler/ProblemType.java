package com.joel.food.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	RECURSO_NAO_ENCONTRADO("/recurso-não-encontrado", "Recurso não encontrado"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensível", "Mensagem incompreensível"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido");

	private String title;
	private String uri;

	ProblemType(String path, String title) {
		this.uri = "https://food_api.com.br" + path;
		this.title = title;
	}

}

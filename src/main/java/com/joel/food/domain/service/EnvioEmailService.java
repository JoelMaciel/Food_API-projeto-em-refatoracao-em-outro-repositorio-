package com.joel.food.domain.service;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

public interface EnvioEmailService {
	
	void enviar(Mensagem mensagem);
	
	
	@Getter
	@Setter
	class Mensagem {
		
		private Set<String> destinarios;
		private String assunto;
		private String corpo;
	}
	
	

}

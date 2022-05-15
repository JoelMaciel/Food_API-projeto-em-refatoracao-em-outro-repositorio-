package com.joel.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joel.food.domain.exception.EntidadeNaoEncontradaException;
import com.joel.food.domain.model.Cozinha;
import com.joel.food.domain.model.Restaurante;
import com.joel.food.domain.repository.CozinhaRepository;
import com.joel.food.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro com o código %d", cozinhaId)));

		
		restaurante.setCozinha(cozinha);
		return restauranteRepository.salvar(restaurante);
	}

}

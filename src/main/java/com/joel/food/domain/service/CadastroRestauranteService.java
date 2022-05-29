package com.joel.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joel.food.domain.exception.RestauranteNaoEncontradoException;
import com.joel.food.domain.model.Cozinha;
import com.joel.food.domain.model.Restaurante;
import com.joel.food.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).orElseThrow(
				() -> new RestauranteNaoEncontradoException(restauranteId));
	}

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();

		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

		restaurante.setCozinha(cozinha);
		return restauranteRepository.save(restaurante);
	}

}
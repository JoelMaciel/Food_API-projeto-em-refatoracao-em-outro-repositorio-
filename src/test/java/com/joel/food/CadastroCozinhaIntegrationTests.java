package com.joel.food;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.joel.food.domain.exception.CozinhaNaoEncontradaException;
import com.joel.food.domain.exception.EntidadeEmUsoException;
import com.joel.food.domain.model.Cozinha;
import com.joel.food.domain.service.CadastroCozinhaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CadastroCozinhaIntegrationTests {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Test
	public void testarCadastroCozinhaComSucesso() {

		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void testarCadastroCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
			cadastroCozinha.salvar(novaCozinha);

		});
		assertThat(erroEsperado).isNotNull();

	}
	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		EntidadeEmUsoException exception = assertThrows(EntidadeEmUsoException.class, () -> {
			Long cozinhaId = 1L;
			cadastroCozinha.excluir(cozinhaId);
		});
		
		exception.printStackTrace();
		assertThatExceptionOfType(EntidadeEmUsoException.class);
	}
	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		CozinhaNaoEncontradaException exception = assertThrows(CozinhaNaoEncontradaException.class, () -> {
			Long cozinhaId = 10L;
			cadastroCozinha.excluir(cozinhaId);
		});
		exception.printStackTrace();
		assertThatExceptionOfType(CozinhaNaoEncontradaException.class);
	}

}

package com.joel.food;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.joel.food.domain.exception.CozinhaNaoEncontradaException;
import com.joel.food.domain.exception.EntidadeEmUsoException;
import com.joel.food.domain.service.CadastroCozinhaService;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIntegrationT {
	
	@LocalServerPort
	private int port;
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		RestAssured.given()
		.basePath("/cozinhas")
		.port(port)
		.accept(ContentType.JSON)
		.when().get().then().statusCode(HttpStatus.SC_OK);
	}

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
//
//	@Test
//	public void testarCadastroCozinhaComSucesso() {
//
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setNome("Chinesa");
//
//		novaCozinha = cadastroCozinha.salvar(novaCozinha);
//
//		assertThat(novaCozinha).isNotNull();
//		assertThat(novaCozinha.getId()).isNotNull();
//	}
//
//	@Test
//	public void testarCadastroCozinhaSemNome() {
//		Cozinha novaCozinha = new Cozinha();
//		novaCozinha.setNome(null);
//
//		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
//			cadastroCozinha.salvar(novaCozinha);
//
//		});
//		assertThat(erroEsperado).isNotNull();
//
//	}
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

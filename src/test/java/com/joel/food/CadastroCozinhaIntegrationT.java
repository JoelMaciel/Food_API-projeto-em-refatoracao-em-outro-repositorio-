package com.joel.food;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.joel.food.domain.model.Cozinha;
import com.joel.food.domain.repository.CozinhaRepository;
import com.joel.food.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIntegrationT {

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	CozinhaRepository cozinhaRepository;
	
	

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		databaseCleaner.clearTables();
		prepararDados();
		

	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {

		given()
		.accept(ContentType.JSON)
		.when()
		.get()
		.then()
		.statusCode(HttpStatus.SC_OK);
	}

	@Test
	public void deveConter2Cozinhas_QuandoConsultarCozinhas() {

		given()
		.accept(ContentType.JSON)
		.when()
		.get()
		.then()
		.body("", Matchers.hasSize(2));
				
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		given()
		.body("{ \"nome\": \"Chinesa\"}")
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.when()
		.post()
		.then()
		 .statusCode(HttpStatus.SC_CREATED);
}
	
	private void prepararDados() {
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandeza");
		cozinhaRepository.save(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);

	}
//	@Autowired
//	private CadastroCozinhaService cadastroCozinha;
////
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
//	@Test
//	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
//		EntidadeEmUsoException exception = assertThrows(EntidadeEmUsoException.class, () -> {
//			Long cozinhaId = 1L;
//			cadastroCozinha.excluir(cozinhaId);
//		});
//		
//		exception.printStackTrace();
//		assertThatExceptionOfType(EntidadeEmUsoException.class);
//	}
//	@Test
//	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
//		CozinhaNaoEncontradaException exception = assertThrows(CozinhaNaoEncontradaException.class, () -> {
//			Long cozinhaId = 10L;
//			cadastroCozinha.excluir(cozinhaId);
//		});
//		exception.printStackTrace();
//		assertThatExceptionOfType(CozinhaNaoEncontradaException.class);
//	}

}

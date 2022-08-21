package com.joel.food.core.openapi;


import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.joel.food.api.controller.openapi.model.PageableModelOpenApi;
import com.joel.food.api.exceptionhandler.Problem;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {
	
	

	 @Bean
	  public Docket apiDocket() {
		 var typeResolver = new TypeResolver();
		 
	    return new Docket(DocumentationType.OAS_30)
	        .select()
	          .apis(RequestHandlerSelectors.basePackage("com.joel.food.api"))
	          .paths(PathSelectors.any())
	          .build()
	        .useDefaultResponseMessages(false)
	        .globalResponses(HttpMethod.GET, globalGetResponseMessages())
	        .globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
	        .globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
	        .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
	        .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
	        .additionalModels(typeResolver.resolve(Problem.class))
	        .apiInfo(apiInfo())
	        .tags(new Tag("Cidades", "Gerencia as cidades"),
	        		new Tag("Grupos", "Gerencia os grupos de usuários"));
	        
	  }
	 
	 private List<Response> globalGetResponseMessages() {
		    return Arrays.asList(
		            new ResponseBuilder()
		                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		                    .description("Erro interno do Servidor")
		                    .representation( MediaType.APPLICATION_JSON )
		                    .apply(getProblemaModelReference())
		                    .build(),
		            new ResponseBuilder()
		                    .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		                    .description("Recurso não possui representação que pode ser aceita pelo consumidor")
		                    .build()
		    );
		}

		private List<Response> globalPostPutResponseMessages() {
		    return Arrays.asList(
		            new ResponseBuilder()
		                    .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		                    .description("Requisição inválida (erro do cliente)")
		                    .representation( MediaType.APPLICATION_JSON )
		                    .apply(getProblemaModelReference())
		                    .build(),
		            new ResponseBuilder()
		                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		                    .description("Erro interno no servidor")
		                    .representation( MediaType.APPLICATION_JSON )
		                    .apply(getProblemaModelReference())
		                    .build(),
		            new ResponseBuilder()
		                    .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
		                    .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
		                    .build(),
		            new ResponseBuilder()
		                    .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
		                    .description("Requisição recusada porque o corpo está em um formato não suportado")
		                    .representation( MediaType.APPLICATION_JSON )
		                    .apply(getProblemaModelReference())
		                    .build()
		    );
		}

		private List<Response> globalDeleteResponseMessages() {
		    return Arrays.asList(
		            new ResponseBuilder()
		                    .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
		                    .description("Requisição inválida (erro do cliente)")
		                    .representation( MediaType.APPLICATION_JSON )
		                    .apply(getProblemaModelReference())
		                    .build(),
		            new ResponseBuilder()
		                    .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
		                    .description("Erro interno no servidor")
		                    .representation( MediaType.APPLICATION_JSON )
		                    .apply(getProblemaModelReference())
		                    .build()
		    );
		}

	
	private Consumer<RepresentationBuilder> getProblemaModelReference() {
	    return r -> r.model(m -> m.name("Problema")
	            .referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
	                    q -> q.name("Problema").namespace("com.joel.food.api.exceptionhandler")))));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Joel Maciel",
						"https://www.linkedin.com/in/joel-maciel-dev-java-back-end-spring-framework",
						 "jmviana37@gmail.com"))
				.build();
	}
	
	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}
	
}









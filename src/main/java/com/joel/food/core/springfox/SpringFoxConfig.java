package com.joel.food.core.springfox;


import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.joel.food.api.exceptionhandler.Problem;
import com.joel.food.api.v1.model.CidadeModel;
import com.joel.food.api.v1.model.CozinhaModel;
import com.joel.food.api.v1.model.EstadoModel;
import com.joel.food.api.v1.model.FormaPagamentoModel;
import com.joel.food.api.v1.model.GrupoModel;
import com.joel.food.api.v1.model.PedidoResumoModel;
import com.joel.food.api.v1.model.PermissaoModel;
import com.joel.food.api.v1.model.ProdutoModel;
import com.joel.food.api.v1.model.RestauranteBasicoModel;
import com.joel.food.api.v1.model.UsuarioModel;
import com.joel.food.api.v1.openapi.model.CidadesModelOpenApi;
import com.joel.food.api.v1.openapi.model.CozinhasModelOpenApi;
import com.joel.food.api.v1.openapi.model.EstadosModelOpenApi;
import com.joel.food.api.v1.openapi.model.FormasPagamentoModelOpenApi;
import com.joel.food.api.v1.openapi.model.GruposModelOpenApi;
import com.joel.food.api.v1.openapi.model.LinksModelOpenApi;
import com.joel.food.api.v1.openapi.model.PageableModelOpenApi;
import com.joel.food.api.v1.openapi.model.PedidosResumoModelOpenApi;
import com.joel.food.api.v1.openapi.model.PermissoesModelOpenApi;
import com.joel.food.api.v1.openapi.model.ProdutosModelOpenApi;
import com.joel.food.api.v1.openapi.model.RestaurantesBasicoModelOpenApi;
import com.joel.food.api.v1.openapi.model.UsuariosModelOpenApi;
import com.joel.food.api.v2.model.CidadeModelV2;
import com.joel.food.api.v2.model.CozinhaModelV2;
import com.joel.food.api.v2.openapi.model.CidadesModelV2OpenApi;
import com.joel.food.api.v2.openapi.model.CozinhasModelV2OpenApi;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.Response;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

	 @Bean
	  public Docket apiDocketV1() {
		 var typeResolver = new TypeResolver();
		 
	    return new Docket(DocumentationType.OAS_30)
	    		 .groupName("V1")
	    		.select()
				.apis(RequestHandlerSelectors.basePackage("com.joel.food.api"))
				.paths(PathSelectors.ant("/v1/**"))
				.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class,
						URL.class, URI.class, URLStreamHandler.class, Resource.class,
						File.class, InputStream.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
						PedidosResumoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CozinhaModel.class),
						CozinhasModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeModel.class),
						CidadesModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
				        typeResolver.resolve(CollectionModel.class, EstadoModel.class),
				        EstadosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
					    FormasPagamentoModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
					    ProdutosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
					    RestaurantesBasicoModelOpenApi.class))
					.alternateTypeRules(AlternateTypeRules.newRule(
					        typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
					        UsuariosModelOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
					    typeResolver.resolve(CollectionModel.class, GrupoModel.class),
					    GruposModelOpenApi.class))

					.alternateTypeRules(AlternateTypeRules.newRule(
					        typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
					        PermissoesModelOpenApi.class))
					  .securityContexts(Arrays.asList(securityContext()))
			           .securitySchemes(List.of(authenticationScheme()))
			           .securityContexts(List.of(securityContext()))
					
				.apiInfo(apiInfoV1())
				.tags(new Tag("Cidades", "Gerencia as cidades"),
						new Tag("Grupos", "Gerencia os grupos de usuários"),
						new Tag("Cozinhas", "Gerencia as cozinhas"),
						new Tag("Formas de pagamento", "Gerencia as formas de pagamento"),
						new Tag("Pedidos", "Gerencia os pedidos"),
						new Tag("Restaurantes", "Gerencia os restaurantes"),
						new Tag("Estados", "Gerencia os estados"),
						new Tag("Produtos", "Gerencia os produtos de restaurantes"),
						new Tag("Usuários", "Gerencia os usuários"),
						 new Tag("Estatísticas", "Estatísticas da AlgaFood"),
						 new Tag("Permissões", "Gerencia as permissões"));
	        
	  }
	 
	 private SecurityScheme securityScheme() {
		 return new OAuthBuilder()
				 .name("Food")
				 .grantTypes(grantTypes())
				 .scopes(scopes())
				 .build();
	 }
	 
	 private SecurityContext securityContext() {
		  return SecurityContext.builder()
		        .securityReferences(securityReference()).build();
		}

		private List<SecurityReference> securityReference() {
		  AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		  AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		  authorizationScopes[0] = authorizationScope;
		  return List.of(new SecurityReference("Authorization", authorizationScopes));
		}

		private HttpAuthenticationScheme authenticationScheme() {
		  return HttpAuthenticationScheme.JWT_BEARER_BUILDER.name("Authorization").build();
		}
	 
	 private List<GrantType> grantTypes() {
		 return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
	 }
	 
	 private List<AuthorizationScope> scopes(){
		 return Arrays.asList(new AuthorizationScope("READ", "Acesso de leitura"),
				 new AuthorizationScope("WRITE","Acesso de leitura"));
	 }
	 
	    @Bean
		public Docket apiDocketV2() {
			var typeResolver = new TypeResolver();

			return new Docket(DocumentationType.OAS_30)
					.groupName("V2")
					.select()
					.apis(RequestHandlerSelectors.basePackage("com.joel.food.api"))
					.paths(PathSelectors.ant("/v2/**"))
					.build()
					.useDefaultResponseMessages(false)
					.globalResponses(HttpMethod.GET, globalGetResponseMessages())
					.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
					.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
					.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
					.additionalModels(typeResolver.resolve(Problem.class))
					.ignoredParameterTypes(ServletWebRequest.class,
							URL.class, URI.class, URLStreamHandler.class, Resource.class,
							File.class, InputStream.class)
					.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
					.directModelSubstitute(Links.class, LinksModelOpenApi.class)
					.alternateTypeRules(AlternateTypeRules.newRule(
						    typeResolver.resolve(PagedModel.class, CozinhaModelV2.class),
						    CozinhasModelV2OpenApi.class))
						.alternateTypeRules(AlternateTypeRules.newRule(
						        typeResolver.resolve(CollectionModel.class, CidadeModelV2.class),
						        CidadesModelV2OpenApi.class))
						.apiInfo(apiInfoV2())
						  .securityContexts(Arrays.asList(securityContext()))
				           .securitySchemes(List.of(authenticationScheme()))
				           .securityContexts(List.of(securityContext()))
						        
						.tags(new Tag("Cidades", "Gerencia as cidades"),
						        new Tag("Cozinhas", "Gerencia as cozinhas"));
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

	private ApiInfo apiInfoV1() {
		return new ApiInfoBuilder()
				.title("Joel_Food API (Depreciada)")
				.description("API aberta para clientes e restaurantes.<br>"
						+ "<strong> Essa versão da API está depreciada e deixará de existir a partir de 30/12/2022."
						+  " Use a versão mais atual da API.")
				.version("1")
				.contact(new Contact("Joel Maciel",
						"https://www.linkedin.com/in/joel-maciel-dev-java-back-end-spring-framework",
						 "jmviana37@gmail.com"))
				.build();
	}
	private ApiInfo apiInfoV2() {
		return new ApiInfoBuilder()
				.title("Joel_Food API")
				.description("API aberta para clientes e restaurantes")
				.version("2")
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









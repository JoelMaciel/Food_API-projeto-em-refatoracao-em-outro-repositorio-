package com.joel.food.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

public @interface Cozinhas {
        
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar { }

        @PreAuthorize("@foodSecurity.podeConsultarCozinhas()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar { }
        
    }
	
public @interface Restaurantes {
    
    @PreAuthorize("@foodSecurity.podeGerenciarCadastroRestaurantes()")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PodeGerenciarCadastro { }

    @PreAuthorize("@foodSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PodeGerenciarFuncionamento { }
    
    @PreAuthorize("@foodSecurity.podeConsultarRestaurantes()")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PodeConsultar { }
    
}

public @interface Pedidos {
    
    @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
    @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
            + "@foodSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or "
            + "@foodSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PodeBuscar { }
    
    @PreAuthorize("@foodSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PodePesquisar { }
    
    @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PodeCriar { }
    
    @PreAuthorize("@foodSecurity.podeGerenciarPedidos(#codigoPedido)")
    @Retention(RUNTIME)
    @Target(METHOD)
    public @interface PodeGerenciarPedidos { }
    
}
	public @interface FormasPagamento {
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar { }

		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar { }
		
	}
	
	 public @interface Cidades {
	        
	        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
	        @Retention(RUNTIME)
	        @Target(METHOD)
	        public @interface PodeEditar { }

	        @PreAuthorize("@foodSecurity.podeConsultarCidades()")
	        @Retention(RUNTIME)
	        @Target(METHOD)
	        public @interface PodeConsultar { }
	        
	    }
	
	 public @interface Estados {
	        
	        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
	        @Retention(RUNTIME)
	        @Target(METHOD)
	        public @interface PodeEditar { }

	        @PreAuthorize("@foodSecurity.podeConsultarEstados()")
	        @Retention(RUNTIME)
	        @Target(METHOD)
	        public @interface PodeConsultar { }
	        
	    }
	
	 public @interface UsuariosGruposPermissoes {
	        
	        @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
	                + "@foodSecurity.usuarioAutenticadoIgual(#usuarioId)")
	        @Retention(RUNTIME)
	        @Target(METHOD)
	        public @interface PodeAlterarPropriaSenha { }
	        
	        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
	                + "@foodSecurity.usuarioAutenticadoIgual(#usuarioId))")
	        @Retention(RUNTIME)
	        @Target(METHOD)
	        public @interface PodeAlterarUsuario { }

	        @PreAuthorize("@foodSecurity.podeEditarUsuariosGruposPermissoes()")
	        @Retention(RUNTIME)
	        @Target(METHOD)
	        public @interface PodeEditar { }
	        

	        @PreAuthorize("@foodSecurity.podeConsultarUsuariosGruposPermissoes()")
	        @Retention(RUNTIME)
	        @Target(METHOD)
	        public @interface PodeConsultar { }
	        
	    }
	    
	    public @interface Estatisticas {
	        
	        @PreAuthorize("@foodSecurity.podeConsultarEstatisticas()")
	        @Retention(RUNTIME)
	        @Target(METHOD)
	        public @interface PodeConsultar { }
	        
	    }   
	
}
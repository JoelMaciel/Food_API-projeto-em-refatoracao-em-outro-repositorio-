package com.joel.food.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joel.food.domain.exception.NegocioException;
import com.joel.food.domain.exception.UsuarioNaoEncontradoException;
import com.joel.food.domain.model.Grupo;
import com.joel.food.domain.model.Usuario;
import com.joel.food.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente  = usuarioRepository.findByEmail(usuario.getEmail());
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe um usuario cadastrado com o email %s",  usuario.getEmail()));
		}
		
		if (usuario.isNovo()) {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		}
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional
	public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
	    Usuario usuario = buscarOuFalhar(usuarioId);
	    
	    if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
	        throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
	    }
	    
	    usuario.setSenha(passwordEncoder.encode(novaSenha));
	}
	
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		
		usuario.removerGrupo(grupo);
	}
	
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		
		Usuario usuario = buscarOuFalhar(usuarioId);
		
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		
		usuario.adicionarGrupo(grupo);
	}
	
	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}

}













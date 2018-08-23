package services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import domain.entity.negocio.Usuario;
import domain.repository.UsuarioRepository;
import services.Autenticacao;

@Stateless
public class AutenticacaoImpl implements Autenticacao{
	
	@Inject
	private UsuarioRepository usuarioRepository;

	public boolean login(String login, String senha) {
		List<Usuario> usuarios = usuarioRepository.buscarTodos();
		if(usuarios.isEmpty() && login.equalsIgnoreCase("admin") && senha.equalsIgnoreCase("admin"))
			return true;
		return usuarios
			.stream()
				.filter(usuario -> usuario.getLogin().equals(login) && usuario.getSenha().equals(senha))
					.collect(Collectors.toList()).size() > 0;
	}
	

	public boolean existeUsuario(String login, String senha) {
		 return usuarioRepository.buscarTodos()
		 	.stream()
		 		.filter(usuario -> usuario.getLogin().equals(login))
		 			.collect(Collectors.toList()).size() > 0;
	}
	
	public boolean cadastrar(String login, String senha) {
		if(existeUsuario(login,senha))
			return false;
		if(senha.length() < 3)
			return false;
		
		Usuario novoUsuario = new Usuario(login,senha);
		try {
			usuarioRepository.salvar(novoUsuario);	
			return true;
		}catch(Exception e) {
			return false;
		}		
		
	}

}

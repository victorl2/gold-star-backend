package resource.dto;

import java.util.Optional;

public class InformacaoUsuario {
	private String login;
	private String senha;
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Optional<String> getOptSenha(){
		return Optional.ofNullable(senha);
	}
	
	public Optional<String> getOptLogin(){
		return Optional.ofNullable(login);
	}
	
}
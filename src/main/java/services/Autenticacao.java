package services;

public interface Autenticacao {
	public boolean login(final String login, final String senha);
	
	public boolean existeUsuario(final String login, final String senha);
	
	public boolean cadastrar(String login, String senha);
}

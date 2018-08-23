package domain.entity.negocio;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import domain.entity.BaseEntity;

@Entity
@Table(name = "USUARIO", schema = "NEGOCIO")
@AttributeOverride(name = "ID", column = @Column(name = "USUARIO_ID"))
public class Usuario extends BaseEntity{
	private static final long serialVersionUID = 1932799233870617068L;
	
	@Column(name = "LOGIN")
	private String login;
	
	@Column(name = "SENHA")
	private String senha;
	
	@Column(name = "ULTIMO_LOGIN")
	private Date ultimoLogin;
	
	public Usuario() {
		this.ultimoLogin = new Date();
	}
	
	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
		this.ultimoLogin = new Date();
	}

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

	public Date getUltimoLogin() {
		return ultimoLogin;
	}

	public void setUltimoLogin(Date ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}
	
}

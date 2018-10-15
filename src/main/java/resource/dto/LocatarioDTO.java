package resource.dto;

import domain.entity.negocio.Locatario;

public class LocatarioDTO {
	private String id;
	private String cpf;
	private String nome;
	private String telefone;
	private String celular;
	private Boolean possuidor;
	private String email;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public Boolean getPossuidor() {
		return possuidor;
	}
	public void setPossuidor(Boolean possuidor) {
		this.possuidor = possuidor;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	public Locatario build() {
		Locatario loc = new Locatario();
		loc.setCelular(this.getCelular());
		loc.setCpf(this.getCpf());
		loc.setNome(this.getNome());
		loc.setPossuidor(this.getPossuidor());
		return loc;
	}
}

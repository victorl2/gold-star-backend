package resource.dto;

import domain.entity.negocio.Locatario;

public class LocatarioDTO {
	private String cpf;
	private String nome;
	private String telefone;
	private String celular;
	private Boolean possuidor;
	
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
	public boolean isPossuidor() {
		return possuidor;
	}
	public void setPossuidor(boolean possuidor) {
		this.possuidor = possuidor;
	}
	
	public Locatario build() {
		Locatario loc = new Locatario();
		loc.setCelular(this.getCelular());
		loc.setCpf(this.getCpf());
		loc.setNome(this.getNome());
		loc.setPossuidor(this.isPossuidor());
		return loc;
	}
}

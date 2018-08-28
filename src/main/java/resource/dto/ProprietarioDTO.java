package resource.dto;

import domain.entity.negocio.Proprietario;

public class ProprietarioDTO {
	private String nome;
	private String telefone;
	private String celular;
	private boolean possuidor;
	private String cpf;
	private String endereco;
	
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Proprietario build() {
		Proprietario prop = new Proprietario();
		prop.setCelular(this.getCelular());
		prop.setCpf(this.getCpf());
		prop.setEndereco(this.getEndereco());
		prop.setNome(this.getNome());
		prop.setPossuidor(this.isPossuidor());
		return prop;
	}
}

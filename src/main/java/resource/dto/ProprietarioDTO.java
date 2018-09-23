package resource.dto;

import domain.entity.negocio.Proprietario;

public class ProprietarioDTO {
	private String id;
	private String nome;
	private String telefone;
	private String celular;
	private Boolean possuidor;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getPossuidor() {
		return possuidor;
	}
	public void setPossuidor(Boolean possuidor) {
		this.possuidor = possuidor;
	}
	public Proprietario build() {
		Proprietario prop = new Proprietario();
		prop.setCelular(this.getCelular());
		prop.setCpf(this.getCpf());
		prop.setEndereco(this.getEndereco());
		prop.setNome(this.getNome());
		prop.setTelefone(this.getTelefone());
		return prop;
	}
}

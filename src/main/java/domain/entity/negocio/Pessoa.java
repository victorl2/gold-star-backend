package domain.entity.negocio;

public abstract class Pessoa {
	
	private String nome;
	
	private String telefone;
	
	private String celular;
	
	/**
	 * Indica que a pessoa é "possuidora" de um imovel
	 */
	private boolean possuidor;

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
	
	

}

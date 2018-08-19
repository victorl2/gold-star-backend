package domain.entity.negocio;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import domain.entity.BaseEntity;

@Entity
@Table(name = "PESSOA", schema = "NEGOCIO")
@AttributeOverride(name = "ID", column = @Column(name = "PESSOA_ID"))
public abstract class Pessoa extends BaseEntity{
	private static final long serialVersionUID = 1310521356899772315L;

	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "TELEFONE")
	private String telefone;
	
	@Column(name = "CELULAR")
	private String celular;
	
	/**
	 * Indica que a pessoa é "possuidora" de um imovel
	 */
	@Column(name = "E_POSSUIDOR_IMOVEL")
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

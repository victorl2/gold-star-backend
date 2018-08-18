package domain.entity.negocio;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PROPRIETARIO", schema = "NEGOCIO")
@AttributeOverride(name = "ID", column = @Column(name = "PROPRIETARIO_ID"))
public class Proprietario extends Pessoa {
	
	@Column(name = "CPF")
	private String cpf;
	
	@Column(name = "ENDERECO")
	private String endereco;
	
	@OneToMany(mappedBy = "donoImovel")
	private List<Imovel> imoveis;

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

	public List<Imovel> getImoveis() {
		return imoveis;
	}

	public void setImoveis(List<Imovel> imoveis) {
		this.imoveis = imoveis;
	}	
	
	
}

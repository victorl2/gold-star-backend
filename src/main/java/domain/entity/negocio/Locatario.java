package domain.entity.negocio;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "LOCATARIO", schema = "NEGOCIO")
@AttributeOverride(name = "ID", column = @Column(name = "LOCATARIO_ID"))
public class Locatario extends Pessoa {
	
	@Column(name = "CPF")
	private String cpf;
	
	@OneToMany(mappedBy = "locador")
	private List<Imovel> imoveisAlugados;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Imovel> getImoveisAlugados() {
		return imoveisAlugados;
	}

	public void setImoveisAlugados(List<Imovel> imoveisAlugados) {
		this.imoveisAlugados = imoveisAlugados;
	}
	
	
}

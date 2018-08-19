package domain.entity.negocio;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Locatario extends Pessoa {
	private static final long serialVersionUID = -4180334420040116206L;

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

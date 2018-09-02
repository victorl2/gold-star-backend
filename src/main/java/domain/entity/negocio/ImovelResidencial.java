package domain.entity.negocio;

import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class ImovelResidencial extends Imovel{
	private static final long serialVersionUID = -1981916995685971317L;

	@OneToMany(cascade={CascadeType.ALL})
	@JoinTable(name = "MORADORES_RESIDENCIA")
	private List<Pessoa> moradores;
	
	@Column(name = "POSSUI_ANIMAL")
	private Boolean possuiAnimalEstimacao;
	
	public List<Pessoa> getMoradores() {
		return moradores;
	}

	public void setMoradores(List<Pessoa> moradores) {
		this.moradores = moradores;
	}

	public Boolean isPossuiAnimalEstimacao() {
		return possuiAnimalEstimacao;
	}

	public void setPossuiAnimalEstimacao(Boolean possuiAnimalEstimacao) {
		this.possuiAnimalEstimacao = possuiAnimalEstimacao;
	}
	
	public Optional<List<ProcessoCondominial>> getOptProcessos() {
		return Optional.ofNullable(this.getProcessos());
	}
}

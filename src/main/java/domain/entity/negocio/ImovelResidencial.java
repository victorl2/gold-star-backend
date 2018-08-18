package domain.entity.negocio;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class ImovelResidencial extends Imovel{
	
	@OneToMany
	@JoinTable(name = "MORADORES_RESIDENCIA")
	private List<Pessoa> moradores;
	
	@Column(name = "POSSUI_ANIMAL")
	private boolean possuiAnimalEstimacao;

	public List<Pessoa> getMoradores() {
		return moradores;
	}

	public void setMoradores(List<Pessoa> moradores) {
		this.moradores = moradores;
	}

	public boolean isPossuiAnimalEstimacao() {
		return possuiAnimalEstimacao;
	}

	public void setPossuiAnimalEstimacao(boolean possuiAnimalEstimacao) {
		this.possuiAnimalEstimacao = possuiAnimalEstimacao;
	}
	
	
}

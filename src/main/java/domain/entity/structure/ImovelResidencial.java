package domain.entity.structure;

import java.util.List;

public class ImovelResidencial extends Imovel{
	
	private List<Pessoa> moradores;
	
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

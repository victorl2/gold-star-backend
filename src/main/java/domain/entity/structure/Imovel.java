package domain.entity.structure;

import java.util.Optional;

public abstract class Imovel {
	
	/**
	 * N�mero do apartamento no edif�cio goldstar
	 */
	private int numeroImovel;
	
	/**
	 * C�digo RGI do im�vel
	 */
	private String rgi;
		
	/**
	 * Indica se o cano ( barbara ) foi trocado
	 */
	private boolean trocouBarbara;
	
	
	/**
	 * Pessoa que � a proprietaria do im�vel
	 */
	private Proprietario donoImovel;
	
	/**
	 * Pessoa que est� atualmente alugando o imovel
	 */
	private Locatario locador;


	public int getNumeroImovel() {
		return numeroImovel;
	}


	public void setNumeroImovel(int numeroImovel) {
		this.numeroImovel = numeroImovel;
	}


	public String getRgi() {
		return rgi;
	}


	public void setRgi(String rgi) {
		this.rgi = rgi;
	}


	public boolean isTrocouBarbara() {
		return trocouBarbara;
	}


	public void setTrocouBarbara(boolean trocouBarbara) {
		this.trocouBarbara = trocouBarbara;
	}


	public Proprietario getDonoImovel() {
		return donoImovel;
	}


	public void setDonoImovel(Proprietario donoImovel) {
		this.donoImovel = donoImovel;
	}
	
	
	public Optional<Locatario> getLocatario(){
		return Optional.ofNullable(locador);
	}
	
	
	
}

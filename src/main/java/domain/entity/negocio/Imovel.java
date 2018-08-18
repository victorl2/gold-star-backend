package domain.entity.negocio;

import java.util.List;
import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import domain.entity.BaseEntity;

@Entity
@Table(name = "IMOVEL", schema = "NEGOCIO")
@AttributeOverride(name = "ID", column = @Column(name = "IMOVEL_ID"))
public abstract class Imovel extends BaseEntity{
	
	/**
	 * Número do apartamento no edifício goldstar
	 */
	@Column(name = "NUMERO")
	private int numeroImovel;
	
	/**
	 * Código RGI do imóvel
	 */
	@Column(name = "RGI")
	private String rgi;
		
	/**
	 * Indica se o cano ( barbara ) foi trocado
	 */
	@Column(name = "TROCOU_BARBARA")
	private boolean trocouBarbara;
	
	/**
	 * Pessoa que é a proprietaria do imóvel
	 */
	@ManyToOne
	@JoinColumn(unique = false, name="PROPRIETARIO")
	private Proprietario donoImovel;
	
	/**
	 * Pessoa que está atualmente alugando o imovel
	 */
	@ManyToOne
	@JoinColumn(unique = false, name="LOCATARIO")
	private Locatario locador;
	
	/**
	 * Lista de processos sobre o imovel
	 */
	@OneToMany
	@JoinColumn(unique = false, name="PROCESSOS_POR_IMOVEL")
	private List<ProcessoCondominial> processos;


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


	public List<ProcessoCondominial> getProcessos() {
		return processos;
	}


	public void setProcessos(List<ProcessoCondominial> processos) {
		this.processos = processos;
	}
	
	
	
}

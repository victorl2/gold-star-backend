package domain.entity.negocio;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import domain.entity.BaseEntity;

@Entity
@Table(name = "IMOVEL", schema = "NEGOCIO")
@AttributeOverride(name = "ID", column = @Column(name = "IMOVEL_ID"))
public abstract class Imovel extends BaseEntity{
	private static final long serialVersionUID = 2635211862637609953L;

	/**
	 * Número do apartamento no edifício goldstar
	 */
	@Column(name = "NUMERO")
	private Integer numeroImovel;
	
	/**
	 * Código RGI do imóvel
	 */
	@Column(name = "RGI")
	private String rgi;
		
	/**
	 * Indica se o cano ( barbara ) foi trocado
	 */
	@Column(name = "TROCOU_BARBARA")
	private Boolean trocouBarbara;
	
	/**
	 * Contato de emergência associado ao imóvel
	 */
	@Column(name = "CONTATO_EMERGENCIA")
	@OneToOne(optional=true)
	private Pessoa contatoEmergencia;
	
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


	public Integer getNumeroImovel() {
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


	public Boolean getTrocouBarbara() {
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
	
	
	public Locatario getLocatario(){
		return locador;
	}
	
	public void setLocatario(Locatario locatario) {
		this.locador = locatario;
	}


	public List<ProcessoCondominial> getProcessos() {
		return processos;
	}


	public void setProcessos(List<ProcessoCondominial> processos) {
		this.processos = processos;
	}
	
	
	
}

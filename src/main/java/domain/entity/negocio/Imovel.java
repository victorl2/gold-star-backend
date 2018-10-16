package domain.entity.negocio;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.*;
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
	private String numeroImovel;
	
	/**
	 * Código RGI do imóvel
	 */
	@Column(name = "RGI", unique = true)
	private String rgi;
		
	/**
	 * Indica se o cano ( barbara ) foi trocado
	 */
	@Column(name = "TROCOU_BARBARA")
	private Boolean trocouBarbara;
	
	@Column(name = "TROCOU_COLUNA_AGUA")
	private Boolean trocouColuna;
	/**
	 * Contato de emergência associado ao imóvel
	 */
	@OneToOne(optional=true, cascade = CascadeType.ALL, orphanRemoval = true)
	private Pessoa contatoEmergencia;
	
	/**
	 * Pessoa que é a proprietaria do imóvel
	 */
	@ManyToOne
	@JoinColumn(unique = false, name="PROPRIETARIO")
	@JsonManagedReference
	private Proprietario donoImovel;
	
	/**
	 * Pessoa que está atualmente alugando o imovel
	 */

	@ManyToOne
	@JoinColumn(unique = false, name="LOCATARIO")
	@JsonManagedReference
	private Locatario locador;
	
	/**
	 * Lista de processos sobre o imovel
	 */
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(unique = false, name="PROCESSOS_POR_IMOVEL")
	private List<ProcessoCondominial> processos;
	
	@Column(name = "COBRANCA_BOLETO")
	private String cobrancaBoleto;
	
	@Column(name = "ACORDO_IMOVEL")
	private String acordo;
	
	public String getNumeroImovel() {
		return numeroImovel;
	}


	public void setNumeroImovel(String numeroImovel) {
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


	public void setTrocouBarbara(Boolean trocouBarbara) {
		this.trocouBarbara = trocouBarbara;
	}
	

	public Boolean getTrocouColuna() {
		return trocouColuna;
	}


	public void setTrocouColuna(Boolean trocouColuna) {
		this.trocouColuna = trocouColuna;
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

	
	public Pessoa getContatoEmergencia() {
		return contatoEmergencia;
	}


	public void setContatoEmergencia(Pessoa contatoEmergencia) {
		this.contatoEmergencia = contatoEmergencia;
	}


	public String getCobrancaBoleto() {
		return cobrancaBoleto;
	}


	public void setCobrancaBoleto(String cobrancaBoleto) {
		this.cobrancaBoleto = cobrancaBoleto;
	}


	public String getAcordo() {
		return acordo;
	}


	public void setAcordo(String acordo) {
		this.acordo = acordo;
	}
	
	
	
}

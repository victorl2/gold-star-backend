package resource.dto;

import java.util.List;
import java.util.Optional;

import domain.entity.negocio.Pessoa;
import domain.entity.negocio.ProcessoCondominial;

public class ImovelResidencialDTO {
	private List<Pessoa> moradores;
	private Boolean possuiAnimalEstimacao;
	private String numeroImovel;
	private String rgi;
	private Boolean trocouBarbara;
	private Pessoa contatoEmergencia;
	private String oidProprietario;
	private String oidLocador;
	private List<ProcessoCondominial> processos;
	
	public List<Pessoa> getMoradores() {
		return moradores;
	}
	public void setMoradores(List<Pessoa> moradores) {
		this.moradores = moradores;
	}
	public Boolean getPossuiAnimalEstimacao() {
		return possuiAnimalEstimacao;
	}
	public void setPossuiAnimalEstimacao(Boolean possuiAnimalEstimacao) {
		this.possuiAnimalEstimacao = possuiAnimalEstimacao;
	}
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
	public Pessoa getContatoEmergencia() {
		return contatoEmergencia;
	}
	public void setContatoEmergencia(Pessoa contatoEmergencia) {
		this.contatoEmergencia = contatoEmergencia;
	}
	public String getOidProprietario() {
		return oidProprietario;
	}
	public void setOidProprietario(String oidProprietario) {
		this.oidProprietario = oidProprietario;
	}
	public String getOidLocador() {
		return oidLocador;
	}
	public void setOidLocador(String oidLocador) {
		this.oidLocador = oidLocador;
	}
	public List<ProcessoCondominial> getProcessos() {
		return processos;
	}
	public void setProcessos(List<ProcessoCondominial> processos) {
		this.processos = processos;
	}
	public Optional<List<ProcessoCondominial>> getOptProcessos() {
		return Optional.ofNullable(this.getProcessos());
	}
}

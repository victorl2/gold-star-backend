package resource.dto;

import java.util.List;
import java.util.Optional;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.Pessoa;
import domain.entity.negocio.ProcessoCondominial;
import domain.entity.negocio.TipoComercio;

public class ImovelComercialDTO {
	private String id;
	private String numeroImovel;
	private String rgi;
	private Boolean trocouBarbara;
	private Pessoa contatoEmergencia;
	private ProprietarioDTO proprietario;
	private LocatarioDTO locador;
	private List<ProcessoCondominial> processos;
	private Boolean eSobreloja;
	private TipoComercio tipoLoja;
	private Boolean trocouColuna;
	private String cobrancaBoleto;
	private String nomeLoja;
	private String acordo;
	private String nomeRgi;
	
	public String getAcordo() {
		return acordo;
	}
	public void setAcordo(String acordo) {
		this.acordo = acordo;
	}
	public Boolean getTrocouColuna() {
		return trocouColuna;
	}
	public void setTrocouColuna(Boolean trocouColuna) {
		this.trocouColuna = trocouColuna;
	}
	public String getCobrancaBoleto() {
		return cobrancaBoleto;
	}
	public void setCobrancaBoleto(String cobrancaBoleto) {
		this.cobrancaBoleto = cobrancaBoleto;
	}
	public String getNomeLoja() {
		return nomeLoja;
	}
	public void setNomeLoja(String nomeLoja) {
		this.nomeLoja = nomeLoja;
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
	public ProprietarioDTO getProprietario() {
		return proprietario;
	}
	public void setProprietario(ProprietarioDTO proprietario) {
		this.proprietario = proprietario;
	}
	public LocatarioDTO getLocador() {
		return locador;
	}
	public void setLocador(LocatarioDTO locador) {
		this.locador = locador;
	}
	public List<ProcessoCondominial> getProcessos() {
		return processos;
	}
	public void setProcessos(List<ProcessoCondominial> processos) {
		this.processos = processos;
	}
	public Boolean geteSobreloja() {
		return eSobreloja;
	}
	public void seteSobreloja(Boolean eSobreloja) {
		this.eSobreloja = eSobreloja;
	}
	public TipoComercio getTipoLoja() {
		return tipoLoja;
	}
	public void setTipoLoja(TipoComercio tipoLoja) {
		this.tipoLoja = tipoLoja;
	}
	public Optional<List<ProcessoCondominial>> getOptProcessos() {
		return Optional.ofNullable(this.getProcessos());
	}
	
	public ImovelComercial build() {
		ImovelComercial imovel = new ImovelComercial();
		imovel.setContatoEmergencia(this.getContatoEmergencia());
		imovel.setNumeroImovel(this.getNumeroImovel());
		imovel.setTrocouBarbara(this.getTrocouBarbara());
		imovel.setCobrancaBoleto(this.getCobrancaBoleto());
		imovel.setNomeLoja(this.getNomeLoja());
		imovel.setTrocouColuna(this.getTrocouColuna());
		imovel.setRgi(this.getRgi());
		imovel.setProcessos(this.getProcessos());
		imovel.setTipoLoja(this.getTipoLoja());
		imovel.seteSobreloja(this.geteSobreloja());
		imovel.setAcordo(this.getAcordo());
		imovel.setNomeRgi(this.getNomeRgi());
		return imovel;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNomeRgi() {
		return nomeRgi;
	}
	public void setNomeRgi(String nomeRgi) {
		this.nomeRgi = nomeRgi;
	}
	
}

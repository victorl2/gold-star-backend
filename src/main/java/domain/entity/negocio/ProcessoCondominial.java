package domain.entity.negocio;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import domain.entity.BaseEntity;

@Entity
@Table(name = "PROCESSO_CONDOMINIAL", schema = "NEGOCIO")
@AttributeOverride(name = "ID", column = @Column(name = "PROCESSO_ID"))
public class ProcessoCondominial extends BaseEntity{
	private static final long serialVersionUID = 215332124728661546L;

	@Column(name = "CODIGO_PROCESSO")
	private String codigoProcesso;
	
	@Column(name = "DESCRICAO_PROCESSO")
	private String descricao;
	
	/**
	 * Indica se o processo se encontra ativo ou ja foi arquivado
	 */
	@Column(name = "ATIVO")
	private Boolean processoAtivo;

	public String getCodigoProcesso() {
		return codigoProcesso;
	}

	public void setCodigoProcesso(String codigoProcesso) {
		this.codigoProcesso = codigoProcesso;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getProcessoAtivo() {
		return processoAtivo == null ? false : true;
	}

	public void setProcessoAtivo(Boolean processoAtivo) {
		this.processoAtivo = processoAtivo;
	}
	
	
}

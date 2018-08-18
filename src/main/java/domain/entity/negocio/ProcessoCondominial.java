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
	
	@Column(name = "CODIGO_PROCESSO")
	private String codigoProcesso;
	
	@Column(name = "DESCRICAO_PROCESSO")
	private String descricao;
	
	/**
	 * Indica se o processo se encontra ativo ou ja foi arquivado
	 */
	@Column(name = "ATIVO")
	private boolean processoAtivo;

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

	public boolean isProcessoAtivo() {
		return processoAtivo;
	}

	public void setProcessoAtivo(boolean processoAtivo) {
		this.processoAtivo = processoAtivo;
	}
	
	
}

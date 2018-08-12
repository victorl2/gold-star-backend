package domain.entity.negocio;

import domain.entity.BaseEntity;

public class ProcessoCondominial extends BaseEntity{
	private String codigoProcesso;
	
	private String descricao;
	
	/**
	 * Indica se o processo se encontra ativo ou ja foi arquivado
	 */
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

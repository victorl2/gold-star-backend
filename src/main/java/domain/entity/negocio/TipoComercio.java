package domain.entity.negocio;

import domain.entity.BaseEntity;

public class TipoComercio extends BaseEntity{
	private String nomeDoTipoComercio;

	private String descricaoAtividadeComercial;

	public String getNomeDoTipoComercio() {
		return nomeDoTipoComercio;
	}

	public void setNomeDoTipoComercio(String nomeDoTipoComercio) {
		this.nomeDoTipoComercio = nomeDoTipoComercio;
	}

	public String getDescricaoAtividadeComercial() {
		return descricaoAtividadeComercial;
	}

	public void setDescricaoAtividadeComercial(String descricaoAtividadeComercial) {
		this.descricaoAtividadeComercial = descricaoAtividadeComercial;
	}

}

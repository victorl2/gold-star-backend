package domain.entity.negocio;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import domain.entity.BaseEntity;


@Entity
@Table(name = "TIPO_COMERCIO", schema = "NEGOCIO")
@AttributeOverride(name = "ID", column = @Column(name = "TIPO_COMERCIO_ID"))
public class TipoComercio extends BaseEntity{
	@Column(name = "NOME")
	private String nomeDoTipoComercio;

	@Column(name = "DESCRICAO")
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

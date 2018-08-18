package domain.entity.negocio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ImovelComercial extends Imovel{
	@Column(name = "SOBRELOJA")
	private boolean eSobreloja;
	
	/**
	 * Tipo de atividade comercial
	 * realizada no imovel
	 */
	@ManyToOne
	@JoinColumn(name = "TIPO_COMERCIO")
	private TipoComercio tipoLoja;

	public boolean iseSobreloja() {
		return eSobreloja;
	}

	public void seteSobreloja(boolean eSobreloja) {
		this.eSobreloja = eSobreloja;
	}

	public TipoComercio getTipoLoja() {
		return tipoLoja;
	}

	public void setTipoLoja(TipoComercio tipoLoja) {
		this.tipoLoja = tipoLoja;
	}	
	
	
	
	
}

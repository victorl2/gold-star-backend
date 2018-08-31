package domain.entity.negocio;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ImovelComercial extends Imovel{
	private static final long serialVersionUID = -2351066021769937217L;

	@Column(name = "SOBRELOJA")
	private Boolean eSobreloja;
	
	/**
	 * Tipo de atividade comercial
	 * realizada no imovel
	 */
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name = "TIPO_COMERCIO")
	private TipoComercio tipoLoja;

	public Boolean iseSobreloja() {
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

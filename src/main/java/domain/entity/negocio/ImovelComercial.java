package domain.entity.negocio;

public class ImovelComercial extends Imovel{
	private boolean eSobreloja;
	
	/**
	 * Tipo de atividade comercial
	 * realizada no imovel
	 */
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

package domain.entity.negocio;

import java.util.List;

import domain.entity.BaseEntity;

public class Relatorio extends BaseEntity{
	private List<Imovel> imoveisPresentesRelatorio;

	public List<Imovel> getImoveisPresentesRelatorio() {
		return imoveisPresentesRelatorio;
	}

	public void setImoveisPresentesRelatorio(List<Imovel> imoveisPresentesRelatorio) {
		this.imoveisPresentesRelatorio = imoveisPresentesRelatorio;
	}
	
	
}

package domain.entity.negocio;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import domain.entity.BaseEntity;

@Entity
@Table(name = "RELATORIO", schema = "NEGOCIO")
@AttributeOverride(name = "ID", column = @Column(name = "RELATORIO_ID"))
/**
 * Informações sobre imóveis agrupados utilizadas para
 * gerar relatórios. HSUAHSAUHSAUSAHU
 *
 */
public class Relatorio extends BaseEntity{
	
	@Column(name = "IMOVEIS_RELATADOS")
	@ManyToMany
	private List<Imovel> imoveisPresentesRelatorio;

	public List<Imovel> getImoveisPresentesRelatorio() {
		return imoveisPresentesRelatorio;
	}

	public void setImoveisPresentesRelatorio(List<Imovel> imoveisPresentesRelatorio) {
		this.imoveisPresentesRelatorio = imoveisPresentesRelatorio;
	}
	
	
}

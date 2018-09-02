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
	private static final long serialVersionUID = -976818436413316325L;
	
	@Column(name = "IMOVEIS_RELATADOS")
	@ManyToMany
	private List<Imovel> imoveisPresentesRelatorio;
	
	@Column(name = "NUMERO")
	private Integer numeroDeImoveis;
	
	public Integer getNumeroDeImoveis() {
		return numeroDeImoveis;
	}

	public void setNumeroDeImoveis(Integer numeroDeImoveis) {
		this.numeroDeImoveis = numeroDeImoveis;
	}

	public List<Imovel> getImoveisPresentesRelatorio() {
		return imoveisPresentesRelatorio;
	}

	public void setImoveisPresentesRelatorio(List<Imovel> imoveisPresentesRelatorio) {
		this.imoveisPresentesRelatorio = imoveisPresentesRelatorio;
	}
	
	
}

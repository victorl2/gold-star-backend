package domain.entity.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Locatario extends Pessoa {
	private static final long serialVersionUID = -4180334420040116206L;
	
	@JsonBackReference
	@OneToMany(mappedBy = "locador")
	private List<Imovel> imoveisAlugados;

	public List<Imovel> getImoveisAlugados() {
		if(imoveisAlugados == null) {
			imoveisAlugados = new ArrayList<Imovel>();	
		}
		return imoveisAlugados;
	}

	public void setImoveisAlugados(List<Imovel> imoveisAlugados) {
		this.imoveisAlugados = imoveisAlugados;
	}
	
	public void addImovel(Imovel imovel) {
		if(this.imoveisAlugados==null) {
			imoveisAlugados = new ArrayList<Imovel>();
		}
		imoveisAlugados.add(imovel);
		imovel.setLocatario(this);
	}
}

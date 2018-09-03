package services.impl;

import java.util.Comparator;
import java.util.stream.Collectors;

import javax.inject.Inject;

import domain.entity.negocio.Imovel;
import domain.entity.negocio.ImovelResidencial;
import domain.entity.negocio.Relatorio;
import domain.repository.ImovelComercialRepository;
import domain.repository.ImovelResidencialRepository;
import services.GeradorRelatorio;

public class GeradoRelatorioImpl implements GeradorRelatorio{
	
	@Inject
	private ImovelResidencialRepository imovelResidencialRepository;
	
	@Inject
	private ImovelComercialRepository imovelComercialRepository;
	
	public Relatorio gerarRelatorioTodosImoveisResidenciais() {
		
		Relatorio relatorio = new Relatorio();
		
		relatorio.setImoveisPresentesRelatorio(
				imovelResidencialRepository
					.buscarTodos()
						.stream()
							.map(residencia -> (Imovel) residencia)
								.sorted(Comparator.comparing(Imovel::getNumeroImovel))
									.collect(Collectors.toList()));
	    return relatorio;
	}
	

	public Relatorio gerarRelatorioImoveisComerciais() {
		throw new UnsupportedOperationException("Funcionalidade não implementada");
	}

	public Relatorio gerarRelatorioTodosImoveis() {
		throw new UnsupportedOperationException("Funcionalidade não implementada");
	}



	public Relatorio gerarRelatorioTodosImoveisComerciais() {
		Relatorio relatorio = new Relatorio();
		relatorio.setNumeroDeImoveis(0);
		relatorio.setImoveisPresentesRelatorio(
				imovelComercialRepository
					.buscarTodos()
						.stream()
							.map(comercio -> (Imovel) comercio)
								.sorted(Comparator.comparing(Imovel::getNumeroImovel))
									.collect(Collectors.toList()));
		relatorio.getImoveisPresentesRelatorio().forEach(imovel -> 
								relatorio.setNumeroDeImoveis(relatorio.getNumeroDeImoveis()+1));
	    return relatorio;
	}
}



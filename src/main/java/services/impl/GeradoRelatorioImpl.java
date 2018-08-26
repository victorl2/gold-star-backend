package services.impl;

import java.util.stream.Collectors;

import javax.inject.Inject;

import domain.entity.negocio.Imovel;
import domain.entity.negocio.Relatorio;
import domain.repository.ImovelResidencialRepository;
import services.GeradorRelatorio;

public class GeradoRelatorioImpl implements GeradorRelatorio{
	
	@Inject
	private ImovelResidencialRepository imovelResidencialRepository;
	
	@Inject
	private ImovelResidencialRepository imovelComercialRepository;
	
	public Relatorio gerarRelatorioTodosImoveisResidenciais() {
		
		Relatorio relatorio = new Relatorio();
		
		relatorio.setImoveisPresentesRelatorio(
				imovelResidencialRepository
					.buscarTodos()
						.stream()
							.map(residencia -> (Imovel) residencia)
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
		
		relatorio.setImoveisPresentesRelatorio(
				imovelComercialRepository
					.buscarTodos()
						.stream()
							.map(comercio -> (Imovel) comercio)
								.collect(Collectors.toList()));
	    return relatorio;
		
	}
	
}



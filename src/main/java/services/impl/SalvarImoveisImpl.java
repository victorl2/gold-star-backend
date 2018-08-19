package services.impl;

import javax.inject.Inject;

import domain.repository.ImovelComercialRepository;
import domain.repository.ImovelResidencialRepository;
import services.SalvarImoveis;

public class SalvarImoveisImpl implements SalvarImoveis {
	
	@Inject
	private ImovelResidencialRepository residencialRepository;
	
	@Inject
	private ImovelComercialRepository comercialRepository;
	
	public void salvarImovelResidencial() {
		
	}

	public void salvarImovelComercial() {
		
	}
	
}

package services.impl;

import domain.entity.negocio.ImovelResidencial;
import domain.repository.ImovelResidencialRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

import services.ImovelService;

@Stateless 
public class ImovelServiceImpl implements ImovelService{
	
	@Inject
	private ImovelResidencialRepository repository;

	public ImovelResidencial salvar(ImovelResidencial imovel) {
		return repository.salvar(imovel);
	}
	
}

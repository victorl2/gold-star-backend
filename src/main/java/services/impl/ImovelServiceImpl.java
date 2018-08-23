package services.impl;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;
import domain.repository.ImovelComercialRepository;
import domain.repository.ImovelResidencialRepository;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import services.ImovelService;

@Stateless 
public class ImovelServiceImpl implements ImovelService{
	
	@Inject
	private ImovelResidencialRepository imovelResidencialRepository;
	
	@Inject
	private ImovelComercialRepository imovelComercialRepository;

	public ImovelResidencial cadastrarImovelResidencial(ImovelResidencial imovel) {
		return imovelResidencialRepository.salvar(imovel);
	}

	public ImovelComercial cadastrarImovelComercial(ImovelComercial imovel) {
		return imovelComercialRepository.salvar(imovel);
	}

	public List<ImovelResidencial> buscarTodosImoveisResidenciais() {
		return imovelResidencialRepository.buscarTodos();
	}

	public List<ImovelComercial> buscarTodosImoveisComerciais() {
		return imovelComercialRepository.buscarTodos();
	}
	
}

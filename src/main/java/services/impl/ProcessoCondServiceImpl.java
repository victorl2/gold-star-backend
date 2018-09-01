package services.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;
import domain.entity.negocio.ProcessoCondominial;
import domain.repository.ImovelComercialRepository;
import domain.repository.ImovelResidencialRepository;
import domain.repository.ProcessoCondominialRepository;
import services.ProcessoCondService;

public class ProcessoCondServiceImpl implements ProcessoCondService{
	@Inject
	private ImovelResidencialRepository imovelResidencialRepository;
	@Inject
	private ImovelComercialRepository imovelComercialRepository;
	@Inject
	private ProcessoCondominialRepository processoRepository;
	
	public Boolean atualizarProcessoCondominial(List<ProcessoCondominial> processos, String oidImovel) {
		Optional<ImovelResidencial> imovel1 = imovelResidencialRepository.buscarPorID(oidImovel);
		if(imovel1.isPresent()) {
			if(!imovel1.get().getOptProcessos().isPresent()) {
				imovel1.get().setProcessos(processos);
			}else {
				processos.forEach(processo -> imovel1.get().getProcessos().add(processo));
			}
			processos.forEach(processo -> processoRepository.salvar(processo));
			return true;
		}else {
			Optional<ImovelComercial> imovel2 =imovelComercialRepository.buscarPorID(oidImovel);
			
		}
		return true;
	}
}

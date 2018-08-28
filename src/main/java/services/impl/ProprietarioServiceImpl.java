package services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import domain.entity.negocio.Proprietario;
import domain.repository.ProprietarioRepository;
import resource.dto.ProprietarioDTO;
import services.ProprietarioService;

@Stateless
public class ProprietarioServiceImpl implements ProprietarioService{
	@Inject
	private ProprietarioRepository proprietarioRepository;
	
	public Boolean cadastrarProprietario(ProprietarioDTO proprietarioDTO) {
		List<Proprietario> prop = proprietarioRepository.buscarTodos().stream()
							.filter( propri -> propri.getCpf()
									.equals(proprietarioDTO.getCpf()))
											.collect(Collectors.toList());
		if(prop.isEmpty()) {
			proprietarioRepository.salvar(proprietarioDTO.build());
			return true;
		}
		return false;
		
	}
}
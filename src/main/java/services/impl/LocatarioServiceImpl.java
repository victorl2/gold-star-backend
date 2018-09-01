package services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import domain.entity.negocio.Locatario;
import domain.repository.LocatarioRepository;
import resource.dto.LocatarioDTO;

public class LocatarioServiceImpl {
	@Inject
	private LocatarioRepository locatarioRepository;
	
	public Locatario cadastrarLocatario(LocatarioDTO locatarioDTO) {
		if(locatarioDTO.getCpf()==null) return new Locatario();
		List<Locatario> locatarios = locatarioRepository.buscarTodos().stream()
							.filter( locatario -> locatario.getCpf()
									.equals(locatarioDTO.getCpf()))
											.collect(Collectors.toList());
		if(locatarios.isEmpty()) {
			
			return locatarioRepository.salvar(locatarioDTO.build());
		}
		return new Locatario();
		
	}
	
	public Locatario buscaLocatarioPorCPF(String cpf) {
		if(cpf !=null) {
			List<Locatario> locatarios = locatarioRepository.buscarTodos()
					.stream().filter(locatario -> locatario.getCpf().equals(cpf))
					.collect(Collectors.toList());
			if(locatarios.isEmpty()) {
				return new Locatario();
			}else return locatarios.get(0);
		}
		return new Locatario();
	}
}

package services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import domain.entity.negocio.Locatario;
import domain.repository.LocatarioRepository;
import resource.dto.LocatarioDTO;
import services.LocatarioService;

@Stateless
public class LocatarioServiceImpl implements LocatarioService{
	
	@Inject
	private LocatarioRepository locatarioRepository;
	
	public Optional<Locatario> cadastrarLocatario(LocatarioDTO locatarioDTO) {
		
		if(locatarioDTO.getCpf()==null) return Optional.empty();
		List<Locatario> locatarios = locatarioRepository.buscarTodos().stream()
							.filter( locatario -> locatario.getCpf()
									.equals(locatarioDTO.getCpf()))
											.collect(Collectors.toList());
		if(locatarios.isEmpty()) {
			
			return Optional.of(locatarioRepository.salvar(locatarioDTO.build()));
		}
		return Optional.empty();
		
	}
	
	public Optional<Locatario> buscaLocatarioPorCPF(String cpf) {
		if(cpf !=null) {
			List<Locatario> locatarios = locatarioRepository.buscarTodos()
					.stream().filter(locatario -> locatario.getCpf().equals(cpf))
					.collect(Collectors.toList());
			if(locatarios.isEmpty()) {
				return Optional.empty();
			}else return Optional.of(locatarios.get(0));
		}
		return Optional.empty();
	}
}

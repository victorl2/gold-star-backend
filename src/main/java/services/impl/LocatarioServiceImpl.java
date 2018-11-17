package services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import domain.entity.negocio.Imovel;
import domain.entity.negocio.Locatario;
import domain.repository.LocatarioRepository;
import resource.dto.LocatarioDTO;
import services.LocatarioService;


@Stateless 
public class LocatarioServiceImpl implements LocatarioService{

	@Inject
	private LocatarioRepository locatarioRepository;
	
	public  Optional<Locatario> cadastrarLocatario(LocatarioDTO locatarioDTO, Imovel imovel) {
		if(locatarioDTO.getCpf() == null || locatarioDTO.getCpf().isEmpty() ) 
			return Optional.empty();
		Optional<Locatario> locatario = buscaLocatarioPorCPF(locatarioDTO.getCpf());
		if(locatario.isPresent()) {
			locatario.get().getImoveisAlugados().add(imovel);
			
			return Optional.of(locatarioRepository.salvar(locatario.get()));
		}
		Locatario loc = locatarioDTO.build();
		loc.getImoveisAlugados().add(imovel);
		return Optional.of(locatarioRepository.salvar(loc));
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

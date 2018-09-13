package services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import domain.entity.negocio.Pessoa;
import domain.entity.negocio.Proprietario;
import domain.repository.ProprietarioRepository;
import resource.dto.ProprietarioDTO;
import services.ProprietarioService;

@Stateless
public class ProprietarioServiceImpl implements ProprietarioService{
	@Inject
	private ProprietarioRepository proprietarioRepository;
	
	public Optional<Proprietario> cadastrarProprietario(ProprietarioDTO proprietarioDTO) {
		if(proprietarioDTO.getCpf()==null) return Optional.empty();
		List<Proprietario> prop = proprietarioRepository.buscarTodos().stream()
							.filter( propri -> propri.getCpf()
									.equals(proprietarioDTO.getCpf()))
											.collect(Collectors.toList());
		if(prop.isEmpty()) {
			
			return Optional.of(proprietarioRepository.salvar(proprietarioDTO.build()));
		}
		return Optional.empty();
		
	}
	
	public Optional<Proprietario> buscaProprietarioPorCPF(String cpf) {
		if(cpf !=null) {
			List<Proprietario> proprietarios = proprietarioRepository.buscarTodos()
					.stream().filter(proprietario -> proprietario.getCpf().equals(cpf))
					.collect(Collectors.toList());
			if(proprietarios.isEmpty()) {
				return Optional.empty();
			}else return Optional.of(proprietarios.get(0));
		}
		return Optional.empty();
	}
	
	public Optional<Proprietario> atualizarProprietario(ProprietarioDTO proprietarioDTO) {
		Optional<Proprietario> prop = proprietarioRepository.buscarPorID(proprietarioDTO.getId());
			prop.get().setCelular(proprietarioDTO.getCelular());
			prop.get().setCpf(proprietarioDTO.getCpf());
			prop.get().setEndereco(proprietarioDTO.getEndereco());
			prop.get().setNome(proprietarioDTO.getNome());
			prop.get().setTelefone(proprietarioDTO.getTelefone());
			return Optional.of(proprietarioRepository.merge(prop.get()));
	}
}

package services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import domain.entity.negocio.Imovel;
import domain.entity.negocio.Proprietario;
import domain.repository.ProprietarioRepository;
import resource.dto.ProprietarioDTO;
import services.ProprietarioService;

@Stateless
public class ProprietarioServiceImpl implements ProprietarioService{
	@Inject
	private ProprietarioRepository proprietarioRepository;	
	
	public Optional<Proprietario> cadastrarProprietario(ProprietarioDTO proprietarioDTO, Imovel imovel){
		if(proprietarioDTO.getCpf()==null || proprietarioDTO.getCpf().isEmpty()) 
			return Optional.empty();
		Optional<Proprietario> prop = buscaProprietarioPorCPF(proprietarioDTO.getCpf());
		if(prop.isPresent()) {
			prop.get().getImoveis().add(imovel);
			return Optional.of(proprietarioRepository.salvar(prop.get()));
		}
		Proprietario proprietario = proprietarioDTO.build();
		proprietario.getImoveis().add(imovel);
		return Optional.of(proprietarioRepository.salvar(proprietario));
	}
	
	public Optional<Proprietario> buscaProprietarioPorCPF(String cpf) {
		if(cpf !=null) {
			List<Proprietario> proprietarios = proprietarioRepository.buscarTodos()
					.stream().filter(proprietario -> proprietario.getCpf().equals(cpf))
					.collect(Collectors.toList());
			if(proprietarios.isEmpty()) {
				return Optional.empty();
			}else {
				if(proprietarios.size()>1) {
					throw new RuntimeException("Existe mais de 1 proprietario com o cpf informado");
				}
				return Optional.of(proprietarios.get(0));
			}
		}
		return Optional.empty();
	}
}

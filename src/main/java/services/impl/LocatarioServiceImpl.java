package services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;
import domain.entity.negocio.Locatario;
import domain.repository.ImovelComercialRepository;
import domain.repository.ImovelResidencialRepository;
import domain.repository.LocatarioRepository;
import resource.dto.LocatarioDTO;
import services.LocatarioService;


@Stateless 
public class LocatarioServiceImpl implements LocatarioService{

	@Inject
	private LocatarioRepository locatarioRepository;
	
	@Inject
	private ImovelComercialRepository imovelComercialRepository;
	
	@Inject 
	private ImovelResidencialRepository imovelResidencialRepository;
	
	public Optional<Locatario> cadastrarLocatario(LocatarioDTO locatarioDTO) {	
		if(locatarioDTO.getCpf() == null || locatarioDTO.getCpf().isEmpty() ) 
			return Optional.empty();
	
		//Removida a verificação de unicidade de cpf temporariamente
		return Optional.of(locatarioRepository.salvar(locatarioDTO.build()));
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
	
	public Optional<Locatario> atualizarLocatario(LocatarioDTO locatarioDTO, String idImovel){
		if(locatarioDTO.getCpf() == null || locatarioDTO.getCpf().isEmpty()) 
			return Optional.empty();
		
		Optional<ImovelComercial> imovelComercial = imovelComercialRepository.buscarPorID(idImovel);

		if(imovelComercial.isPresent() && imovelComercial.get().getLocatario() == null) {
			return this.cadastrarLocatario(locatarioDTO);
		}
		
		if(imovelComercial.isPresent()) {
			imovelComercial.get().getLocatario().setCelular(locatarioDTO.getCelular());
			imovelComercial.get().getLocatario().setTelefone(locatarioDTO.getTelefone());
			imovelComercial.get().getLocatario().setCpf(locatarioDTO.getCpf());
			imovelComercial.get().getLocatario().setNome(locatarioDTO.getNome());
			return Optional.ofNullable(locatarioRepository.salvar(imovelComercial.get().getLocatario()));
		}
		
		Optional<ImovelResidencial> imovelResidencial = imovelResidencialRepository.buscarPorID(idImovel);
			imovelResidencial.get().getLocatario().setCelular(locatarioDTO.getCelular());
			imovelResidencial.get().getLocatario().setTelefone(locatarioDTO.getTelefone());
			imovelResidencial.get().getLocatario().setCpf(locatarioDTO.getCpf());
			imovelResidencial.get().getLocatario().setNome(locatarioDTO.getNome());
			return Optional.ofNullable(locatarioRepository.salvar(imovelResidencial.get().getLocatario()));
	}
	
}

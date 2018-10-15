package services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;
import domain.entity.negocio.Proprietario;
import domain.repository.ImovelComercialRepository;
import domain.repository.ImovelResidencialRepository;
import domain.repository.ProprietarioRepository;
import resource.dto.ProprietarioDTO;
import services.ProprietarioService;

@Stateless
public class ProprietarioServiceImpl implements ProprietarioService{
	@Inject
	private ProprietarioRepository proprietarioRepository;
	
	@Inject
	private ImovelResidencialRepository imovelResidencialRepository;
	
	@Inject
	private ImovelComercialRepository imovelComercialRepository;
	
	public Optional<Proprietario> cadastrarProprietario(ProprietarioDTO proprietarioDTO) {
		if(proprietarioDTO.getCpf()==null || proprietarioDTO.getCpf().isEmpty()) 
			return Optional.empty();
		
		//Removida a verificação de unicidade para cpf temporariamente
		return Optional.of(proprietarioRepository.salvar(proprietarioDTO.build()));
		
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
	
	public Optional<Proprietario> atualizarProprietario(ProprietarioDTO proprietarioDTO, String idImovel){
			if(proprietarioDTO.getCpf()==null || proprietarioDTO.getCpf().isEmpty()) 
				return Optional.empty();
			
			Optional<ImovelComercial> imovelComercial = imovelComercialRepository.buscarPorID(idImovel);
	
			if(imovelComercial.isPresent() && imovelComercial.get().getDonoImovel() == null) {
				return this.cadastrarProprietario(proprietarioDTO);
			}
			
			if(imovelComercial.isPresent()) {
				imovelComercial.get().getDonoImovel().setEmail(proprietarioDTO.getEmail());
				imovelComercial.get().getDonoImovel().setCelular(proprietarioDTO.getCelular());
				imovelComercial.get().getDonoImovel().setTelefone(proprietarioDTO.getTelefone());
				imovelComercial.get().getDonoImovel().setCpf(proprietarioDTO.getCpf());
				imovelComercial.get().getDonoImovel().setEndereco(proprietarioDTO.getEndereco());
				imovelComercial.get().getDonoImovel().setNome(proprietarioDTO.getNome());
				return Optional.ofNullable(proprietarioRepository.salvar(imovelComercial.get().getDonoImovel()));
			}
			
			Optional<ImovelResidencial> imovelResidencial = imovelResidencialRepository.buscarPorID(idImovel);
				imovelResidencial.get().getDonoImovel().setEmail(proprietarioDTO.getEmail());
				imovelResidencial.get().getDonoImovel().setCelular(proprietarioDTO.getCelular());
				imovelResidencial.get().getDonoImovel().setTelefone(proprietarioDTO.getTelefone());
				imovelResidencial.get().getDonoImovel().setCpf(proprietarioDTO.getCpf());
				imovelResidencial.get().getDonoImovel().setEndereco(proprietarioDTO.getEndereco());
				imovelResidencial.get().getDonoImovel().setNome(proprietarioDTO.getNome());
				return Optional.ofNullable(proprietarioRepository.salvar(imovelResidencial.get().getDonoImovel()));
	}
}

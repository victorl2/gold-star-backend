package services.impl;

import java.util.List;
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
	
	public Proprietario cadastrarProprietario(ProprietarioDTO proprietarioDTO) {
		if(proprietarioDTO.getCpf()==null) return new Proprietario();
		List<Proprietario> prop = proprietarioRepository.buscarTodos().stream()
							.filter( propri -> propri.getCpf()
									.equals(proprietarioDTO.getCpf()))
											.collect(Collectors.toList());
		if(prop.isEmpty()) {
			
			return proprietarioRepository.salvar(proprietarioDTO.build());
		}
		return new Proprietario();
		
	}
	
	public Pessoa cadastrarPessoaComProprietario(Pessoa pessoa) {
		Proprietario proprietario = new Proprietario();
		proprietario.setCelular(pessoa.getCelular());
		proprietario.setNome(pessoa.getNome());
		proprietario.setTelefone(pessoa.getTelefone());
		pessoa = (Pessoa) proprietarioRepository.salvar(proprietario);
		
		return pessoa;
	}
	
	public Proprietario buscaProprietarioPorCPF(String cpf) {
		if(cpf !=null) {
			List<Proprietario> proprietarios = proprietarioRepository.buscarTodos()
					.stream().filter(proprietario -> proprietario.getCpf().equals(cpf))
					.collect(Collectors.toList());
			if(proprietarios.isEmpty()) {
				return new Proprietario();
			}else return proprietarios.get(0);
		}
		return new Proprietario();
	}
}

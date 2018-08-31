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
	
	public Boolean cadastrarProprietario(ProprietarioDTO proprietarioDTO) {
		if(proprietarioDTO.getCpf()==null) return false;
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
	
	public Pessoa cadastrarPessoaComProprietario(Pessoa pessoa) {
		Proprietario proprietario = new Proprietario();
		proprietario.setCelular(pessoa.getCelular());
		proprietario.setNome(pessoa.getNome());
		proprietario.setTelefone(pessoa.getTelefone());
		pessoa = (Pessoa) proprietarioRepository.salvar(proprietario);
		
		return pessoa;
	}
	
	public Boolean buscaProprietarioPorCPF(String cpf) {
		if(cpf !=null) {
			if(proprietarioRepository.buscarTodos()
					.stream().filter(proprietario -> proprietario.getCpf().equals(cpf))
						.collect(Collectors.toList()).isEmpty()) {
				return false;
			}else return true;
		}
		return false;
	}
}

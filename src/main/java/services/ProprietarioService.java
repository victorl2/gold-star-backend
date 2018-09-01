package services;

import java.util.Optional;

import domain.entity.negocio.Pessoa;
import domain.entity.negocio.Proprietario;
import resource.dto.ProprietarioDTO;

public interface ProprietarioService {
	public Optional<Proprietario> cadastrarProprietario(ProprietarioDTO proprietarioDTO);
	
	public Pessoa cadastrarPessoaComProprietario(Pessoa pessoa);

	public Optional<Proprietario> buscaProprietarioPorCPF(String cpf);
}

package services;

import domain.entity.negocio.Pessoa;
import domain.entity.negocio.Proprietario;
import resource.dto.ProprietarioDTO;

public interface ProprietarioService {
	public Proprietario cadastrarProprietario(ProprietarioDTO proprietarioDTO);
	
	public Pessoa cadastrarPessoaComProprietario(Pessoa pessoa);

	public Proprietario buscaProprietarioPorCPF(String cpf);
}

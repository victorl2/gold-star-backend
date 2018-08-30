package services;

import domain.entity.negocio.Pessoa;
import resource.dto.ProprietarioDTO;

public interface ProprietarioService {
	public Boolean cadastrarProprietario(ProprietarioDTO proprietarioDTO);
	
	public Pessoa cadastrarPessoaComProprietario(Pessoa pessoa);
}

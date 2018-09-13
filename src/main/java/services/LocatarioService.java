package services;

import java.util.Optional;

import domain.entity.negocio.Locatario;
import resource.dto.LocatarioDTO;

public interface LocatarioService {
	public Optional<Locatario> cadastrarLocatario(LocatarioDTO locatarioDTO);
	
	public Optional<Locatario> buscaLocatarioPorCPF(String cpf);

	public Optional<Locatario> atualizarLocatario(LocatarioDTO locatarioDTO);
}

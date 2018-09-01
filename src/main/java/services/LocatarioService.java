package services;

import domain.entity.negocio.Locatario;
import resource.dto.LocatarioDTO;

public interface LocatarioService {
	public Locatario cadastrarLocatario(LocatarioDTO locatarioDTO);
	
	public Locatario buscaLocatarioPorCPF(String cpf);
}

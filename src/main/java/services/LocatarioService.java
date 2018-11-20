package services;

import java.util.Optional;

import domain.entity.negocio.Imovel;
import domain.entity.negocio.Locatario;
import resource.dto.LocatarioDTO;

public interface LocatarioService {
	public Optional<Locatario> cadastrarLocatario(LocatarioDTO locatarioDTO, Imovel novoImovel);
	
	public Optional<Locatario> buscaLocatarioPorCPF(String cpf);
}

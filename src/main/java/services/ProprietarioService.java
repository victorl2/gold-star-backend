package services;

import java.util.Optional;

import domain.entity.negocio.Imovel;
import domain.entity.negocio.Proprietario;
import resource.dto.ProprietarioDTO;

public interface ProprietarioService {

	public Optional<Proprietario> buscaProprietarioPorCPF(String cpf);

	public Optional<Proprietario> cadastrarProprietario(ProprietarioDTO proprietario, Imovel imovel);
}

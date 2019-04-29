package domain.repository;

import java.util.Optional;

import domain.entity.negocio.Locatario;

public interface LocatarioRepository extends Repository<Locatario> {
	Optional<Locatario> buscarLocatarioPorCPF(final String cpf);
}

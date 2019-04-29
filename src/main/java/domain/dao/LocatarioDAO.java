package domain.dao;

import java.util.List;
import java.util.Optional;

import domain.entity.negocio.Locatario;
import domain.repository.LocatarioRepository;

public class LocatarioDAO extends BaseDAO<Locatario> implements LocatarioRepository{

	@Override
	public Optional<Locatario> buscarLocatarioPorCPF(String cpf) {
		 List<Locatario> locatarios = getEntityManager()
				.createQuery("SELECT locatario FROM Locatario locatario WHERE locatario.cpf = :cpf")
					.setParameter("cpf", cpf)
						.getResultList();
		 
		 return locatarios.isEmpty() ? Optional.empty() : Optional.ofNullable(locatarios.get(0));
	}

}

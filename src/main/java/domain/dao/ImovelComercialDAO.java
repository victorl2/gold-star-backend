package domain.dao;

import java.util.List;

import domain.entity.negocio.ImovelComercial;
import domain.repository.ImovelComercialRepository;

/**
 * Implementação das definições de persistencia para a classe <b>ImovelComercial</b>
 */
public class ImovelComercialDAO extends BaseDAO<ImovelComercial> implements ImovelComercialRepository{

	@SuppressWarnings("unchecked")
	@Override
	public List<ImovelComercial> buscarImovelComercialPorNumero(String numeroImovel) {
		return getEntityManager()
			.createQuery("SELECT residencia FROM ImovelComercial residencia WHERE residencia.numeroImovel LIKE :numeroResidencia")
				.setParameter("numeroResidencia", "%" + numeroImovel + "%")
					.setMaxResults(MAX_RESULTS)
						.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ImovelComercial> buscarImovelComercialPorRGI(String RGI) {
		return getEntityManager()
				.createQuery("SELECT residencia FROM ImovelComercial residencia WHERE residencia.rgi LIKE :rgi")
					.setParameter("rgi", "%" + RGI + "%")
						.setMaxResults(MAX_RESULTS)
							.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ImovelComercial> buscarImovelComercialPorNomeLocatario(String nomeLocatario) {
		return getEntityManager()
				.createQuery("SELECT residencia FROM ImovelComercial residencia WHERE residencia.locador.nome LIKE :locatario")
					.setParameter("locatario", "%" + nomeLocatario + "%")
						.setMaxResults(MAX_RESULTS)
							.getResultList();
	}

}

package domain.dao;

import java.util.List;

import domain.entity.negocio.ImovelResidencial;
import domain.repository.ImovelResidencialRepository;

/**
 * Implementação das definições de persistencia para a classe <b>ImovelResidencial</b>
 */
public class ImovelResidencialDAO extends BaseDAO<ImovelResidencial> implements ImovelResidencialRepository{	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ImovelResidencial> buscarImovelResidencialPorNumero(String numeroImovel) {
		return getEntityManager()
				.createQuery("SELECT residencia FROM ImovelResidencial residencia WHERE residencia.numeroImovel LIKE :numeroResidencia")
					.setParameter("numeroResidencia", "%" + numeroImovel + "%")
						.setMaxResults(MAX_RESULTS)
							.getResultList();
	}

	@Override
	public List<ImovelResidencial> buscarImovelResidencialPorRGI(String RGI) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ImovelResidencial> buscarImovelResidencialPorNomeLocatario(String nomeLocatario) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

package domain.repository;

import java.util.List;

import domain.entity.negocio.ImovelResidencial;

/**
 * Defini��es das opera��es de persistencia para a classe <b>ImovelResidencial</b>
 */
public interface ImovelResidencialRepository extends Repository<ImovelResidencial> {
	
	/**
	 * Busca todos os im�veis que possuem o n�mero informado
	 * @param numeroImovel
	 * @return {@link List} de {@link ImovelResidencial}
	 */
	List<ImovelResidencial> buscarImovelResidencialPorNumero(final String numeroImovel);
	
	/**
	 * Busca todos os imoveis com o rgi informado
	 * @param RGI para filtragem
	 * @return {@link List} de {@link ImovelResidencial}
	 */
	List<ImovelResidencial> buscarImovelResidencialPorRGI(final String RGI);
	
	/**
	 * Busca todos os imoveis com o nome de locatario informado
	 * @param nomeLocatario para filtragem
	 * @return {@link List} de {@link ImovelResidencial}
	 */
	List<ImovelResidencial> buscarImovelResidencialPorNomeLocatario(final String nomeLocatario);
}

package domain.repository;

import java.util.List;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;

/**
 * Defini��es das opera��es de persistencia para a classe <b>ImovelComercial</b>
 */
public interface ImovelComercialRepository extends Repository<ImovelComercial> {
	/**
	 * Busca todos os im�veis comerciais que possuem o n�mero informado
	 * @param numeroImovel
	 * @return {@link List} de {@link ImovelResidencial}
	 */
	List<ImovelComercial> buscarImovelComercialPorNumero(final String numeroImovel);
	
	/**
	 * Busca todos os imoveis comerciais com o rgi informado
	 * @param RGI para filtragem
	 * @return {@link List} de {@link ImovelComercial}
	 */
	List<ImovelComercial> buscarImovelComercialPorRGI(final String RGI);
	
	/**
	 * Busca todos os imoveis comerciais com o nome de locatario informado
	 * @param nomeLocatario para filtragem
	 * @return {@link List} de {@link ImovelComercial}
	 */
	List<ImovelComercial> buscarImovelComercialPorNomeLocatario(final String nomeLocatario);
}

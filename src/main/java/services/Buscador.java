package services;

import java.util.List;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;

/**
 * Interface definindo os metodos necessarios para realizar a busca de imoveis
 *
 */
public interface Buscador {
	/**
	 * Busca todos os imoveis residenciais cadastrados
	 * 
	 * @return Lista contendo os imoveis residencias
	 */
	public List<ImovelResidencial> buscarTodosImoveisResidenciais();

	/**
	 * Busca todos os imoveis comerciais cadastrados
	 * 
	 * @return Lista contendo os imoveis comerciais
	 */
	public List<ImovelComercial> buscarTodosImoveisComerciais();
}

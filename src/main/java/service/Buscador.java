package service;

import java.util.List;

import domain.entity.negocio.Imovel;

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
	public List<Imovel> buscarTodosImoveisResidenciais();

	/**
	 * Busca todos os imoveis comerciais cadastrados
	 * 
	 * @return Lista contendo os imoveis comerciais
	 */
	public List<Imovel> buscarTodosImoveisComerciais();
}

package service;

import java.util.List;

import domain.entity.structure.Imovel;

public interface Buscador {
	/**
	 * Busca todos os imoveis residenciais cadastrados
	 * @return Lista contendo os imoveis residencias
	 */
	public List<Imovel> buscarTodosImoveisResidenciais();
	
	/**
	 * Busca todos os imoveis comerciais cadastrados
	 * @return Lista contendo os imoveis comerciais
	 */
	public List<Imovel> buscarTodosImoveisComerciais();
}

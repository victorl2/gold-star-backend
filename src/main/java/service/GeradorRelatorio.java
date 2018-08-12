package service;

import domain.entity.structure.Relatorio;

/**
 * Interface definindo os metodos necessarios para gerar relatorios sobre os
 * imoveis
 * 
 * cadastrados
 *
 */
public interface GeradorRelatorio {

	/**
	 * Gera relat�rio para todos os imoveis residenciais cadastrados
	 * 
	 * @return Relatorio gerado contemplando todos os imoveis residenciais
	 */
	public Relatorio gerarRelatorioImoveisResidenciais();

	/**
	 * Gera relat�rio para todos os imoveis comerciais cadastrados
	 * 
	 * @return Relatorio gerado contemplando todos os imoveis comerciais
	 */
	public Relatorio gerarRelatorioImoveisComerciais();

	/**
	 * Gera relat�rio para todos os imoveis cadastrados
	 * 
	 * @return Relatorio gerado contemplando todos os imoveis
	 */
	public Relatorio gerarRelatorioTodosImoveis();
}

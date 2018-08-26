package services;

import java.io.FileNotFoundException;
import java.io.IOException;

import domain.entity.negocio.Relatorio;

/**
 * Interface definindo os metodos necessarios para gerar relatorios sobre os
 * imoveis
 * 
 * cadastrados
 *
 */
public interface GeradorRelatorio {

	/**
	 * Gera relatório para todos os imoveis residenciais cadastrados
	 * 
	 * @return Relatorio gerado contemplando todos os imoveis residenciais
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public Relatorio gerarRelatorioTodosImoveisResidenciais() throws FileNotFoundException, IOException;

	/**
	 * Gera relatório para todos os imoveis comerciais cadastrados
	 * 
	 * @return Relatorio gerado contemplando todos os imoveis comerciais
	 */
	public Relatorio gerarRelatorioImoveisComerciais();

	/**
	 * Gera relatório para todos os imoveis cadastrados
	 * 
	 * @return Relatorio gerado contemplando todos os imoveis
	 */
	public Relatorio gerarRelatorioTodosImoveis();
}

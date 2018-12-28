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
	public Relatorio gerarRelatorioTodosImoveisResidenciais();

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

	public Relatorio gerarRelatorioTodosImoveisComerciais();
	
	public Relatorio gerarRelatorioImovelResidencial(String numero);
	
	public Relatorio gerarRelatorioImovelComercial(String numero);
	
	public boolean gerarPDFTodosImoveisResidenciais(String path, Relatorio relatorio);

	public boolean gerarPDFTodosImoveisComerciais(String path, Relatorio relatorio);

	public Relatorio gerarRelatorioImoveisBarbara();

	public Relatorio gerarRelatorioImoveisColuna();

	public Relatorio gerarRelatorioImoveisProcessos();

	public Relatorio gerarRelatorioImoveisRgi();

	public boolean gerarPDFTodosImoveisFiltro(String caminhoPadrao, Relatorio relatorio, String filtro);

}

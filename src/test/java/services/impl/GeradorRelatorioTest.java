package services.impl;

import org.junit.Before;
import org.junit.Test;

import services.GeradorRelatorio;

/**
 * Testes na geracao de relatorios
 *
 */
public class GeradorRelatorioTest {
	
	private GeradorRelatorio gerador;
	
	@Before
	public void setup() {
		gerador = new GeradorRelatorioImpl();
	}
	
	@Test
	/**
	 * Verifica se um relatorio geral foi gerado corretamente
	 */
	public void gerarRelatorioGeral() {
		gerador.gerarRelatorioTodosImoveis();
	}
}

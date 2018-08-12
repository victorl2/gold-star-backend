package services.impl;

import org.junit.Before;
import org.junit.Test;

import services.Buscador;

public class BuscadorTest {
	
	private Buscador buscador;
	
	@Before
	public void setup() {
		buscador = new BuscadorImpl();
	}
	
	@Test
	/**
	 * Verifica se todos os imoveis residenciais sao informados corretamente
	 */
	public void buscaTodosImoveisResidenciais() {
		buscador.buscarTodosImoveisResidenciais();
	}
	
	
}

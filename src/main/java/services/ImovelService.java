package services;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.Relatorio;
import resource.dto.ImovelComercialDTO;
import resource.dto.ImovelResidencialDTO;

public interface ImovelService extends Buscador{

	/**
	 * Cadastra o im�vel residencial na base de dados, caso o im�vel j� exista no
	 * sistema este ser� atualizado
	 * 
	 * @param imovelResidencial a ser inserido ou atualizado
	 * @return ImovelResidencial ap�s ser persistido na base de dados
	 */
	public Boolean cadastrarImovelResidencial(ImovelResidencialDTO imovelResidencial);
	
	/**
	 * Cadastra o im�vel comercial na base de dados, caso o im�vel j� exista no
	 * sistema este ser� atualizado
	 * 
	 * @param imovelComercial a ser inserido ou atualizado
	 * @return ImovelComercial ap�s ser persistido na base de dados
	 */
	public Boolean cadastrarImovelComercial(ImovelComercialDTO imovel);
	
	public boolean gerarRelatorioTodosImoveisResidenciais(String path, Relatorio relatorio);
	
	public boolean gerarRelatorioTodosImoveisComerciais(String path, Relatorio relatorio);
}

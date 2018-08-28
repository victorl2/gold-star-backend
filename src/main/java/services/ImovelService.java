package services;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;
import domain.entity.negocio.Relatorio;
import resource.dto.ImovelResidencialDTO;

public interface ImovelService extends Buscador{

	/**
	 * Cadastra o imóvel residencial na base de dados, caso o imóvel já exista no
	 * sistema este será atualizado
	 * 
	 * @param imovelResidencial a ser inserido ou atualizado
	 * @return ImovelResidencial após ser persistido na base de dados
	 */
	public ImovelResidencial cadastrarImovelResidencial(ImovelResidencialDTO imovelResidencial);
	
	/**
	 * Cadastra o imóvel comercial na base de dados, caso o imóvel já exista no
	 * sistema este será atualizado
	 * 
	 * @param imovelComercial a ser inserido ou atualizado
	 * @return ImovelComercial após ser persistido na base de dados
	 */
	public ImovelComercial cadastrarImovelComercial(ImovelComercial imovelComercial);
	
	public boolean gerarRelatorioTodosImoveisResidenciais(String path, Relatorio relatorio);
	
	public boolean gerarRelatorioTodosImoveisComerciais(String path, Relatorio relatorio);
}

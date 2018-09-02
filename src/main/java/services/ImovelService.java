package services;

import java.util.List;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;
import domain.entity.negocio.Relatorio;
import resource.dto.ImovelComercialDTO;
import resource.dto.ImovelResidencialDTO;

public interface ImovelService extends Buscador{

	/**
	 * Cadastra o imóvel residencial na base de dados, caso o imóvel já exista no
	 * sistema este será atualizado
	 * 
	 * @param imovelResidencial a ser inserido ou atualizado
	 * @return ImovelResidencial após ser persistido na base de dados
	 */
	public Boolean cadastrarImovelResidencial(ImovelResidencialDTO imovelResidencial);
	
	/**
	 * Cadastra o imóvel comercial na base de dados, caso o imóvel já exista no
	 * sistema este será atualizado
	 * 
	 * @param imovelComercial a ser inserido ou atualizado
	 * @return ImovelComercial após ser persistido na base de dados
	 */
	public Boolean cadastrarImovelComercial(ImovelComercialDTO imovel);
	
	public boolean gerarRelatorioTodosImoveisResidenciais(String path, Relatorio relatorio);
	
	public boolean gerarRelatorioTodosImoveisComerciais(String path, Relatorio relatorio);

	public List<ImovelResidencial> buscarImovelResidencialPorRGI(String rgi);

	public List<ImovelResidencial> buscarImovelResidencialPorNumero(String numero);

	public List<ImovelResidencial> buscarImovelResidencialPorNomeLocatario(String nome);
	
	public List<ImovelComercial> buscarImovelComercialPorRGI(String rgi);

	public List<ImovelComercial> buscarImovelComercialPorNumero(String numero);

	public List<ImovelComercial> buscarImovelComercialPorNomeLocatario(String nome);
}

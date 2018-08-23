package services;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;

public interface ImovelService extends Buscador{

	/**
	 * Cadastra o imóvel residencial na base de dados, caso o imóvel já exista no
	 * sistema este será atualizado
	 * 
	 * @param imovelResidencial a ser inserido ou atualizado
	 * @return ImovelResidencial após ser persistido na base de dados
	 */
	public ImovelResidencial cadastrarImovelResidencial(ImovelResidencial imovelResidencial);
	
	/**
	 * Cadastra o imóvel comercial na base de dados, caso o imóvel já exista no
	 * sistema este será atualizado
	 * 
	 * @param imovelComercial a ser inserido ou atualizado
	 * @return ImovelComercial após ser persistido na base de dados
	 */
	public ImovelComercial cadastrarImovelComercial(ImovelComercial imovelComercial);
}

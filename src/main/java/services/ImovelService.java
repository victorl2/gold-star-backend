package services;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;

public interface ImovelService extends Buscador{

	/**
	 * Cadastra o im�vel residencial na base de dados, caso o im�vel j� exista no
	 * sistema este ser� atualizado
	 * 
	 * @param imovelResidencial a ser inserido ou atualizado
	 * @return ImovelResidencial ap�s ser persistido na base de dados
	 */
	public ImovelResidencial cadastrarImovelResidencial(ImovelResidencial imovelResidencial);
	
	/**
	 * Cadastra o im�vel comercial na base de dados, caso o im�vel j� exista no
	 * sistema este ser� atualizado
	 * 
	 * @param imovelComercial a ser inserido ou atualizado
	 * @return ImovelComercial ap�s ser persistido na base de dados
	 */
	public ImovelComercial cadastrarImovelComercial(ImovelComercial imovelComercial);
}

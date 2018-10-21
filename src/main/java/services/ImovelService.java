package services;

import java.util.List;
import java.util.Optional;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;
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

	public List<ImovelResidencial> buscarImovelResidencialPorRGI(String rgi);

	public List<ImovelResidencial> buscarImovelResidencialPorNumero(String numero);

	public List<ImovelResidencial> buscarImovelResidencialPorNomeLocatario(String nome);
	
	public List<ImovelComercial> buscarImovelComercialPorRGI(String rgi);

	public List<ImovelComercial> buscarImovelComercialPorNumero(String numero);

	public List<ImovelComercial> buscarImovelComercialPorNomeLocatario(String nome);

	public Boolean atualizarImovelComercial(ImovelComercialDTO imovelComercial);

	public Boolean atualizarImovelResidencial(ImovelResidencialDTO imovelResidencial);

	public Optional<ImovelResidencial> recuperarImovelResidencialPorNumero(String numero);

	public Optional<ImovelComercial> recuperarImovelComercialPorNumero(String numero);
	
	public void removerImovelResidencial(String numero);
	
	public void removerImovelComercial(String numero);
}

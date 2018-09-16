package resource;

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.Relatorio;
import resource.dto.ImovelComercialDTO;
import services.GeradorRelatorio;
import services.ImovelService;


@Path("/imovel-comercial")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
/**
 * Endpoints para imoveis comericias
 *
 */
public class ImovelComercialResource {
	private Logger LOGGER = Logger.getLogger(getClass().getName());
	
	@Inject
	private GeradorRelatorio gerarRelatorio;
	
	@Inject
	private ImovelService imovelService;
	
	@POST
	@Path("gerar-relatorio-comercial")
	public Response gerarRelatorioTodosImoveisComerciais(String path) {
		
		final String usuarioPC = System.getProperty("user.name");
		final String caminhoPadrao = "C:\\Users\\" + usuarioPC + "\\Documents\\gerenciador-goldstar\\";
		
		
		File pasta = new File(caminhoPadrao);
		LOGGER.log(Level.INFO, "pasta:" + caminhoPadrao);
		
		if(!new File(caminhoPadrao).isDirectory()) {
			pasta.mkdir();
		}
		
		Relatorio relatorio = gerarRelatorio.gerarRelatorioTodosImoveisComerciais();
		if(relatorio.getImoveisPresentesRelatorio().isEmpty()) return Response.status(412).entity("Relatorio está vazio.").build(); 
		if(imovelService.gerarRelatorioTodosImoveisComerciais(caminhoPadrao, relatorio)) {
			return Response.ok("relatório gerado com sucesso em ".concat(caminhoPadrao)).build();
		}
		return Response.status(412).entity("Falha ao tentar encontrar caminho para gerar o relatório: Relatório não gerado.").build();
	}
	
	@POST
	@Path("/cadastrar-imovel-comercial")
	public Response cadastrarImoveisComerciais(ImovelComercialDTO imovelComercial) {
		if(imovelComercial.getNumeroImovel()!=null) { 
			if(imovelService.cadastrarImovelComercial(imovelComercial)) {
				return Response.ok("Cadastro realizado com sucesso").build();
			}
		}
		return Response.status(412).entity("Cadastro não realizado, imovel ja cadastrado ou numero vazio.").build();
	}
	
	@GET
	@Path("busca-rgi/{rgi}")
	public Response buscaImovelComercialPorRGI(@PathParam("rgi") String rgi) {
		List<ImovelComercial> imoveis = imovelService.buscarImovelComercialPorRGI(rgi);
		if(!imoveis.isEmpty()) 
			return Response.ok("Imovel Comercial encontrado").entity(imoveis).build();
		
		return Response.status(412).build();
	}
	
	@GET
	@Path("busca-numero/{numero}")
	public Response buscaImovelComercialPorNumero(@PathParam("numero") String numero) {
		List<ImovelComercial> imoveis = imovelService.buscarImovelComercialPorNumero(numero);
		if(!imoveis.isEmpty()) 
			return Response.ok("Imovel Comercial encontrado").entity(imoveis).build();
		
		return Response.status(412).build();
	}
	
	@GET
	@Path("busca-nomeLocatario/{nome}")
	public Response buscaImovelComercialPorNomeLocatario(@PathParam("nome") String nome) {
		List<ImovelComercial> imoveis = imovelService.buscarImovelComercialPorNomeLocatario(nome);
		if(!imoveis.isEmpty()) 
			return Response.ok("Imovel Comercial encontrado").entity(imoveis).build();
		
		return Response.status(412).build();
	}

}

package resource;

import java.io.File;
import java.util.List;
import java.util.Optional;
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
	
	@GET
	@Path("gerar-relatorio-comercial")
	public Response gerarRelatorioTodosImoveisComerciais() {
		
		final String usuarioPC = System.getProperty("user.name");
		final String caminhoPadrao = "C:\\Users\\" + usuarioPC + "\\Documents\\gerenciador-goldstar\\";
		
		
		File pasta = new File(caminhoPadrao);
		LOGGER.log(Level.INFO, "pasta:" + caminhoPadrao);
		
		if(!new File(caminhoPadrao).isDirectory()) {
			pasta.mkdir();
		}
		
		Relatorio relatorio = gerarRelatorio.gerarRelatorioTodosImoveisComerciais();
		if(relatorio.getImoveisPresentesRelatorio().isEmpty()) return Response.status(412).entity("Relatorio está vazio.").build(); 
		if(gerarRelatorio.gerarPDFTodosImoveisComerciais(caminhoPadrao, relatorio)) {
			return Response.ok("relatório gerado com sucesso em ".concat(caminhoPadrao)).build();
		}
		return Response.status(412).entity("Falha ao tentar encontrar caminho para gerar o relatório: Relatório não gerado.").build();
	}
	
	@POST
	@Path("/cadastrar-imovel-comercial")
	public Response cadastrarImoveisComerciais(ImovelComercialDTO imovelComercial) {
		if(imovelComercial.getNumeroImovel()==null || imovelComercial.getNumeroImovel().isEmpty()) 
			return Response.status(412).entity("Cadastro não realizado, número vazio.").build();
			if(imovelService.cadastrarImovelComercial(imovelComercial)) {
				return Response.ok("Cadastro realizado com sucesso").build();
		}
		return Response.status(412).entity("Cadastro não realizado.").build();
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
	
	@POST
	@Path("atualizar-imovel-comercial")
	public Response atualizarImoveisComerciais(ImovelComercialDTO imovelComercial) {
		if(imovelComercial.getNumeroImovel()!=null) { 
			if(imovelService.atualizarImovelComercial(imovelComercial)) {
				return Response.ok("Atualizacao realizada com sucesso").build();
			}
		}
		return Response.status(412).entity("Atualizacao não realizada.").build();
	}
	
	@GET
	@Path("recuperar-imovelComercial-completo/{numero}")
	public Response recuperarImovelCompleto(@PathParam("numero") String numero) {
		Optional<ImovelComercial> imovelComercial = imovelService.recuperarImovelComercialPorNumero(numero);
		if(imovelComercial.isPresent()) {
			return Response.ok().entity(imovelComercial.get()).build();
		}
		return Response.status(412).entity("Imovel nao recuperado").build();
	}
	
	@GET
	@Path("gerar-relatorio-imovel/{numero}")
	public Response gerarRelatarorioImovelComercial(@PathParam("numero") String numero) {
		final String usuarioPC = System.getProperty("user.name");
		final String caminhoPadrao = "C:\\Users\\" + usuarioPC + "\\Documents\\gerenciador-goldstar\\";
		
		
		File pasta = new File(caminhoPadrao);
		LOGGER.log(Level.INFO, "pasta:" + caminhoPadrao);
		
		if(!new File(caminhoPadrao).isDirectory()) {
			pasta.mkdir();
		}
		
		Relatorio relatorio = gerarRelatorio.gerarRelatorioImovelComercial(numero);
		if(relatorio.getImoveisPresentesRelatorio().isEmpty()) return Response.status(412).entity("Relatorio está vazio.").build(); 
		if(gerarRelatorio.gerarPDFTodosImoveisComerciais(caminhoPadrao, relatorio)) {
			return Response.ok("relatório gerado com sucesso em ".concat(caminhoPadrao)).build();
		}
		return Response.status(412).entity("Falha ao tentar encontrar caminho para gerar o relatório: Relatório não gerado.").build();
	}

	@GET
	@Path("remover-imovel/{numero}")
	public Response removerImovelComercial(@PathParam("numero") String numero) {
		imovelService.removerImovelComercial(numero);
		return Response.ok().build();
	}

}

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

import domain.entity.negocio.ImovelResidencial;
import domain.entity.negocio.ProcessoCondominial;
import domain.entity.negocio.Relatorio;
import resource.dto.ImovelResidencialDTO;
import services.GeradorRelatorio;
import services.ImovelService;
	
@Path("/imovel-residencial")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
/**
 * Endpoints para imoveis residenciais
 *
 */
public class ImovelResidencialResource {
	private Logger LOGGER = Logger.getLogger(this.getClass().getName());
	
	@Inject
	private GeradorRelatorio gerarRelatorio;
	
	@Inject
	private ImovelService imovelService;
	
	@GET
	@Path("/gerar-relatorio-residenciais")
	public Response gerarRelatorioTodosImoveisResidenciais() {
		final String usuarioPC = System.getProperty("user.name");
		final String caminhoPadrao = "C:\\Users\\" + usuarioPC + "\\Documents\\gerenciador-goldstar\\";
		
		File pasta = new File(caminhoPadrao);
		LOGGER.log(Level.INFO, "pasta:" + caminhoPadrao);
		
		if(!new File(caminhoPadrao).isDirectory()) {
			pasta.mkdir();
		}
		
		Relatorio relatorio = gerarRelatorio.gerarRelatorioTodosImoveisResidenciais();
		if(relatorio.getImoveisPresentesRelatorio().isEmpty()) 
			return Response.status(412).entity("Relatorio está vazio.").build(); 
		
		if(gerarRelatorio.gerarPDFTodosImoveisResidenciais(caminhoPadrao, relatorio)) {
			return Response.ok("Relatório gerado com sucesso em ".concat(caminhoPadrao)).build();
		}
		return Response.status(412).entity("Falha ao tentar encontrar caminho para gerar o relatório: Relatório não gerado.").build();		
	}
	
	@POST
	@Path("/cadastrarImovelResidencial")
	public Response cadastrarImoveisResidenciais(ImovelResidencialDTO imovelResidencial) {
		
		if(imovelResidencial.getNumeroImovel()==null || imovelResidencial.getNumeroImovel().isEmpty()) 
			return Response.status(412).entity("Cadastro não realizado, número vazio.").build();
		if(imovelService.cadastrarImovelResidencial(imovelResidencial)) {
			return Response.ok("Cadastro realizado com sucesso").build();
		}
		return Response.status(412).entity("Cadastro não realizado.").build();
	}
	
	@POST
	@Path("/atualizar-Processos")
	public Response atualizaProcessosCondominiais(List<ProcessoCondominial> processos, String oidImovel) {
		return Response.ok("Processos atualizados com sucesso").build();
	}
	
	@GET
	@Path("busca-rgi/{rgi}")
	public Response buscaImovelResidencialPorRGI(@PathParam("rgi") String rgi) {
		List<ImovelResidencial> imoveis = imovelService.buscarImovelResidencialPorRGI(rgi);
		if(!imoveis.isEmpty()) 
			return Response.ok("Imovel Residencial encontrado").entity(imoveis).build();
		
		return Response.status(412).build();
	}
	
	@GET
	@Path("busca-numero/{numero}")
	public Response buscaImovelResidencialPorNumero(@PathParam("numero") String numero) {
		List<ImovelResidencial> imoveis = imovelService.buscarImovelResidencialPorNumero(numero);
		if(!imoveis.isEmpty()) 
			return Response.ok("Imovel Residencial encontrado").entity(imoveis).build();
		
		return Response.status(412).build();
	}
	
	@GET
	@Path("busca-nomeLocatario/{nome}")
	public Response buscaImovelResidencialPorNomeLocatario(@PathParam("nome") String nome) {
		List<ImovelResidencial> imoveis = imovelService.buscarImovelResidencialPorNomeLocatario(nome);
		if(!imoveis.isEmpty()) 
			return Response.ok("Imovel Residencial encontrado").entity(imoveis).build();
		
		return Response.status(412).build();
	}
	
	@POST
	@Path("/atualizar-imovel-residencial")
	public Response atualizarImoveisResidenciais(ImovelResidencialDTO imovelResidencial) {
		
		if(imovelResidencial.getNumeroImovel()!=null) { 
			if(imovelService.atualizarImovelResidencial(imovelResidencial)) {
				return Response.ok("Atualização realizada com sucesso").build();
			}
		}
		return Response.status(412).entity("Atualização não realizada.").build();
	}
	
	@GET
	@Path("recuperar-imovelResidencial-completo/{numero}")
	public Response recuperarImovelCompleto(@PathParam("numero") String numero) {
		Optional<ImovelResidencial> imovelResidencial = imovelService.recuperarImovelResidencialPorNumero(numero);
		if(imovelResidencial.isPresent()) {
			return Response.ok().entity(imovelResidencial.get()).build();
		}
		return Response.status(412).entity("Imovel nao recuperado").build();
	}
	
	@GET
	@Path("gerar-relatorio-imovel/{numero}")
	public Response gerarRelatorioImovelResidencial(@PathParam("numero") String numero) {
		final String usuarioPC = System.getProperty("user.name");
		final String caminhoPadrao = "C:\\Users\\" + usuarioPC + "\\Documents\\gerenciador-goldstar\\";
		
		File pasta = new File(caminhoPadrao);
		LOGGER.log(Level.INFO, "pasta:" + caminhoPadrao);
		
		if(!new File(caminhoPadrao).isDirectory()) {
			pasta.mkdir();
		}
		
		Relatorio relatorio = gerarRelatorio.gerarRelatorioImovelResidencial(numero);
		if(relatorio.getImoveisPresentesRelatorio().isEmpty()) 
			return Response.status(412).entity("Relatorio está vazio.").build(); 
		
		if(gerarRelatorio.gerarPDFTodosImoveisResidenciais(caminhoPadrao, relatorio)) {
			return Response.ok("Relatório gerado com sucesso em ".concat(caminhoPadrao)).build();
		}
		return Response.status(412).entity("Falha ao tentar encontrar caminho para gerar o relatório: Relatório não gerado.").build();	
	}
	
	

}

package resource;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
	
	@POST
	@Path("/gerar-relatorio")
	public Response gerarRelatorioTodosImoveisResidenciais(String path) {
		Relatorio relatorio = gerarRelatorio.gerarRelatorioTodosImoveisResidenciais();
		if(relatorio.getImoveisPresentesRelatorio().isEmpty()) return Response.status(412).entity("Relatorio está vazio.").build(); 
		if(imovelService.gerarRelatorioTodosImoveisResidenciais(path, relatorio)) {
			return Response.ok("Relatório gerado com sucesso.").build();
		}
		return Response.status(412).entity("Falha ao tentar encontrar caminho para gerar o relatório: Relatório não gerado.").build();		
	}
	
	@POST
	@Path("/cadastrarImovelResidencial")
	public Response cadastrarImoveisResidenciais(ImovelResidencialDTO imovelResidencial) {
		if(imovelResidencial.getNumeroImovel()!=null) { 
			if(imovelService.cadastrarImovelResidencial(imovelResidencial)) {
				return Response.ok("Cadastro realizado com sucesso").build();
			}
		}
		return Response.status(412).entity("Cadastro não realizado, imovel ja cadastrado ou numero vazio.").build();
	}
	
	
}

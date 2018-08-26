package resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import domain.entity.negocio.Relatorio;
import services.GeradorRelatorio;
import services.impl.ImovelServiceImpl;
	
@Path("/imovel-residencial")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
/**
 * Endpoints para imoveis residenciais
 *
 */
public class ImovelResidencialResource {
	@Inject
	private GeradorRelatorio gerarRelatorio;
	
	@Inject
	private ImovelServiceImpl imovelservice;
	
	@POST
	@Path("/gerar-relatorio")
	public Response gerarRelatorioTodosImoveisResidenciais(String path) {
		Relatorio relatorio = gerarRelatorio.gerarRelatorioTodosImoveisResidenciais();
		if(imovelservice.gerarRelatorioTodosImoveisResidenciais(path, relatorio)) {
			return Response.ok("Relatório gerado com sucesso").build();
		}
		return Response.status(412,"Falha ao tentar encontrar caminho para gerar o relatório: Relatório não gerado.").build();		
	}
	

}

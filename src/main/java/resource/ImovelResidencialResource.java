package resource;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import domain.entity.negocio.Relatorio;
import services.GeradorRelatorio;
	
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
	
	@GET
	@Path("/gerar-relatorio")
	public Response gerarRelatorioTodosImoveisResidenciais() throws IOException {
		Relatorio relatorio = new Relatorio();
		gerarRelatorio.gerarRelatorioTodosImoveisResidenciais();

		return Response.ok("Relatório gerado com sucesso").build();
	}
}

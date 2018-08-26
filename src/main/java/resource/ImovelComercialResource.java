package resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("/imovel-comercial")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
/**
 * Endpoints para imoveis comericias
 *
 */
public class ImovelComercialResource {
	
	@GET
	@Path("/{rgi}")
	/**
	 * Endpoint informando os imoveis comerciais que podem estar associado
	 * ou rgi informado 
	 * 
	 * nota: o rgi n�o precisa necessariamente estar completo
	 * 
	 * @param rgi ( ou parte do rgi ) do imovel procurado
	 * @return Lista contendo os imoveis procurados
	 */
	public Response getImoveisComericiasPorRGI(@PathParam(value = "rgi") String rgi) {
		return Response.status(404).entity("Recurso ainda n�o implementando").build();
	}
	
	@POST
	@Path("gerar-relatorio-comercial")
	public Response gerarRelatorioTodosImoveisComerciais(String path) {
		
		
		return Response.ok("relat�rio gerado com sucesso").build();
	}

}

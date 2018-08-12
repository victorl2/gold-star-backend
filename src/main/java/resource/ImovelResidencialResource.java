package resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/imovel-residencial")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
/**
 * Endpoints para imoveis residenciais
 *
 */
public class ImovelResidencialResource {
	
	@GET
	@Path("/{rgi}")
	/**
	 * Endpoint informando os imoveis residenciais que podem estar associado
	 * ou rgi informado 
	 * 
	 * nota: o rgi não precisa necessariamente estar completo
	 * 
	 * @param rgi ( ou parte do rgi ) do imovel procurado
	 * @return Lista contendo os imoveis procurados
	 */
	public Response getImoveisResidenciaisPorRGI(@PathParam(value = "rgi") String rgi) {
		return Response.status(404).entity("Recurso ainda não implementando").build();
	}
}

package resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import resource.dto.ProprietarioDTO;
import services.ProprietarioService;

@Path("/cadastro-proprietario")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
/**
 * Endpoint para cadastro proprietario
 *
 */
public class ProprietarioResource {
	
	@Inject
	private ProprietarioService proprietarioSrv;
	
	@POST
	@Path("/cadastrar")
	public Response cadastroProprietario(ProprietarioDTO proprietarioDTO) {
		if(proprietarioSrv.cadastrarProprietario(proprietarioDTO)) {
			return Response.ok("Cadastro realizado com sucesso").build();
		}
		return Response.status(412).entity("Cadastro não efetuado").build();
	}
}

package resource;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import domain.entity.negocio.Proprietario;
import services.ProprietarioService;

@Path("/proprietario")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
/**
 * Endpoint para cadastro proprietario
 *
 */
public class ProprietarioResource {
	
	@Inject
	private ProprietarioService proprietarioSrv;
	
	@GET
	@Path("/{cpf}")
	public Response buscarProprietarioPorCPF(@PathParam("cpf") String cpf) {
		Optional<Proprietario> proprietario = proprietarioSrv.buscaProprietarioPorCPF(cpf);
		if(proprietario.isPresent()) {
			return Response.ok("Proprietário cadastrado").entity(proprietario.get()).build();
		}
		return Response.status(412).entity("Proprietário não cadastrado").build();
	}

}

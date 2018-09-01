package resource;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import domain.entity.negocio.Proprietario;
import resource.dto.ProprietarioDTO;
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
	
	@POST
	@Path("/cadastrar-proprietario")
	public Response cadastroProprietario(ProprietarioDTO proprietarioDTO) {
		Optional<Proprietario> proprietario = proprietarioSrv.cadastrarProprietario(proprietarioDTO);
		if(proprietario.isPresent()) {
			return Response.ok("Cadastro realizado com sucesso").entity(proprietario.get().getID()).build();
		}
		return Response.status(412).entity("Cadastro não efetuado").build();
	}
	
	@GET
	@Path("/{cpf}")
	public Response buscarProprietarioPorCPF(@PathParam("cpf") String cpf) {
		Optional<Proprietario> proprietario = proprietarioSrv.buscaProprietarioPorCPF(cpf);
		if(proprietario.isPresent()) {
			return Response.ok("Proprietário cadastrado").entity(proprietario.get()).build();
		}
		return Response.status(412).entity("Locatario não cadastrado").build();
	}
}

package resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
		if(proprietarioSrv.cadastrarProprietario(proprietarioDTO)) {
			return Response.ok("Cadastro realizado com sucesso").build();
		}
		return Response.status(412).entity("Cadastro n�o efetuado").build();
	}
	
	@POST
	@Path("/buscar-proprietario")
	public Response buscarProprietarioPorCPF(String cpf) {
		if(proprietarioSrv.buscaProprietarioPorCPF(cpf)) {
			return Response.ok("Propriet�rio cadastrado").entity(true).build();
		}
		return Response.status(412).entity("Proprietario n�o cadastrado").entity(false).build();
	}
}

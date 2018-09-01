package resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import domain.entity.negocio.Locatario;
import resource.dto.LocatarioDTO;
import services.LocatarioService;

@Path("/locatario")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
/**
 * Endpoint para cadastro locatario
 *
 */
public class LocatarioResource{
	
	@Inject
	private LocatarioService locatarioSrv;
	
	@POST
	@Path("/cadastrar-locatario")
	public Response cadastroLocatario(LocatarioDTO locatarioDTO) {
		Locatario locatario = locatarioSrv.cadastrarLocatario(locatarioDTO);
		if(locatario.getID()!=null) {
			return Response.ok("Cadastro realizado com sucesso").entity(locatario.getID()).build();
		}
		return Response.status(412,"Cadastro não efetuado").build();
	}
	
	@POST
	@Path("/buscar-locatario")
	public Response buscarLocatarioPorCPF(String cpf) {
		Locatario locatario = locatarioSrv.buscaLocatarioPorCPF(cpf);
		if(locatario.getID()!=null) {
			return Response.ok("Locatario encontrado").entity(locatario).build();
		}
		return Response.status(412,"Locatario não cadastrado").build();
	}
}
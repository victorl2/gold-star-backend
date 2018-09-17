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
		Optional<Locatario> locatario = locatarioSrv.cadastrarLocatario(locatarioDTO);
		if(locatario.isPresent()) {
			return Response.ok("Cadastro realizado com sucesso").entity(locatario.get().getID()).build();
		}
		return Response.status(412).entity("Cadastro não efetuado").build();
	}
	
	@GET
	@Path("/{cpf}")
	public Response buscarLocatarioPorCPF(@PathParam("cpf") String cpf) {
		Optional<Locatario> locatario = locatarioSrv.buscaLocatarioPorCPF(cpf);
		if(locatario.isPresent()) {
			return Response.ok("Locatario encontrado").entity(locatario.get()).build();
		}
		return Response.status(412).entity("Locatario não cadastrado").build();
	}
	
	@POST
	@Path("/atualizar-locatario/{idImovel}")
	public Response atualizarLocatario(LocatarioDTO locatarioDTO, @PathParam("idImovel") String idImovel) {
		Optional<Locatario> locatario = locatarioSrv.atualizarLocatario(locatarioDTO, idImovel);
		if(locatario.isPresent()) {
			return Response.ok("Atualizaçao realizada com sucesso").entity(locatario.get().getID()).build();
		}
		return Response.status(412).entity("Atualização não efetuada").build();
	}
}
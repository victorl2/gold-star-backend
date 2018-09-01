package resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
		Proprietario proprietario = proprietarioSrv.cadastrarProprietario(proprietarioDTO);
		if(proprietario.getID()!=null) {
			return Response.ok("Cadastro realizado com sucesso").entity(proprietario.getID()).build();
		}
		return Response.status(412,"Cadastro não efetuado").build();
	}
	
	@POST
	@Path("/buscar-proprietario")
	public Response buscarProprietarioPorCPF(String cpf) {
		Proprietario proprietario = proprietarioSrv.buscaProprietarioPorCPF(cpf);
		if(proprietario.getID()!=null) {
			return Response.ok("Proprietário cadastrado").entity(proprietario).build();
		}
		return Response.status(412,"Locatario não cadastrado").build();
	}
}

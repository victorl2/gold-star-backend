package resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import services.Autenticacao;

@Path("/usuario")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
public class UsuarioResource {
	
	@Inject
	private Autenticacao auth;
	
	@POST
	@Path("/login")
	public Response login(String login, String senha) {
		boolean sucessoLogin = auth.login(login, senha);
		if(sucessoLogin)
			return Response.ok().entity("Login realizado com sucesso").build();
		return Response.status(412).entity("Falha ao realizaro login").build();
	}
	
	@POST
	@Path("/cadastrar")
	public Response cadastrar(String login, String senha) {
		if(auth.existeUsuario(login, senha)) 
			return Response.status(412).entity("Já existe um usuário com o login informado").build();
		
		if(auth.cadastrar(login, senha))
			return Response.ok().entity("Cadastro realizado com sucesso").build();
		return Response.status(415).entity("Não foi possível realizar o cadastro, verifique a senha informada").build();
	}
}

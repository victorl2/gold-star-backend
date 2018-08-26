package resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import resource.dto.InformacaoUsuario;
import services.Autenticacao;

@Path("/usuario")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
public class UsuarioResource {
	
	@Inject
	private Autenticacao auth;
	
	@POST
	@Path("/login")
	public Response login(InformacaoUsuario info) {
		if(!info.getOptLogin().isPresent() || !info.getOptSenha().isPresent())
			return Response.status(415).entity("Informações inválidas para login").build();
		
		boolean sucessoLogin = auth.login(info.getLogin(), info.getSenha());
		
		if(sucessoLogin)
			return Response.ok().entity("Login realizado com sucesso").build();
		return Response.status(412).entity("Falha ao realizaro login").build();
	}
	
	@POST
	@Path("/cadastrar")
	public Response cadastrar(InformacaoUsuario info) {
		if(!info.getOptLogin().isPresent() || !info.getOptSenha().isPresent())
			return Response.status(415).entity("Informações inválidas para login").build();
		
		if(auth.existeUsuario(info.getLogin(), info.getSenha())) 
			return Response.status(412).entity("Já existe um usuário com o login informado").build();
		
		if(auth.cadastrar(info.getLogin(), info.getSenha()))
			return Response.ok().entity("Cadastro realizado com sucesso").build();
		return Response.status(415).entity("Não foi possível realizar o cadastro, verifique a senha informada").build();
	}
	
	@GET
	public Response gerarRelatorioTodosImoveisResidenciais2(){

		return Response.ok("Relatório gerado com sucesso").build();
	}
	
	
}

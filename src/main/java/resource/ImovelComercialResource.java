package resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import domain.entity.negocio.Relatorio;
import resource.dto.ImovelComercialDTO;
import services.GeradorRelatorio;
import services.ImovelService;


@Path("/imovel-comercial")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
/**
 * Endpoints para imoveis comericias
 *
 */
public class ImovelComercialResource {
	@Inject
	private GeradorRelatorio gerarRelatorio;
	
	@Inject
	private ImovelService imovelService;

	@GET
	@Path("/{rgi}")
	/**
	 * Endpoint informando os imoveis comerciais que podem estar associado
	 * ou rgi informado 
	 * 
	 * nota: o rgi não precisa necessariamente estar completo
	 * 
	 * @param rgi ( ou parte do rgi ) do imovel procurado
	 * @return Lista contendo os imoveis procurados
	 */
	public Response getImoveisComerciaisPorRGI(@PathParam(value = "rgi") String rgi) {
		return Response.status(404).entity("Recurso ainda não implementando").build();
	}
	
	@POST
	@Path("gerar-relatorio-comercial")
	public Response gerarRelatorioTodosImoveisComerciais(String path) {
		Relatorio relatorio = gerarRelatorio.gerarRelatorioTodosImoveisComerciais();
		if(relatorio.getImoveisPresentesRelatorio().isEmpty()) return Response.status(412).entity("Relatorio está vazio.").build(); 
		if(imovelService.gerarRelatorioTodosImoveisComerciais(path, relatorio)) {
			return Response.ok("relatório gerado com sucesso").build();
		}
		return Response.status(412).entity("Falha ao tentar encontrar caminho para gerar o relatório: Relatório não gerado.").build();
	}
	
	@POST
	@Path("/cadastrar-imovel-comercial")
	public Response cadastrarImoveisComerciais(ImovelComercialDTO imovelComercial) {
		if(imovelComercial.getNumeroImovel()!=null) { 
			if(imovelService.cadastrarImovelComercial(imovelComercial)) {
				return Response.ok("Cadastro realizado com sucesso").build();
			}
		}
		return Response.status(412).entity("Cadastro não realizado, imovel ja cadastrado ou numero vazio.").build();
	}

}

package resource;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.Locatario;
import domain.entity.negocio.Proprietario;
import domain.entity.negocio.Relatorio;
import resource.dto.ImovelComercialDTO;
import resource.dto.ImovelResidencialDTO;
import services.GeradorRelatorio;
import services.ImovelService;
import services.LocatarioService;
import services.ProprietarioService;


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
	
	@Inject
	private ProprietarioService proprietarioService;

	@Inject
	private LocatarioService locatarioService;

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
		if (imovelComercial.getNumeroImovel() == null)
			return Response.status(412).entity("Cadastro não realizado, imovel ja cadastrado ou numero vazio.").build();
		
		if (imovelComercial.getProprietario() != null) {
			if (imovelComercial.getProprietario().getId() == null
					|| imovelComercial.getProprietario().getId().isEmpty()) {
				Optional<Proprietario> proprietario = proprietarioService
						.cadastrarProprietario(imovelComercial.getProprietario());
				if (proprietario.isPresent()) {
					imovelComercial.getProprietario().setId(proprietario.get().getID());
				}
			}
		}
		if (imovelComercial.getLocador() != null) {
			if (imovelComercial.getLocador().getId() == null
					|| imovelComercial.getLocador().getId().isEmpty()) {
				Optional<Locatario> locatario = locatarioService
						.cadastrarLocatario(imovelComercial.getLocador());
				if (locatario.isPresent()) {
					imovelComercial.getLocador().setId(locatario.get().getID());
				}
			}
		}
		if (imovelService.cadastrarImovelComercial(imovelComercial)) {
			return Response.ok("Cadastro realizado com sucesso").build();
		}
		return Response.status(415).entity("Falha ao realizar cadastro").build();
	}
	
	@GET
	@Path("busca-rgi/{rgi}")
	public Response buscaImovelComercialPorRGI(@PathParam("rgi") String rgi) {
		List<ImovelComercial> imoveis = imovelService.buscarImovelComercialPorRGI(rgi);
		if(!imoveis.isEmpty()) 
			return Response.ok("Imovel Comercial encontrado").entity(imoveis).build();
		
		return Response.status(412).build();
	}
	
	@GET
	@Path("busca-numero/{numero}")
	public Response buscaImovelComercialPorNumero(@PathParam("numero") String numero) {
		List<ImovelComercial> imoveis = imovelService.buscarImovelComercialPorNumero(numero);
		if(!imoveis.isEmpty()) 
			return Response.ok("Imovel Comercial encontrado").entity(imoveis).build();
		
		return Response.status(412).build();
	}
	
	@GET
	@Path("busca-nomeLocatario/{nome}")
	public Response buscaImovelComercialPorNomeLocatario(@PathParam("nome") String nome) {
		List<ImovelComercial> imoveis = imovelService.buscarImovelComercialPorNomeLocatario(nome);
		if(!imoveis.isEmpty()) 
			return Response.ok("Imovel Comercial encontrado").entity(imoveis).build();
		
		return Response.status(412).build();
	}
	
	@POST
	@Path("/atualizarImovelComercial")
	public Response atualizarImoveisComerciais(ImovelComercialDTO imovelComercial, String idImovel) {
		if(idImovel == null) return Response.status(415).entity("Falha: IdImovel Vazio").build();
		if (imovelComercial.getProprietario() != null) {
			if (imovelComercial.getProprietario().getId() == null
					|| imovelComercial.getProprietario().getId().isEmpty()) {
				Optional<Proprietario> proprietario = proprietarioService
						.cadastrarProprietario(imovelComercial.getProprietario());
				if (proprietario.isPresent()) {
					imovelComercial.getProprietario().setId(proprietario.get().getID());
				}
			}else{
				proprietarioService.atualizarProprietario(imovelComercial.getProprietario());
			}
		}
		if (imovelComercial.getLocador() != null) {
			if (imovelComercial.getLocador().getId() == null
					|| imovelComercial.getLocador().getId().isEmpty()) {
				Optional<Locatario> locatario = locatarioService
						.cadastrarLocatario(imovelComercial.getLocador());
				if (locatario.isPresent()) {
					imovelComercial.getLocador().setId(locatario.get().getID());
				}
			}else{
				locatarioService.atualizarLocatario(imovelComercial.getLocador());
			}
		}if (imovelService.atualizarImovelComercial(imovelComercial,idImovel)) {
			return Response.ok("atualização realizada com sucesso").build();
		}
		return Response.status(415).entity("Falha ao realizar a atualização").build();
	}

}


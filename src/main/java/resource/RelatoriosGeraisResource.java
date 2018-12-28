package resource;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import domain.entity.negocio.Relatorio;
import services.GeradorRelatorio;

@Path("relatorio")
public class RelatoriosGeraisResource {
	private Logger LOGGER = Logger.getLogger(this.getClass().getName());
	
	@Inject
	private GeradorRelatorio gerarRelatorio;
	
	@GET
	@Path("{filtro}")
	public Response gerarRelatorioImoveisComFiltro(@PathParam("filtro") String filtro) {
		final String usuarioPC = System.getProperty("user.name");
		final String caminhoPadrao = "C:\\Users\\" + usuarioPC + "\\Documents\\gerenciador-goldstar\\";
		
		File pasta = new File(caminhoPadrao);
		LOGGER.log(Level.INFO, "pasta:" + caminhoPadrao);
		
		if(!new File(caminhoPadrao).isDirectory()) {
			pasta.mkdir();
		}
		Relatorio relatorio = null;
		if("barbara".equals(filtro)) {
			relatorio = gerarRelatorio.gerarRelatorioImoveisBarbara();
		}
		if("coluna".equals(filtro)) {
			relatorio = gerarRelatorio.gerarRelatorioImoveisColuna();
		}
		if("processos".equals(filtro)) {
			relatorio = gerarRelatorio.gerarRelatorioImoveisProcessos();
		}
		if("rgi".equals(filtro)) {
			relatorio = gerarRelatorio.gerarRelatorioImoveisRgi();
		}
		
		if(relatorio.getImoveisPresentesRelatorio().isEmpty()) 
			return Response.status(412).entity("Relatorio está vazio.").build(); 
		
		if(gerarRelatorio.gerarPDFTodosImoveisFiltro(caminhoPadrao, relatorio, filtro)) {
			return Response.ok("Relatório gerado com sucesso em ".concat(caminhoPadrao)).build();
		}
		return Response.status(412).entity("Falha ao tentar encontrar caminho para gerar o relatório: Relatório não gerado.").build();		
	
	}
}

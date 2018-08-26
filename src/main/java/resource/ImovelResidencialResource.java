package resource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import domain.entity.negocio.Imovel;
import domain.entity.negocio.ProcessoCondominial;
import domain.entity.negocio.Relatorio;
import services.GeradorRelatorio;
	
@Path("/imovel-residencial")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
/**
 * Endpoints para imoveis residenciais
 *
 */
public class ImovelResidencialResource {
	private Logger LOGGER = Logger.getLogger(getClass().getName());
	@Inject
	private GeradorRelatorio gerarRelatorio;
	
	@POST
	@Path("/gerar-relatorio")
	public Response gerarRelatorioTodosImoveisResidenciais(String path) {
		Relatorio relatorio = gerarRelatorio.gerarRelatorioTodosImoveisResidenciais();
		Date data = new Date(System.currentTimeMillis());
		try {
			FileOutputStream fos = new FileOutputStream(path);
			PdfWriter writer = new PdfWriter(fos);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);
			PdfFont font = PdfFontFactory.createFont("Helvetica");
			PdfFont bold = PdfFontFactory.createFont("Helvetica-Bold");
			document.add(new Paragraph("Relat�rio: Todos os im�veis Resid�nciais.             " 
													+ "Relat�rio gerado em: " + java.text.DateFormat.getDateInstance(DateFormat.MEDIUM)
																.format(data)).setFont(bold));
			for(Imovel imovel : relatorio.getImoveisPresentesRelatorio()) {
				Table table = new Table(new float[]{1,1});
				table.setWidth(UnitValue.createPercentValue(100));
				process(table, imovel, bold, true);
				process(table, imovel, font, false);
				document.add(new Paragraph());
				document.add(new Paragraph());
				document.add(table);
			}
			document.add(new Paragraph());
				document.close();
		}catch(FileNotFoundException e){
			LOGGER.log(Level.SEVERE, "Falha ao tentar encontrar caminho para gerar o relat�rio: Relat�rio n�o gerado.");
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Falha durante a leitura do arquivo");
		}
		
		
		return Response.ok("Relat�rio gerado com sucesso").build();
	}
	
	private boolean possuiProcessoAtivo(Imovel imovel) {
		if(imovel.getProcessos() == null) return false;
			
		for (ProcessoCondominial processo : imovel.getProcessos()) {
			if(processo.getProcessoAtivo()) return true;
		}
		return false;
	}
	
	protected void process(Table table, Imovel imovel, PdfFont font, boolean isHeader) {
		//Caso em que as informa��es devem ser geradas como cabe�alho das tabelas no pdf
		if (isHeader) {
            table.addHeaderCell(
                new Cell().add( 
                    new Paragraph("N� do apartamento: " + imovel.getNumeroImovel().toString()).setFont(font)));
            if(imovel.getRgi()!=null) {
            table.addHeaderCell(
                    new Cell().add(
                        new Paragraph("RGI im�vel: " + imovel.getRgi().toString()).setFont(font)));
            }else {
            	table.addHeaderCell(
                        new Cell().add(
                            new Paragraph("RGI do im�vel n�o informado: " + imovel.getRgi().toString()).setFont(font)));
            }
        // Caso onde as informa��es ser�o geradas no corpo das tabelas no pdf
        }else {
		    if(imovel.getDonoImovel()!=null) {
		    	if(imovel.getDonoImovel().getNome()!=null) {
				    table.addCell(
				    		new Cell().add(
				                new Paragraph("Nome Propriet�rio: " + imovel.getDonoImovel().getNome()).setFont(font)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Nome Propriet�rio: n�o informado").setFont(font)));
		    	}
		    	if(imovel.getDonoImovel().getCelular() !=null) {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel Propriet�rio: " + imovel.getDonoImovel().getCelular()).setFont(font)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel Propriet�rio: n�o informado").setFont(font)));
		    	}	    
		    }else {
		    	table.addCell(
			    		new Cell().add(
			                new Paragraph("Nome Propriet�rio: n�o informado").setFont(font)));
		    	table.addCell(
			    		new Cell().add(
			                new Paragraph("Tel Propriet�rio: n�o informado").setFont(font)));
		    }
		    if(imovel.getLocatario()!=null) {
		    	if(imovel.getLocatario().getNome()!=null) {
				    table.addCell(
				    		new Cell().add(
				                new Paragraph("Nome Locat�rio: " + imovel.getLocatario().getNome()).setFont(font)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Nome locat�rio n�o informado").setFont(font)));
		    	}
		    	if(imovel.getLocatario().getCelular() !=null) {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel Locat�rio: " + imovel.getLocatario().getCelular()).setFont(font)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel locat�rio n�o informado").setFont(font)));
		    	}
		    }else {
		    	table.addCell(
			    		new Cell().add(
			                new Paragraph("Nome locat�rio: " + imovel.getLocatario().getNome()).setFont(font)));
		    	table.addCell(
				    	new Cell().add(
				               new Paragraph("Tel locat�rio n�o informado").setFont(font)));
		    }
		    if(imovel.getTrocouBarbara()) {
			    table.addCell(
			            new Cell().add(
			                new Paragraph("Trocou barbar�").setFont(font)));
		    }else {
		    	table.addCell(
			            new Cell().add(
			                new Paragraph("N�o trocou barbar�").setFont(font)));
		    }
		    if(possuiProcessoAtivo(imovel)) {
		    	table.addCell(
		            new Cell().add(
		                new Paragraph("Possui a��o condominial").setFont(font)));
		    }else {
		    	table.addCell(
			        new Cell().add(
			            new Paragraph("N�o possui a��o condominial").setFont(font)));
		    }
		    
		    if(imovel.getContatoEmergencia()!=null) {
		    	if(imovel.getContatoEmergencia().getNome()!=null) {
				    table.addCell(
				    		new Cell().add(
				                new Paragraph("Contato emerg�ncia: " + imovel.getContatoEmergencia().getNome()).setFont(font)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Contato emerg�ncia n�o informado").setFont(font)));
		    	}
		    	if(imovel.getContatoEmergencia().getCelular() !=null) {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel Emerg�ncia: " + imovel.getContatoEmergencia().getCelular()).setFont(font)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel Emerg�ncia n�o informado").setFont(font)));
		    	}
		    }else {
		    	table.addCell(
			    		new Cell().add(
			                new Paragraph("Contato emerg�ncia n�o informado").setFont(font)));
		    	table.addCell(
			    		new Cell().add(
			                new Paragraph("Tel Emerg�ncia n�o informado").setFont(font)));
		    }
        }
	}
	
}

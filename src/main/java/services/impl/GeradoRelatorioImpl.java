package services.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import javax.inject.Inject;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import domain.entity.negocio.ImovelResidencial;
import domain.entity.negocio.Locatario;
import domain.entity.negocio.ProcessoCondominial;
import domain.entity.negocio.Proprietario;
import domain.entity.negocio.Relatorio;
import domain.repository.ImovelResidencialRepository;
import services.GeradorRelatorio;

public class GeradoRelatorioImpl implements GeradorRelatorio{
	
	@Inject
	private ImovelResidencialRepository imovelResidencialRepository;
	
	public Relatorio gerarRelatorioTodosImoveisResidenciais() throws IOException {
		
		Relatorio relatorio = new Relatorio();
		/*relatorio.setImoveisPresentesRelatorio(
				imovelResidencialRepository
					.buscarTodos()
						.stream()
							.map(residencia -> (Imovel) residencia)
								.collect(Collectors.toList()));*/
		
		ImovelResidencial imovel1 = new ImovelResidencial();
		imovel1.setDonoImovel(new Proprietario());
		imovel1.setRgi("14743997704");
		imovel1.getDonoImovel().setNome("Ronald Machado Campbell Junior Junior haha");
		imovel1.getDonoImovel().setCelular("(21)983047112");
		imovel1.setLocatario(new Locatario());
		imovel1.getLocatario().setNome("Camilla Peixoto");
		imovel1.getLocatario().setTelefone("(21)981879344");
		imovel1.setTrocouBarbara(true);
		imovel1.setNumeroImovel(1037);
		
		Date data = new Date(System.currentTimeMillis());
		
		FileOutputStream fos = new FileOutputStream("C:\\Users\\ronal\\Documents\\projeto goldstar\\gold-star-backend\\relatorio3.pdf");
		PdfWriter writer = new PdfWriter(fos);
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);
		PdfFont font = PdfFontFactory.createFont("Helvetica");
		PdfFont bold = PdfFontFactory.createFont("Helvetica-Bold");
		document.add(new Paragraph("Relatório: Todos os imóveis Residênciais.             " 
												+ "Relatório gerado em: " + java.text.DateFormat.getDateInstance(DateFormat.MEDIUM)
															.format(data)).setFont(bold));
		for(int i=0;i<20;i++) {
			imovel1.setNumeroImovel(1037+i);;
			Table table = new Table(new float[]{1,1});
			table.setWidth(UnitValue.createPercentValue(100));
		process(table, imovel1, bold, true);
			process(table, imovel1, font, false);
			document.add(new Paragraph());
			document.add(new Paragraph());
			document.add(table);
		}
		document.add(new Paragraph());
		document.close();
		
	    return relatorio;
	}
	

	public Relatorio gerarRelatorioImoveisComerciais() {
		throw new UnsupportedOperationException("Funcionalidade não implementada");
	}

	public Relatorio gerarRelatorioTodosImoveis() {
		throw new UnsupportedOperationException("Funcionalidade não implementada");
	}
	
	protected void process(Table table, ImovelResidencial imovel, PdfFont font, boolean isHeader) {
		if (isHeader) {
            table.addHeaderCell(
                new Cell().add( 
                    new Paragraph("Nº do Apartamento: " + imovel.getNumeroImovel().toString()).setFont(font)));
            table.addHeaderCell(
                    new Cell().add(
                        new Paragraph("RGI Imóvel: " + imovel.getRgi().toString()).setFont(font)));
        }else {
		    if(imovel.getDonoImovel()!=null) {
		    	if(imovel.getDonoImovel().getNome()!=null) {
				    table.addCell(
				    		new Cell().add(
				                new Paragraph("Nome Proprietário: " + imovel.getDonoImovel().getNome()).setFont(font)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Nome Proprietário: não informado").setFont(font)));
		    	}
		    	if(imovel.getDonoImovel().getCelular() !=null) {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel Proprietário: " + imovel.getDonoImovel().getCelular()).setFont(font)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel Proprietário: não informado").setFont(font)));
		    	}	    
		    }
		    if(imovel.getLocatario()!=null) {
		    	if(imovel.getLocatario().getNome()!=null) {
				    table.addCell(
				    		new Cell().add(
				                new Paragraph("Nome Locatário: " + imovel.getLocatario().getNome()).setFont(font)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Nome Locatário: não informado").setFont(font)));
		    	}
		    	if(imovel.getLocatario().getCelular() !=null) {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel Locatário: " + imovel.getLocatario().getCelular()).setFont(font)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel Locatário: não informado").setFont(font)));
		    	}
		    }
		    if(imovel.getTrocouBarbara()) {
			    table.addCell(
			            new Cell().add(
			                new Paragraph("Trocou barbará: sim").setFont(font)));
		    }else {
		    	table.addCell(
			            new Cell().add(
			                new Paragraph("Trocou barbará: não").setFont(font)));
		    }
		    if(possuiProcessoAtivo(imovel)) {
		    	table.addCell(
		            new Cell().add(
		                new Paragraph("Ação Condominial: sim").setFont(font)));
		    }else {
		    	table.addCell(
			        new Cell().add(
			            new Paragraph("Ação Condominial: não").setFont(font)));
		    }
		    
		    if(imovel.getContatoEmergencia()!=null) {
		    	if(imovel.getContatoEmergencia().getNome()!=null) {
				    table.addCell(
				    		new Cell().add(
				                new Paragraph("Contato Emergência: " + imovel.getContatoEmergencia().getNome()).setFont(font)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Contato Emergência: não informado").setFont(font)));
		    	}
		    	if(imovel.getContatoEmergencia().getCelular() !=null) {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel Emergência: " + imovel.getContatoEmergencia().getCelular()).setFont(font)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel Emergência: não informado").setFont(font)));
		    	}
		    }
        }
	}


		private boolean possuiProcessoAtivo(ImovelResidencial imovel) {
			if(imovel.getProcessos() == null) return false;
				
			for (ProcessoCondominial processo : imovel.getProcessos()) {
				if(processo.getProcessoAtivo()) return true;
			}
			return false;
		}
	}



package services.impl;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import domain.entity.negocio.Imovel;
import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;
import domain.entity.negocio.Pessoa;
import domain.entity.negocio.ProcessoCondominial;
import domain.entity.negocio.Relatorio;
import domain.repository.ImovelComercialRepository;
import domain.repository.ImovelResidencialRepository;
import services.GeradorRelatorio;

public class GeradoRelatorioImpl implements GeradorRelatorio{
	private Logger LOGGER = Logger.getLogger(getClass().getName());
	
	@Inject
	private ImovelResidencialRepository imovelResidencialRepository;
	
	@Inject
	private ImovelComercialRepository imovelComercialRepository;
	
	public Relatorio gerarRelatorioTodosImoveisResidenciais() {
		
		Relatorio relatorio = new Relatorio();
		
		relatorio.setImoveisPresentesRelatorio(
				imovelResidencialRepository
					.buscarTodos()
						.stream()
							.map(residencia -> (Imovel) residencia)
								.sorted(Comparator.comparing(Imovel::getNumeroImovel))
									.collect(Collectors.toList()));
	    return relatorio;
	}
	

	public Relatorio gerarRelatorioImoveisComerciais() {
		throw new UnsupportedOperationException("Funcionalidade n�o implementada");
	}

	public Relatorio gerarRelatorioTodosImoveis() {
		throw new UnsupportedOperationException("Funcionalidade n�o implementada");
	}



	public Relatorio gerarRelatorioTodosImoveisComerciais() {
		Relatorio relatorio = new Relatorio();
		relatorio.setImoveisPresentesRelatorio(
				imovelComercialRepository
					.buscarTodos()
						.stream()
							.map(comercio -> (Imovel) comercio)
								.sorted(Comparator.comparing(Imovel::getNumeroImovel))
									.collect(Collectors.toList()));
	    return relatorio;
	}
	
	public Relatorio gerarRelatorioImovelResidencial(String numero) {
		Relatorio relatorio = new Relatorio();
		relatorio.setImoveisPresentesRelatorio(
				imovelResidencialRepository
					.buscarTodos()
						.stream().filter(imovel->imovel.getNumeroImovel().equals(numero))
										.collect(Collectors.toList()));
	    return relatorio;
	}
	
	public Relatorio gerarRelatorioImovelComercial(String numero) {
		Relatorio relatorio = new Relatorio();
		relatorio.setImoveisPresentesRelatorio(
				imovelComercialRepository
					.buscarTodos()
						.stream().filter(imovel->imovel.getNumeroImovel().equals(numero))
										.collect(Collectors.toList()));
	    return relatorio;
	}
	
	public boolean gerarPDFTodosImoveisComerciais(String path, Relatorio relatorio) {
		Date data = new Date(System.currentTimeMillis());
		String pathFormatado= "";
		if(relatorio.getNumeroDeImoveis() == 1) {
			pathFormatado = path+"relatorioImovel"+java.text.SimpleDateFormat.getTimeInstance().format(data).replace(":","")+".pdf";
		}else {
			pathFormatado = path+"relatorioImoveis"+java.text.SimpleDateFormat.getTimeInstance().format(data).replace(":","")+".pdf";
		}
		try {
			FileOutputStream fos = new FileOutputStream(pathFormatado);
			PdfWriter writer = new PdfWriter(fos);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);
			PdfFont font = PdfFontFactory.createFont("Helvetica");
			PdfFont bold = PdfFontFactory.createFont("Helvetica-Bold");
			if(relatorio.getNumeroDeImoveis() == 1) {
				document.add(new Paragraph("Relat�rio: Im�vel Comercial.").addTabStops(new TabStop(1000, TabAlignment.RIGHT)).add(new Tab()) 
						.add("Relat�rio gerado em: " + java.text.DateFormat.getDateInstance(DateFormat.MEDIUM)
									.format(data)).setFont(bold));
			}else {
				document.add(new Paragraph("Relat�rio: Todos os im�veis Comerciais.").addTabStops(new TabStop(1000, TabAlignment.RIGHT)).add(new Tab()) 
						.add("Relat�rio gerado em: " + java.text.DateFormat.getDateInstance(DateFormat.MEDIUM)
									.format(data)+".").setFont(bold));
				document.add(new Paragraph("Relat�rio gerado com: "+relatorio.getNumeroDeImoveis()+" im�veis comerciais."));
			}
			
			if(!relatorio.getImoveisPresentesRelatorio().isEmpty()) {
				for(Imovel imovel : relatorio.getImoveisPresentesRelatorio()) {
					Table table = montaTabelaComercial(bold,font,imovel,relatorio);
					document.add(new Paragraph());
					document.add(new Paragraph());
					document.add(table);
				}
				document.add(new Paragraph());
			}
				document.close();
		}catch(FileNotFoundException e){
			LOGGER.log(Level.SEVERE, "Falha ao tentar encontrar caminho para gerar o relat�rio: Relat�rio n�o gerado.");
			return false;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Falha durante a leitura do arquivo");
			return false;
		}
		return true;
	}
	
	private Table montaTabelaComercial(PdfFont bold, PdfFont font, Imovel imovel, Relatorio relatorio) {
		Table table = new Table(new float[]{1});
		table.setWidth(UnitValue.createPercentValue(100));
		table.setFixedLayout();
		process(table,imovel,bold,true,false,relatorio);
		process(table, imovel, font, false, false, relatorio);
		return table;
	}
	
	public boolean gerarPDFTodosImoveisResidenciais(String path, Relatorio relatorio) {
		Date data = new Date(System.currentTimeMillis());
		String pathFormatado = "";
		if(relatorio.getNumeroDeImoveis() == 1) {
			pathFormatado = path+"relatorioImovel"+java.text.SimpleDateFormat.getTimeInstance().format(data).replace(":","")+".pdf";
		}else {
			pathFormatado = path+"relatorioImoveis"+java.text.SimpleDateFormat.getTimeInstance().format(data).replace(":","")+".pdf";
		}
		try {
			FileOutputStream fos = new FileOutputStream(pathFormatado);
			PdfWriter writer = new PdfWriter(fos);
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);
			PdfFont font = PdfFontFactory.createFont("Helvetica");
			PdfFont bold = PdfFontFactory.createFont("Helvetica-Bold");
			if(relatorio.getNumeroDeImoveis() == 1) {
				document.add(new Paragraph("Relat�rio: Im�vel Residencial.").addTabStops(new TabStop(1000, TabAlignment.RIGHT)).add(new Tab()) 
						.add("Relat�rio gerado em: " + java.text.DateFormat.getDateInstance(DateFormat.MEDIUM)
									.format(data)).setFont(bold));
			}else {
				document.add(new Paragraph("Relat�rio: Todos os im�veis Resid�nciais.").addTabStops(new TabStop(1000, TabAlignment.RIGHT)).add(new Tab()) 
						.add("Relat�rio gerado em: " + java.text.DateFormat.getDateInstance(DateFormat.MEDIUM)
									.format(data)).setFont(bold));
				document.add(new Paragraph("Relat�rio gerado com: "+relatorio.getNumeroDeImoveis()+" im�veis residenciais."));
			}
			
			if(!relatorio.getImoveisPresentesRelatorio().isEmpty()) {
				for(Imovel imovel : relatorio.getImoveisPresentesRelatorio()) {
					Table table = montaTabelaResidencial(bold,font,imovel,relatorio);
					document.add(new Paragraph());
					document.add(new Paragraph());
					document.add(table);
				}
				document.add(new Paragraph());
			}
		
			document.close();
		}catch(FileNotFoundException e){
			LOGGER.log(Level.SEVERE, "Falha ao tentar encontrar caminho para gerar o relat�rio: Relat�rio n�o gerado.");
			return false;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Falha durante a leitura do arquivo");
			return false;
		}
		return true;
	}
	
	private Table montaTabelaResidencial(PdfFont bold, PdfFont font, Imovel imovel, Relatorio relatorio) {
		Table table = new Table(new float[]{1});
		table.setWidth(UnitValue.createPercentValue(100));
		table.setFixedLayout();
		process(table, imovel, bold, true, true, relatorio);
		process(table, imovel, font, false, true, relatorio);
		return table;
	}
	
	private void process(Table table, Imovel imovel, PdfFont font, boolean isHeader, boolean isResidencial, Relatorio relatorio) {
		//Caso em que as informa��es devem ser geradas como cabe�alho das tabelas no pdf
		if (isHeader) {
			Paragraph paragrafo = new Paragraph();
			if(isResidencial) {
				paragrafo.add("N�mero do apartamento: "+imovel.getNumeroImovel()+"\n");
				if(imovel.getRgi()!=null && !imovel.getRgi().isEmpty()) {
					paragrafo.add("RGI im�vel: " + imovel.getRgi());
				}else{
					paragrafo.add("Rgi im�vel: n�o informado");
		        }
				
			}else {
				paragrafo.add("N�mero do com�rcio: "+ adicionaIsSobreloja((ImovelComercial)imovel) + imovel.getNumeroImovel().toString()+"\n");
				if(imovel.getRgi()!=null && !imovel.getRgi().isEmpty()) {
					paragrafo.add("RGI im�vel: " + imovel.getRgi());
				}else{
					paragrafo.add("Rgi im�vel: n�o informado");
		        }
			}
			table.addHeaderCell(
                    new Cell().add( 
                        paragrafo).setFont(font).setBackgroundColor(ColorConstants.LIGHT_GRAY));
			
        }else {
        	adicionaInformacaoSobreDonoDoImovel(imovel,table,font);
		    adicionaInformacaoSobreLocatario(imovel,table,font);
		    adicionaInformacaoSobreContatoEmergencia(imovel,table,font);
		    adicionaInformacaoSobreProcessosAtivos(imovel,table,font);
		    if(isResidencial) {
		    	adicionaInformacaoImovelResidencial(imovel,table,font,relatorio);
		    }else {
		    	adicionaInformacaoImovelComercial((ImovelComercial)imovel,table,font,relatorio);
		    }
		    adicionaInformacoesImovel(imovel,table,font);
        }
	}
	
	private void adicionaInformacaoImovelComercial(ImovelComercial imovel, Table table, PdfFont font, Relatorio relatorio) {
		Paragraph paragrafo_1 = new Paragraph();
		if(imovel.iseSobreloja()!=null) {
			if(imovel.iseSobreloja()) {
	    		paragrafo_1.add("Tipo com�rcio: sobreloja" + "\n");
	    	}else{
	    		paragrafo_1.add("Tipo com�rcio: loja"+"\n");
	    	}
	    }else {
	    		paragrafo_1.add("Tipo com�rcio: n�o informado"+"\n");
	    }
		table.addCell(
	    		new Cell().add((paragrafo_1).setFont(font).setFontSize(11))
				.setVerticalAlignment(VerticalAlignment.MIDDLE));

		Paragraph paragrafo_2 = new Paragraph();
		if(imovel.getNomeLoja()!=null && !imovel.getNomeLoja().isEmpty()) {
	    	paragrafo_2.add("Nome da loja: " + imovel.getNomeLoja()+ "\n");
	    }else {
	    	paragrafo_2.add("Nome da loja: n�o informado"+"\n");
	    }
		table.addCell(
	    		new Cell().add((paragrafo_2).setFont(font).setFontSize(11))
				.setVerticalAlignment(VerticalAlignment.MIDDLE));
	}


	private String adicionaIsSobreloja(ImovelComercial imovel) {
		return imovel.iseSobreloja()!=null?imovel.iseSobreloja()? "Sobreloja ":"Loja ":"";
	}


	private void adicionaInformacaoSobreProcessosAtivos(Imovel imovel, Table table, PdfFont font) {
		Paragraph paragrafo = new Paragraph();		
		if(imovel.getProcessos() != null && !imovel.getProcessos().isEmpty()) {
			List<ProcessoCondominial> lista = imovel.getProcessos().stream()
					.filter(processo -> processo.getProcessoAtivo())
					.collect(Collectors.toList());
			Integer processos = lista.size();
			paragrafo.add("N�mero de processos ativos deste im�vel: "+ processos.toString()+"\n");
			if(lista.size()!=0) {
				int i = 1; 
				for(ProcessoCondominial processo : lista) {
					paragrafo.add("    "+i +" - N�mero do processo: "+ processo.getCodigoProcesso()+"\n");
					i++;
				}
			}
		}
		table.addCell(
	    		new Cell().add((paragrafo).setFont(font).setFontSize(11))
				.setVerticalAlignment(VerticalAlignment.MIDDLE));
	}


	private void adicionaInformacaoImovelResidencial(Imovel imovel, Table table, PdfFont font, Relatorio relatorio) {
		ImovelResidencial imovelResidencial = (ImovelResidencial)relatorio.getImoveisPresentesRelatorio().get(0);
		
		Paragraph paragrafo_1 = new Paragraph();
		if(imovelResidencial.getMoradores() != null && !imovelResidencial.getMoradores().isEmpty()) {
			Integer moradores = imovelResidencial.getMoradores().size();
			paragrafo_1.add("N�mero de moradores deste im�vel: "+ moradores.toString()+"\n");
			if(moradores!=0) {
				int i = 1;
				for(Pessoa pessoa : imovelResidencial.getMoradores()) {
					paragrafo_1.add("Morador "+ i +": "+ pessoa.getNome()+"\n");
					i++;
				}
			}
		}
		table.addCell(
	    		new Cell().add((paragrafo_1).setFont(font).setFontSize(11))
				.setVerticalAlignment(VerticalAlignment.MIDDLE));
		
		Paragraph paragrafo_2 = new Paragraph();
		if(imovelResidencial.isPossuiAnimalEstimacao()!=null) {
			if(imovelResidencial.isPossuiAnimalEstimacao()) {
				paragrafo_2.add("Possui animal de estima��o: sim"+"\n");
			}else {
				paragrafo_2.add("Possui animal de estima��o: n�o"+"\n");
			}
		}else {
			paragrafo_2.add("Possui animal de estima��o: n�o informado"+"\n");
		}
		table.addCell(
	    		new Cell().add((paragrafo_2).setFont(font).setFontSize(11))
				.setVerticalAlignment(VerticalAlignment.MIDDLE));
	}


	private void adicionaInformacoesImovel(Imovel imovel, Table table, PdfFont font) {
		Paragraph paragrafo_1 = new Paragraph();
		if(imovel.getTrocouBarbara()!=null) {
			if(imovel.getTrocouBarbara()) {
	    		paragrafo_1.add("Trocou barbar�: sim" + "\n");
	    	}else{
	    		paragrafo_1.add("Trocou barbar�: n�o"+"\n");
	    	}
	    }else {
	    		paragrafo_1.add("Trocou barbar�: n�o informado"+"\n");
	    }
		table.addCell(
	    		new Cell().add((paragrafo_1).setFont(font).setFontSize(11))
				.setVerticalAlignment(VerticalAlignment.MIDDLE));

		Paragraph paragrafo_2 = new Paragraph();
		if(imovel.getTrocouColuna()!=null) {
			if(imovel.getTrocouColuna()) {
	    		paragrafo_2.add("Trocou coluna de �gua: sim" + "\n");
	    	}else{
	    		paragrafo_2.add("Trocou coluna de �gua: n�o"+"\n");
	    	}
	    }else {
	    		paragrafo_2.add("Trocou coluna de �gua: n�o informado"+"\n");
	    }
		table.addCell(
	    		new Cell().add((paragrafo_2).setFont(font).setFontSize(11))
				.setVerticalAlignment(VerticalAlignment.MIDDLE));
		
		Paragraph paragrafo_3 = new Paragraph();
		if(imovel.getAcordo()!=null && !imovel.getAcordo().isEmpty()) {
	    		paragrafo_3.add("Possui acordo: "+imovel.getAcordo() + "\n");
	    }else{
	    		paragrafo_3.add("Possui acordo: n�o"+"\n");
	    	}
		table.addCell(
	    		new Cell().add((paragrafo_3).setFont(font).setFontSize(11))
				.setVerticalAlignment(VerticalAlignment.MIDDLE));
		
		Paragraph paragrafo_4 = new Paragraph();
		if(imovel.getCobrancaBoleto()!=null && !imovel.getCobrancaBoleto().isEmpty()) {
			paragrafo_4.add("Cobran�a do boleto: "+imovel.getCobrancaBoleto() + "\n");
	    }else {
	    	paragrafo_4.add("Cobran�a do boleto: n�o informado"+ "\n");
	    }
		table.addCell(
	    		new Cell().add((paragrafo_4).setFont(font).setFontSize(11))
				.setVerticalAlignment(VerticalAlignment.MIDDLE));
	}


	private void adicionaInformacaoSobreContatoEmergencia(Imovel imovel, Table table, PdfFont font) {
		Paragraph paragrafo = new Paragraph();
		if(imovel.getContatoEmergencia()!=null) {
			if(imovel.getContatoEmergencia().getNome()!=null && !imovel.getContatoEmergencia().getNome().isEmpty()) {
	    		paragrafo.add("Nome contato emerg�ncia: " + imovel.getContatoEmergencia().getNome()+ "\n");
	    	}else {
	    		paragrafo.add("Nome contato emerg�ncia: n�o informado"+"\n");	
	    	}
	    	if(imovel.getContatoEmergencia().getTelefone() !=null && !imovel.getContatoEmergencia().getTelefone().isEmpty()) {
	    		paragrafo.add("Telefone contato emerg�ncia: "+ imovel.getContatoEmergencia().getTelefone() + "\n");
	    	}else {
	    		paragrafo.add("Telefone contato emerg�ncia: n�o informado"+"\n");
	    	}
	    	if(imovel.getContatoEmergencia().getCelular() !=null && !imovel.getContatoEmergencia().getCelular().isEmpty()) {
	    		paragrafo.add("Celular contato emerg�ncia: "+imovel.getContatoEmergencia().getCelular());
	    	}else {
	    		paragrafo.add("Celular contato emerg�ncia: n�o informado");
	    	}	    
	    }else {
	    	paragrafo.add("Este im�vel n�o possui contato emerg�ncia cadastrado");	
	    }
		table.addCell(
	    		new Cell().add((paragrafo).setFont(font).setFontSize(11))
				.setVerticalAlignment(VerticalAlignment.MIDDLE));
	}


	private void adicionaInformacaoSobreLocatario(Imovel imovel, Table table, PdfFont font) {
		Paragraph paragrafo = new Paragraph();
		if(imovel.getLocatario()!=null) {
	    	if(imovel.getLocatario().getNome()!=null && !imovel.getLocatario().getNome().isEmpty()) {
	    		paragrafo.add("Nome locat�rio: " + imovel.getLocatario().getNome()+ "\n");
	    	}else {
	    		paragrafo.add("Nome locat�rio: n�o informado"+"\n");	
	    	}
	    	if(imovel.getLocatario().getEmail()!=null && !imovel.getLocatario().getEmail().isEmpty()) {
			    paragrafo.add("Email locat�rio: " + imovel.getLocatario().getEmail()+ "\n");
	    	}else {
	    		paragrafo.add("Email locat�rio: n�o informado"+"\n");
	    	}
	    	if(imovel.getLocatario().getTelefone() !=null && !imovel.getLocatario().getTelefone().isEmpty()) {
	    		paragrafo.add("Telefone locat�rio: "+ imovel.getLocatario().getTelefone() + "\n");
	    	}else {
	    		paragrafo.add("Telefone locat�rio: n�o informado"+"\n");
	    	}
	    	if(imovel.getLocatario().getCelular() !=null && !imovel.getLocatario().getCelular().isEmpty()) {
	    		paragrafo.add("Celular locat�rio: "+imovel.getLocatario().getCelular());
	    	}else {
	    		paragrafo.add("Celular locat�rio: n�o informado");
	    	}	    
	    }else {
	    	paragrafo.add("Este im�vel n�o possui locat�rio cadastrado");	
	    }

    	table.addCell(
	    		new Cell().add((paragrafo).setFont(font).setFontSize(11))
				.setVerticalAlignment(VerticalAlignment.MIDDLE));
	}

	private void adicionaInformacaoSobreDonoDoImovel(Imovel imovel, Table table, PdfFont font) {
		Paragraph paragrafo = new Paragraph();
		if(imovel.getDonoImovel()!=null) {
	    	if(imovel.getDonoImovel().getNome()!=null && !imovel.getDonoImovel().getNome().isEmpty()) {
	    		paragrafo.add("Nome propriet�rio: " + imovel.getDonoImovel().getNome()+ "\n");
	    	}else {
	    		paragrafo.add("Nome propriet�rio: n�o informado"+"\n");	
	    	}
	    	if(imovel.getDonoImovel().getEmail()!=null && !imovel.getDonoImovel().getEmail().isEmpty()) {
			    paragrafo.add("Email propriet�rio: " + imovel.getDonoImovel().getEmail()+ "\n");
	    	}else {
	    		paragrafo.add("Email propriet�rio: n�o informado"+"\n");
	    	}
	    	if(imovel.getDonoImovel().getTelefone() !=null && !imovel.getDonoImovel().getTelefone().isEmpty()) {
	    		paragrafo.add("Telefone propriet�rio: "+ imovel.getDonoImovel().getTelefone() + "\n");
	    	}else {
	    		paragrafo.add("Telefone propriet�rio: n�o informado"+"\n");
	    	}
	    	if(imovel.getDonoImovel().getCelular() !=null && !imovel.getDonoImovel().getCelular().isEmpty()) {
	    		paragrafo.add("Celular propriet�rio: "+imovel.getDonoImovel().getCelular());
	    	}else {
	    		paragrafo.add("Celular propriet�rio: n�o informado");
	    	}	    
	    }else {
	    	paragrafo.add("Este im�vel n�o possui propriet�rio cadastrado");	
	    }

    	table.addCell(
	    		new Cell().add((paragrafo).setFont(font).setFontSize(11))
				.setVerticalAlignment(VerticalAlignment.MIDDLE));
	}
}



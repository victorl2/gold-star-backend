package services.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
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

import assemblers.ImovelResidencialAssembler;
import domain.entity.negocio.Imovel;
import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;
import domain.entity.negocio.Locatario;
import domain.entity.negocio.ProcessoCondominial;
import domain.entity.negocio.Proprietario;
import domain.entity.negocio.Relatorio;
import domain.repository.ImovelComercialRepository;
import domain.repository.ImovelResidencialRepository;
import domain.repository.LocatarioRepository;
import domain.repository.ProprietarioRepository;
import resource.dto.ImovelResidencialDTO;
import services.ImovelService;

@Stateless 
public class ImovelServiceImpl implements ImovelService{
	private Logger LOGGER = Logger.getLogger(getClass().getName());
	@Inject
	private ImovelResidencialRepository imovelResidencialRepository;
	
	@Inject
	private ImovelComercialRepository imovelComercialRepository;
	
	@Inject
	private ProprietarioRepository proprietarioRepository;
	
	@Inject
	private LocatarioRepository locatarioRepository;
	
	@Inject
	private ImovelResidencialAssembler imovelResidencialAssembler;

	public ImovelResidencial cadastrarImovelResidencial(ImovelResidencialDTO imovel) {
		List<ImovelResidencial> imoveis = imovelResidencialRepository
				.buscarTodos();
		if(imovel.getNumeroImovel()!=null) {
			imoveis.stream().filter(residencia -> 
						imovel.getNumeroImovel().equals(residencia.getNumeroImovel())
					).collect(Collectors.toList());
		}else {
				imoveis.stream().filter(residencia -> 
							imovel.getRgi().equals(residencia.getRgi())
						).collect(Collectors.toList());
		}
		
		if(imoveis.isEmpty()) {
			if(imovel.getOidProprietario()!=null) {
				Optional<Proprietario> prop = proprietarioRepository.buscarPorID(imovel.getOidProprietario());
				if(prop.isPresent()) {
					prop.get().getImoveis().add(imovelResidencialAssembler.build(imovel));
				}
			}
			if(imovel.getProcessos()!=null) {
				Optional<Locatario> loc = locatarioRepository.buscarPorID(imovel.getOidLocador());
				if(loc.isPresent()) {
					loc.get().getImoveisAlugados().add(imovelResidencialAssembler.build(imovel));
				}
			}
			return imovelResidencialRepository.salvar(imovelResidencialAssembler.build(imovel));
		}
	
		return null;
	}

	public ImovelComercial cadastrarImovelComercial(ImovelComercial imovel) {
		return imovelComercialRepository.salvar(imovel);
	}

	public List<ImovelResidencial> buscarTodosImoveisResidenciais() {
		return imovelResidencialRepository.buscarTodos();
	}

	public List<ImovelComercial> buscarTodosImoveisComerciais() {
		return imovelComercialRepository.buscarTodos();
	}
	
	public boolean gerarRelatorioTodosImoveisComerciais(String path, Relatorio relatorio) {
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
				Table table = montaTabelaComercial(bold,font,imovel);
				document.add(new Paragraph());
				document.add(new Paragraph());
				document.add(table);
			}
			document.add(new Paragraph());
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
	
	private Table montaTabelaComercial(PdfFont bold, PdfFont font, Imovel imovel) {
		Table table = new Table(new float[]{1,1});
		table.setWidth(UnitValue.createPercentValue(100));
		processComercial(table,(ImovelComercial)imovel,bold,true);
		process(table, imovel, font, false);
		processComercial(table,(ImovelComercial)imovel,font,false);
		return table;
	}

	private void processComercial(Table table, ImovelComercial imovel, PdfFont font, boolean isHeader) {
		if (isHeader) {
            table.addHeaderCell(
                new Cell().add( 
                    new Paragraph("N� do comercio: " + imovel.getNumeroImovel().toString()).setFont(font)));
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
        	if(imovel.iseSobreloja()) {
    		    table.addCell(
    		            new Cell().add(
    		                new Paragraph("� sobreloja").setFont(font)));
    	    }else {
    	    	table.addCell(
    		            new Cell().add(
    		                new Paragraph("� loja").setFont(font)));
    	    }
        	
        	if(imovel.getTipoLoja()!=null) {
        		if(imovel.getTipoLoja().getNomeDoTipoComercio()!=null) {
        			table.addCell(
        		            new Cell().add(
        		                new Paragraph("Tipo de loja: " + imovel.getTipoLoja().getNomeDoTipoComercio()).setFont(font)));
        		}else {
        			table.addCell(
        		            new Cell().add(
        		                new Paragraph("Tipo de loja n�o informado " + imovel.getTipoLoja().getNomeDoTipoComercio()).setFont(font)));
        		}
        	}else {
        		table.addCell(
    		            new Cell().add(
    		                new Paragraph("Tipo de loja n�o informado " + imovel.getTipoLoja().getNomeDoTipoComercio()).setFont(font)));
        	}
        }
	}

	public boolean gerarRelatorioTodosImoveisResidenciais(String path, Relatorio relatorio) {
		
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
				Table table = montaTabelaResidencial(bold,font,imovel);
				document.add(new Paragraph());
				document.add(new Paragraph());
				document.add(table);
			}
			document.add(new Paragraph());
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
	
	private Table montaTabelaResidencial(PdfFont bold, PdfFont font, Imovel imovel) {
		Table table = new Table(new float[]{1,1});
		table.setWidth(UnitValue.createPercentValue(100));
		process(table, imovel, bold, true);
		process(table, imovel, font, false);
		return table;
	}

	private void process(Table table, Imovel imovel, PdfFont font, boolean isHeader) {
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
        	
        	adicionaInformacaoSobreDonoDoImovel(imovel,table,font);
		    adicionaInformacaoSobreLocatario(imovel,table,font);
		    
		    //barbara
		    if(imovel.getTrocouBarbara()) {
			    table.addCell(
			            new Cell().add(
			                new Paragraph("Trocou barbar�").setFont(font)));
		    }else {
		    	table.addCell(
			            new Cell().add(
			                new Paragraph("N�o trocou barbar�").setFont(font)));
		    }
		    
		    //processo
		    if(possuiProcessoAtivo(imovel)) {
		    	table.addCell(
		            new Cell().add(
		                new Paragraph("Possui a��o condominial").setFont(font)));
		    }else {
		    	table.addCell(
			        new Cell().add(
			            new Paragraph("N�o possui a��o condominial").setFont(font)));
		    }
		    
		    //contato emergencia
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
	
	private boolean possuiProcessoAtivo(Imovel imovel) {
		if(imovel.getProcessos() == null) return false;
			
		for (ProcessoCondominial processo : imovel.getProcessos()) {
			if(processo.getProcessoAtivo()) return true;
		}
		return false;
	}
	
	private void adicionaInformacaoSobreLocatario(Imovel imovel, Table table, PdfFont font) {
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
		
	}

	private void adicionaInformacaoSobreDonoDoImovel(Imovel imovel, Table table, PdfFont font) {
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
		
	}
	
}

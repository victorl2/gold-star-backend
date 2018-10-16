package services.impl;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.itextpdf.kernel.colors.ColorConstants;
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
import domain.entity.negocio.ImovelComercial;
import domain.entity.negocio.ImovelResidencial;
import domain.entity.negocio.Locatario;
import domain.entity.negocio.Pessoa;
import domain.entity.negocio.ProcessoCondominial;
import domain.entity.negocio.Proprietario;
import domain.entity.negocio.Relatorio;
import domain.repository.ImovelComercialRepository;
import domain.repository.ImovelResidencialRepository;
import domain.repository.LocatarioRepository;
import domain.repository.ProprietarioRepository;
import resource.dto.ImovelComercialDTO;
import resource.dto.ImovelResidencialDTO;
import services.ImovelService;
import services.LocatarioService;
import services.ProprietarioService;

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
	private ProprietarioService proprietarioService;

	@Inject
	private LocatarioService locatarioService;


	public Boolean cadastrarImovelResidencial(ImovelResidencialDTO imovelDTO) {
		
		List<ImovelResidencial> imoveis = imovelResidencialRepository
				.buscarTodos().stream()
					.filter(residencia -> 
						residencia.getNumeroImovel().equals(imovelDTO.getNumeroImovel()) || residencia.getRgi().equals(imovelDTO.getRgi())
					).collect(Collectors.toList());
		
		if(!imoveis.isEmpty()) 
			return false;
		
		ImovelResidencial novoImovel = new ImovelResidencial();
		
		novoImovel.setNumeroImovel(imovelDTO.getNumeroImovel());
		novoImovel.setRgi(imovelDTO.getRgi());
		novoImovel.setTrocouBarbara(imovelDTO.getTrocouBarbara());
		novoImovel.setPossuiAnimalEstimacao(imovelDTO.getPossuiAnimalEstimacao());
		novoImovel.setCobrancaBoleto(imovelDTO.getCobrancaBoleto());
		novoImovel.setTrocouColuna(imovelDTO.getTrocouColuna());
		novoImovel.setMoradores(imovelDTO.getMoradores());
		novoImovel.setProcessos(imovelDTO.getProcessos());
		novoImovel.setContatoEmergencia(imovelDTO.getContatoEmergencia());
		novoImovel.setAcordo(imovelDTO.getAcordo());
		
		if(imovelDTO.getProprietario() != null) {
			Optional<Proprietario> proprietario = proprietarioService.cadastrarProprietario(imovelDTO.getProprietario(), novoImovel);
			if(proprietario.isPresent()) {
				novoImovel.setDonoImovel(proprietario.get());
			}
		}
		if(imovelDTO.getLocador()!=null) {
			Optional<Locatario> locatario = locatarioService.cadastrarLocatario(imovelDTO.getLocador(), novoImovel);
			if(locatario.isPresent()) {
				novoImovel.setLocatario(locatario.get());
				locatario.get().getImoveisAlugados().add(novoImovel);
			}
		}
		
		try {
			imovelResidencialRepository.salvar(novoImovel);
			return true;
		}catch(Exception e) {
			LOGGER.log(Level.SEVERE, "Não foi possível salvar o novo imóvel residencial");
			return false;
		}
			
	}

	public Boolean cadastrarImovelComercial(ImovelComercialDTO imovel) {

		List<ImovelComercial> imoveis = imovelComercialRepository
				.buscarTodos().stream().filter(comercio -> 
						comercio.getNumeroImovel().equals(imovel.getNumeroImovel()) || comercio.getRgi().equals(imovel.getRgi())
					).collect(Collectors.toList());
		
		if(!imoveis.isEmpty())
			return false;
		ImovelComercial comercio = imovel.build();
		
		if(imovel.getProprietario() != null) {
			Optional<Proprietario> proprietario = proprietarioService.cadastrarProprietario(imovel.getProprietario(),comercio);
			if(proprietario.isPresent()) {
				comercio.setDonoImovel(proprietario.get());
			}
		}
		if(imovel.getLocador()!=null) {
			Optional<Locatario> locatario = locatarioService.cadastrarLocatario(imovel.getLocador(),comercio);
			if(locatario.isPresent()) {
				comercio.setLocatario(locatario.get());
			}
		}
		try {
			imovelComercialRepository.salvar(comercio);
			return true;
		}catch(Exception e) {
			LOGGER.log(Level.SEVERE, "Não foi possível salvar o novo imóvel comercial");
			return false;
		}
	}

	public List<ImovelResidencial> buscarTodosImoveisResidenciais() {
		return imovelResidencialRepository.buscarTodos();
	}

	public List<ImovelComercial> buscarTodosImoveisComerciais() {
		return imovelComercialRepository.buscarTodos();
	}
	
	public boolean gerarRelatorioTodosImoveisComerciais(String path, Relatorio relatorio) {
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
				document.add(new Paragraph("Relatório: Imóvel Comercial.                            " 
						+ "Relatório gerado em: " + java.text.DateFormat.getDateInstance(DateFormat.MEDIUM)
									.format(data)).setFont(bold));
			}else {
				document.add(new Paragraph("Relatório: Todos os imóveis Comerciais.             " 
						+ "Relatório gerado em: " + java.text.DateFormat.getDateInstance(DateFormat.MEDIUM)
									.format(data)+".").setFont(bold));
				document.add(new Paragraph("Relatório gerado com: "+relatorio.getNumeroDeImoveis()+" imóveis comerciais."));
			}
			
			if(!relatorio.getImoveisPresentesRelatorio().isEmpty()) {
				for(Imovel imovel : relatorio.getImoveisPresentesRelatorio()) {
					Table table = montaTabelaComercial(bold,font,imovel);
					document.add(new Paragraph());
					document.add(new Paragraph());
					document.add(table);
				}
				document.add(new Paragraph());
			}
			if(relatorio.getNumeroDeImoveis() == 1) {
				addInformacaoParaImovelComercialUnico(relatorio,document);
			}
				document.close();
		}catch(FileNotFoundException e){
			LOGGER.log(Level.SEVERE, "Falha ao tentar encontrar caminho para gerar o relatório: Relatório não gerado.");
			return false;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Falha durante a leitura do arquivo");
			return false;
		}
		return true;
	}
	
	private void addInformacaoParaImovelComercialUnico(Relatorio relatorio, Document document) {
		ImovelComercial imovelComercial = (ImovelComercial)relatorio.getImoveisPresentesRelatorio().get(0);
		
		if(imovelComercial.getProcessos() != null && !imovelComercial.getProcessos().isEmpty()) {
			List<ProcessoCondominial> lista = imovelComercial.getProcessos().stream()
					.filter(processo -> processo.getProcessoAtivo())
					.collect(Collectors.toList());
			Integer processos = lista.size();
			document.add(new Paragraph("Número de processos ativos deste imóvel: "+ processos.toString()+"."));
			int i =1;
			for(ProcessoCondominial processo : lista) {
				document.add(new Paragraph("    "+i +" - Número do processo: "+ processo.getCodigoProcesso()));
				i++;
			}
		}
		
	}

	private Table montaTabelaComercial(PdfFont bold, PdfFont font, Imovel imovel) {
		Table table = new Table(new float[]{1,1,1});
		table.setWidth(UnitValue.createPercentValue(100));
		table.setFixedLayout();
		processComercial(table,(ImovelComercial)imovel,bold,true);
		process(table, imovel, font, false);
		processComercial(table,(ImovelComercial)imovel,font,false);
		return table;
	}

	private void processComercial(Table table, ImovelComercial imovel, PdfFont font, boolean isHeader) {
		if (isHeader) {
            table.addHeaderCell(
                new Cell(1,2).add( 
                    new Paragraph("Nº do comercio: " + imovel.getNumeroImovel().toString()).setFont(font)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            if(imovel.getRgi()!=null && !imovel.getRgi().isEmpty()) {
            table.addHeaderCell(
                    new Cell(1,2).add(
                        new Paragraph("RGI imóvel: " + imovel.getRgi().toString()).setFont(font)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            }else {
            	table.addHeaderCell(
                        new Cell(1,2).add(
                            new Paragraph("RGI do imóvel não informado: " + imovel.getRgi().toString()).setFont(font)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            }
        // Caso onde as informações serão geradas no corpo das tabelas no pdf
        }else {
        	if(imovel.iseSobreloja() != null && imovel.iseSobreloja()) {
    		    table.addCell(
    		            new Cell().add(
    		                new Paragraph("É sobreloja").setFont(font).setFontSize(11)));
    	    }else if(imovel.iseSobreloja() != null){
    	    	table.addCell(
    		            new Cell().add(
    		                new Paragraph("É loja").setFont(font).setFontSize(11)));
    	    }else {
    	    	table.addCell(
    		            new Cell().add(
    		                new Paragraph("Não informado").setFont(font).setFontSize(11)));
    	    }
        	
        	if(imovel.getTipoLoja()!=null) {
        		if(imovel.getTipoLoja().getNomeDoTipoComercio()!=null && !imovel.getTipoLoja().getNomeDoTipoComercio().isEmpty()) {
        			table.addCell(
        		            new Cell().add(
        		                new Paragraph("Tipo de loja: " + imovel.getTipoLoja().getNomeDoTipoComercio()).setFont(font).setFontSize(11)));
        		}else {
        			table.addCell(
        		            new Cell().add(
        		                new Paragraph("Tipo de loja não informado" + imovel.getTipoLoja().getNomeDoTipoComercio()).setFont(font).setFontSize(11)));
        		}
        	}else {
        		table.addCell(
    		            new Cell().add(new Paragraph("Tipo de loja não informado").setFont(font).setFontSize(11)));
        	}
        }
	}

	public boolean gerarRelatorioTodosImoveisResidenciais(String path, Relatorio relatorio) {
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
				document.add(new Paragraph("Relatório: Imóvel Residencial.             " 
						+ "Relatório gerado em: " + java.text.DateFormat.getDateInstance(DateFormat.MEDIUM)
									.format(data)).setFont(bold));
			}else {
				document.add(new Paragraph("Relatório: Todos os imóveis Residênciais.             " 
						+ "Relatório gerado em: " + java.text.DateFormat.getDateInstance(DateFormat.MEDIUM)
									.format(data)).setFont(bold));
				document.add(new Paragraph("Relatório gerado com: "+relatorio.getNumeroDeImoveis()+" imóveis residenciais."));
			}
			
			if(!relatorio.getImoveisPresentesRelatorio().isEmpty()) {
				for(Imovel imovel : relatorio.getImoveisPresentesRelatorio()) {
					Table table = montaTabelaResidencial(bold,font,imovel);
					document.add(new Paragraph());
					document.add(new Paragraph());
					document.add(table);
				}
				document.add(new Paragraph());
			}
			if(relatorio.getNumeroDeImoveis() == 1) {
				addInformacaoParaImovelResidencialUnico(relatorio,document);
			}
				document.close();
		}catch(FileNotFoundException e){
			LOGGER.log(Level.SEVERE, "Falha ao tentar encontrar caminho para gerar o relatório: Relatório não gerado.");
			return false;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Falha durante a leitura do arquivo");
			return false;
		}
		return true;
	}
	
	private void addInformacaoParaImovelResidencialUnico(Relatorio relatorio, Document document) {
		ImovelResidencial imovelResidencial = (ImovelResidencial)relatorio.getImoveisPresentesRelatorio().get(0);
		String sim ="";
		if(imovelResidencial.isPossuiAnimalEstimacao()==null) sim = "Não.";
		else {
			sim = imovelResidencial.isPossuiAnimalEstimacao()? "Sim.":"Nao.";
		}
		document.add(new Paragraph("Possui animal de estimação: "+ sim));
		
		if(!imovelResidencial.getMoradores().isEmpty() || imovelResidencial.getMoradores() != null) {
			Integer moradores = imovelResidencial.getMoradores().size();
			document.add(new Paragraph("Número de moradores deste imóvel: "+ moradores.toString()+"."));
			int i =1;
			for(Pessoa pessoa : imovelResidencial.getMoradores()) {
				document.add(new Paragraph("Morador "+ i +": "+ pessoa.getNome()));
				i++;
			}
		}

		if( imovelResidencial.getProcessos() != null && !imovelResidencial.getProcessos().isEmpty()) {
			List<ProcessoCondominial> lista = imovelResidencial.getProcessos().stream()
					.filter(processo -> processo.getProcessoAtivo())
					.collect(Collectors.toList());
			Integer processos = lista.size();
			document.add(new Paragraph("Número de processos ativos deste imóvel: "+ processos.toString()+"."));
			int i =1;
			for(ProcessoCondominial processo : lista) {
				document.add(new Paragraph("    "+i +" - Número do processo: "+ processo.getCodigoProcesso()));
				i++;
			}
		}	
	}

	private Table montaTabelaResidencial(PdfFont bold, PdfFont font, Imovel imovel) {
		Table table = new Table(new float[]{1,1});
		table.setWidth(UnitValue.createPercentValue(100));
		table.setFixedLayout();
		process(table, imovel, bold, true);
		process(table, imovel, font, false);
		return table;
	}

	private void process(Table table, Imovel imovel, PdfFont font, boolean isHeader) {
		//Caso em que as informações devem ser geradas como cabeçalho das tabelas no pdf
		if (isHeader) {
            table.addHeaderCell(
                new Cell().add( 
                    new Paragraph("Nº do apartamento: " + imovel.getNumeroImovel().toString()).setFont(font)));
            if(imovel.getRgi()!=null && !imovel.getRgi().isEmpty()) {
            table.addHeaderCell(
                    new Cell().add(
                        new Paragraph("RGI imóvel: " + imovel.getRgi().toString()).setFont(font)));
            }else {
            	table.addHeaderCell(
                        new Cell().add(
                            new Paragraph("RGI do imóvel não informado: " + imovel.getRgi().toString()).setFont(font)));
            }
        // Caso onde as informações serão geradas no corpo das tabelas no pdf
        }else {
        	
        	adicionaInformacaoSobreDonoDoImovel(imovel,table,font);
		    adicionaInformacaoSobreLocatario(imovel,table,font);
		    
		    //barbara
		    if(imovel.getTrocouBarbara() != null && imovel.getTrocouBarbara()) {
			    table.addCell(
			            new Cell().add(
			                new Paragraph("Trocou barbará").setFont(font).setFontSize(11)));
		    }else if(imovel.getTrocouBarbara() != null) {
		    	table.addCell(
			            new Cell().add(
			                new Paragraph("Não trocou barbará").setFont(font).setFontSize(11)));
		    }else {
		    	table.addCell(
			            new Cell().add(
			                new Paragraph("Não informado").setFont(font).setFontSize(11)));
		    }
		    
		    //coluna
		    if(imovel.getTrocouColuna() != null && imovel.getTrocouColuna()) {
			    table.addCell(
			            new Cell().add(
			                new Paragraph("Trocou coluna de água").setFont(font).setFontSize(11)));
		    }else if(imovel.getTrocouColuna() != null) {
		    	table.addCell(
			            new Cell().add(
			                new Paragraph("Não trocou coluna de água").setFont(font).setFontSize(11)));
		    }else {
		    	table.addCell(
			            new Cell().add(
			                new Paragraph("Não informado").setFont(font).setFontSize(11)));
		    }
		    
		    //processo
		    if(possuiProcessoAtivo(imovel)) {
		    	table.addCell(
		            new Cell().add(
		                new Paragraph("Possui ação condominial").setFont(font).setFontSize(11)));
		    }else {
		    	table.addCell(
			        new Cell().add(
			            new Paragraph("Não possui ação condominial").setFont(font).setFontSize(11)));
		    }
		    
		    //contato emergencia
		    if(imovel.getContatoEmergencia()!=null) {
		    	if(imovel.getContatoEmergencia().getNome()!=null && !imovel.getContatoEmergencia().getNome().isEmpty()) {
				    table.addCell(
				    		new Cell().add(
				                new Paragraph("Contato emergência: " + imovel.getContatoEmergencia().getNome()).setFont(font).setFontSize(11)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Contato emergência não informado").setFont(font).setFontSize(11)));
		    	}
		    	if(imovel.getContatoEmergencia().getCelular() !=null && !imovel.getContatoEmergencia().getCelular().isEmpty()) {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel emergência: " + imovel.getContatoEmergencia().getCelular()).setFont(font).setFontSize(11)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Tel emergência não informado").setFont(font).setFontSize(11)));
		    	}
		    }else {
		    	table.addCell(
			    		new Cell().add(
			                new Paragraph("Contato emergência não informado").setFont(font).setFontSize(11)));
		    	table.addCell(
			    		new Cell().add(
			                new Paragraph("Tel emergência não informado").setFont(font).setFontSize(11)));
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
	    	if(imovel.getLocatario().getNome()!=null && !imovel.getLocatario().getNome().isEmpty()) {
			    table.addCell(
			    		new Cell().add(
			                new Paragraph("Nome locatário: " + imovel.getLocatario().getNome()).setFont(font).setFontSize(11)));
	    	}else {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Nome locatário não informado").setFont(font).setFontSize(11)));
	    	}
	    	if(imovel.getLocatario().getEmail()!=null && !imovel.getLocatario().getEmail().isEmpty()) {
			    table.addCell(
			    		new Cell().add(
			                new Paragraph("Email locatário: " + imovel.getLocatario().getEmail()).setFont(font).setFontSize(11)));
	    	}else {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Email locatário não informado").setFont(font).setFontSize(11)));
	    	}
	    	if(imovel.getLocatario().getTelefone() !=null && !imovel.getLocatario().getTelefone().isEmpty()) {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Tel locatário: " + imovel.getLocatario().getTelefone()).setFont(font).setFontSize(11)));
	    	}else {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Tel locatário não informado").setFont(font).setFontSize(11)));
	    	}
	    	if(imovel.getLocatario().getCelular() !=null && !imovel.getLocatario().getCelular().isEmpty()) {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Cell locatário: " + imovel.getLocatario().getCelular()).setFont(font).setFontSize(11)));
	    	}else {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Cell locatário não informado").setFont(font).setFontSize(11)));
	    	}
	    }else {
	    	table.addCell(
		    		new Cell().add(
		                new Paragraph("Nome locatário não informado").setFont(font).setFontSize(11)));
	    	table.addCell(
		    		new Cell().add(
		                new Paragraph("Email locatário não informado").setFont(font).setFontSize(11)));
	    	table.addCell(
			    	new Cell().add(
			               new Paragraph("Tel locatário não informado").setFont(font).setFontSize(11)));
	    	table.addCell(
			    	new Cell().add(
			               new Paragraph("Cell locatário não informado").setFont(font).setFontSize(11)));
	    }
		
	}

	private void adicionaInformacaoSobreDonoDoImovel(Imovel imovel, Table table, PdfFont font) {
		if(imovel.getDonoImovel()!=null) {
	    	if(imovel.getDonoImovel().getNome()!=null && !imovel.getDonoImovel().getNome().isEmpty()) {
			    table.addCell(
			    		new Cell().add(
			                new Paragraph("Nome proprietário: " + imovel.getDonoImovel().getNome()).setFont(font).setFontSize(11)));
	    	}else {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Nome proprietário: não informado").setFont(font).setFontSize(11)));
	    	}
	    	if(imovel.getDonoImovel().getEmail()!=null && !imovel.getDonoImovel().getEmail().isEmpty()) {
			    table.addCell(
			    		new Cell().add(
			                new Paragraph("Email Proprietário: " + imovel.getDonoImovel().getEmail()).setFont(font).setFontSize(11)));
	    	}else {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Email Proprietário: não informado").setFont(font).setFontSize(11)));
	    	}
	    	if(imovel.getDonoImovel().getTelefone() !=null && !imovel.getDonoImovel().getTelefone().isEmpty()) {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Tel proprietário: " + imovel.getDonoImovel().getTelefone()).setFont(font).setFontSize(11)));
	    	}else {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Tel proprietário: não informado").setFont(font).setFontSize(11)));
	    	}
	    	if(imovel.getDonoImovel().getCelular() !=null && !imovel.getDonoImovel().getCelular().isEmpty()) {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Cell proprietário: " + imovel.getDonoImovel().getCelular()).setFont(font).setFontSize(11)));
	    	}else {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Cell proprietário: não informado").setFont(font).setFontSize(11)));
	    	}	    
	    }else {
	    	table.addCell(
		    		new Cell().add(
		                new Paragraph("Nome proprietário não informado").setFont(font).setFontSize(11)));
	    	table.addCell(
		    		new Cell().add(
		                new Paragraph("Email proprietário não informado").setFont(font).setFontSize(11)));
	    	table.addCell(
		    		new Cell().add(
		                new Paragraph("Tel proprietário não informado").setFont(font).setFontSize(11)));
	    	table.addCell(
		    		new Cell().add(
		                new Paragraph("Cell proprietário não informado").setFont(font).setFontSize(11)));
	    }
		
	}
	
	public List<ImovelResidencial> buscarImovelResidencialPorRGI(String rgi){
		List<ImovelResidencial> imoveis = imovelResidencialRepository.buscarTodos()
													.stream().filter(imovel -> imovel.getRgi().contains(rgi))
															.sorted(Comparator.comparing(ImovelResidencial :: getNumeroImovel))
																	.collect(Collectors.toList());
		return imoveis;
	}
	
	public List<ImovelResidencial> buscarImovelResidencialPorNumero(String numero){
		List<ImovelResidencial> imoveis = imovelResidencialRepository.buscarTodos()
				.stream().filter(imovel -> imovel.getNumeroImovel().toString().contains(numero))
								.sorted(Comparator.comparing(ImovelResidencial :: getNumeroImovel))
										.collect(Collectors.toList());
		return imoveis;
	}
	
	public List<ImovelResidencial> buscarImovelResidencialPorNomeLocatario(String nome){
		List<ImovelResidencial> imoveis = 
				imovelResidencialRepository.buscarTodos().stream()
												.sorted(Comparator.comparing(ImovelResidencial :: getNumeroImovel))
														.collect(Collectors.toList());
		List<ImovelResidencial> temp = new ArrayList<ImovelResidencial>();
		imoveis.forEach(imovel -> {
			if (imovel.getLocatario()!=null) {
				if(imovel.getLocatario().getNome()!=null && !imovel.getLocatario()
																	.getNome().isEmpty()) {
					if(imovel.getLocatario().getNome().toLowerCase().contains(nome.toLowerCase())) {
						temp.add(imovel);
					}
				}
			}
		});
		return temp;
	}
	
	public List<ImovelComercial> buscarImovelComercialPorRGI(String rgi){
		List<ImovelComercial> imoveis = imovelComercialRepository.buscarTodos()
													.stream().filter(imovel -> imovel.getRgi().contains(rgi))
															.sorted(Comparator.comparing(ImovelComercial :: getNumeroImovel))
																	.collect(Collectors.toList());
		return imoveis;
	}
	
	public List<ImovelComercial> buscarImovelComercialPorNumero(String numero){
		List<ImovelComercial> imoveis = imovelComercialRepository.buscarTodos()
				.stream().filter(imovel -> imovel.getNumeroImovel().toString().contains(numero))
						.sorted(Comparator.comparing(ImovelComercial :: getNumeroImovel))
								.collect(Collectors.toList());
		return imoveis;
	}
	
	public List<ImovelComercial> buscarImovelComercialPorNomeLocatario(String nome){
		List<ImovelComercial> imoveis = 
				imovelComercialRepository.buscarTodos().stream()
					.sorted(Comparator.comparing(ImovelComercial :: getNumeroImovel))
							.collect(Collectors.toList());
		List<ImovelComercial> temp = new ArrayList<ImovelComercial>();
		imoveis.forEach(imovel -> {
			if (imovel.getLocatario()!=null) {
				if(imovel.getLocatario().getNome()!=null && !imovel.getLocatario()
																	.getNome().isEmpty()) {
					if(imovel.getLocatario().getNome().toLowerCase().contains(nome.toLowerCase())) {
						temp.add(imovel);
					}
				}
			}
		});
		return temp;
	}
	
	public Boolean atualizarImovelComercial(ImovelComercialDTO imovel) {
		List<ImovelComercial> imoveis = imovelComercialRepository
				.buscarTodos().stream().filter(comercio -> 
						(comercio.getNumeroImovel().equals(imovel.getNumeroImovel()))
					).collect(Collectors.toList());
		
		if(imoveis.isEmpty())
			return false;
		imoveis.get(0).setCobrancaBoleto(imovel.getCobrancaBoleto());
		imoveis.get(0).setNomeLoja(imovel.getNomeLoja());
		imoveis.get(0).setTrocouColuna(imovel.getTrocouColuna());
		imoveis.get(0).setContatoEmergencia(imovel.getContatoEmergencia());
		imoveis.get(0).seteSobreloja(imovel.geteSobreloja());
		imoveis.get(0).setProcessos(imovel.getProcessos());
		imoveis.get(0).setRgi(imovel.getRgi());
		imoveis.get(0).setTipoLoja(imovel.getTipoLoja());
		imoveis.get(0).setTrocouBarbara(imovel.getTrocouBarbara());
		imoveis.get(0).setAcordo(imovel.getAcordo());
		
		Proprietario prop = imoveis.get(0).getDonoImovel();
		Locatario loc = imoveis.get(0).getLocatario();
		
		if(prop == null) {
			if(imovel.getProprietario()!=null) {
				prop = imovel.getProprietario().build();
				if(prop.getImoveis()== null) {
					prop.setImoveis(new ArrayList<Imovel>());
				}
				prop.getImoveis().add(imoveis.get(0));
				imoveis.get(0).setDonoImovel(prop);
			}
		}else {
			if(imovel.getProprietario() !=null ) {
				if(imovel.getProprietario().getCpf()!=null && !imovel.getProprietario().getCpf().isEmpty()) {
					if(imovel.getProprietario().getCpf().equals(prop.getCpf())) {
						imoveis.get(0).getDonoImovel().setCelular(imovel.getProprietario().getCelular());
						imoveis.get(0).getDonoImovel().setEmail(imovel.getProprietario().getEmail());
						imoveis.get(0).getDonoImovel().setEndereco(imovel.getProprietario().getEndereco());
						imoveis.get(0).getDonoImovel().setNome(imovel.getProprietario().getNome());
						imoveis.get(0).getDonoImovel().setTelefone(imovel.getProprietario().getTelefone());
					}else {
						imoveis.get(0).setDonoImovel(imovel.getProprietario().build());
					}
				}else {
					prop = removeImovelDoProprietario(imoveis);
					proprietarioRepository.salvar(imoveis.get(0).getDonoImovel());
					imoveis.get(0).setDonoImovel(null);
				}
			}
		}
		
		if(loc == null) {
			if(imovel.getLocador()!=null) {
				loc = imovel.getLocador().build();
				if(loc.getImoveisAlugados()== null) {
					loc.setImoveisAlugados(new ArrayList<Imovel>());
				}
				loc.getImoveisAlugados().add(imoveis.get(0));
				imoveis.get(0).setLocatario(loc);
			}
		}else {
			if(imovel.getLocador() !=null ) {
				if(imovel.getLocador().getCpf()!=null && !imovel.getLocador().getCpf().isEmpty()) {
					if(imovel.getLocador().getCpf().equals(loc.getCpf())) {
						imoveis.get(0).getLocatario().setCelular(imovel.getLocador().getCelular());
						imoveis.get(0).getLocatario().setEmail(imovel.getLocador().getEmail());
						imoveis.get(0).getLocatario().setNome(imovel.getLocador().getNome());
						imoveis.get(0).getLocatario().setTelefone(imovel.getLocador().getTelefone());
						imoveis.get(0).getLocatario().setPossuidor(imovel.getLocador().getPossuidor());
					}else {
						imoveis.get(0).setLocatario(imovel.getLocador().build());
					}
				}else {
					loc = removeImovelDoLocador(imoveis);
					locatarioRepository.salvar(imoveis.get(0).getLocatario());
					imoveis.get(0).setDonoImovel(null);
				}
			}
		}
		try {
			imovelComercialRepository.salvar(imoveis.get(0));
			if(prop.getImoveis().size() == 0) {
				proprietarioRepository.deletar(prop);
			}
			if(loc.getImoveisAlugados().size() == 0) {
				locatarioRepository.deletar(loc);
			}
			return true;
		}catch(Exception e) {
			LOGGER.log(Level.SEVERE, "Não foi possível atualizar o novo imóvel comercial",e);
			return false;
		}
	}
	
	private Locatario removeImovelDoLocador(List<ImovelComercial> imoveis) {
		for (Imovel imovel : imoveis.get(0).getLocatario().getImoveisAlugados()) {
			if(imovel.getNumeroImovel().equals(imoveis.get(0).getNumeroImovel())){
				imoveis.get(0).getLocatario().getImoveisAlugados().remove(imovel);
			}
		}
		return imoveis.get(0).getLocatario();
	}

	private Proprietario removeImovelDoProprietario(List<ImovelComercial> imoveis) {
		for (Imovel imovel : imoveis.get(0).getDonoImovel().getImoveis()) {
			if(imovel.getNumeroImovel().equals(imoveis.get(0).getNumeroImovel())){
				imoveis.get(0).getDonoImovel().getImoveis().remove(imovel);
			}
		}
		return imoveis.get(0).getDonoImovel();
	}

	public Boolean atualizarImovelResidencial(ImovelResidencialDTO imovelDTO) {
		List<ImovelResidencial> imoveis = imovelResidencialRepository
				.buscarTodos().stream()//
					.filter(residencia -> // 
						(residencia.getNumeroImovel().equals(imovelDTO.getNumeroImovel()))//
					).collect(Collectors.toList());//
		
		if(imoveis.isEmpty()) 
			return false;
		
		imoveis.get(0).setRgi(imovelDTO.getRgi());
		imoveis.get(0).setTrocouBarbara(imovelDTO.getTrocouBarbara());
		imoveis.get(0).setPossuiAnimalEstimacao(imovelDTO.getPossuiAnimalEstimacao());
		imoveis.get(0).setTrocouColuna(imovelDTO.getTrocouColuna());
		
		imoveis.get(0).setCobrancaBoleto(imovelDTO.getCobrancaBoleto());
		imoveis.get(0).setMoradores(imovelDTO.getMoradores());
		imoveis.get(0).setProcessos(imovelDTO.getProcessos());
		imoveis.get(0).setContatoEmergencia(imovelDTO.getContatoEmergencia());
		imoveis.get(0).setAcordo(imovelDTO.getAcordo());
		
		Proprietario prop = imoveis.get(0).getDonoImovel();
		Locatario loc = imoveis.get(0).getLocatario();
		
		if(prop == null) {
			if(imovelDTO.getProprietario()!=null) {
				prop = imovelDTO.getProprietario().build();
				if(prop.getImoveis()== null) {
					prop.setImoveis(new ArrayList<Imovel>());
				}
				prop.getImoveis().add(imoveis.get(0));
				imoveis.get(0).setDonoImovel(prop);
			}
		}else {
			if(imovelDTO.getProprietario() !=null ) {
				if(imovelDTO.getProprietario().getCpf()!=null && !imovelDTO.getProprietario().getCpf().isEmpty()) {
					if(imovelDTO.getProprietario().getCpf().equals(prop.getCpf())) {
						imoveis.get(0).getDonoImovel().setCelular(imovelDTO.getProprietario().getCelular());
						imoveis.get(0).getDonoImovel().setEmail(imovelDTO.getProprietario().getEmail());
						imoveis.get(0).getDonoImovel().setEndereco(imovelDTO.getProprietario().getEndereco());
						imoveis.get(0).getDonoImovel().setNome(imovelDTO.getProprietario().getNome());
						imoveis.get(0).getDonoImovel().setTelefone(imovelDTO.getProprietario().getTelefone());
					}else {
						imoveis.get(0).setDonoImovel(imovelDTO.getProprietario().build());
					}
				}else {
					prop = removeImovelDoProprietarioResidencial(imoveis);
					proprietarioRepository.salvar(imoveis.get(0).getDonoImovel());
					imoveis.get(0).setDonoImovel(null);
				}
			}
		}
		
		if(loc == null) {
			if(imovelDTO.getLocador()!=null) {
				loc = imovelDTO.getLocador().build();
				if(loc.getImoveisAlugados()== null) {
					loc.setImoveisAlugados(new ArrayList<Imovel>());
				}
				loc.getImoveisAlugados().add(imoveis.get(0));
				imoveis.get(0).setLocatario(loc);
			}
		}else {
			if(imovelDTO.getLocador() !=null ) {
				if(imovelDTO.getLocador().getCpf()!=null && !imovelDTO.getLocador().getCpf().isEmpty()) {
					if(imovelDTO.getLocador().getCpf().equals(loc.getCpf())) {
						imoveis.get(0).getLocatario().setCelular(imovelDTO.getLocador().getCelular());
						imoveis.get(0).getLocatario().setEmail(imovelDTO.getLocador().getEmail());
						imoveis.get(0).getLocatario().setNome(imovelDTO.getLocador().getNome());
						imoveis.get(0).getLocatario().setTelefone(imovelDTO.getLocador().getTelefone());
						imoveis.get(0).getLocatario().setPossuidor(imovelDTO.getLocador().getPossuidor());
					}else {
						imoveis.get(0).setLocatario(imovelDTO.getLocador().build());
					}
				}else {
					loc = removeImovelDoLocadorResidencial(imoveis);
					locatarioRepository.salvar(imoveis.get(0).getLocatario());
					imoveis.get(0).setDonoImovel(null);
				}
			}
		}
		
		try {
			imovelResidencialRepository.salvar(imoveis.get(0));
			if(prop.getImoveis().size() == 0) {
				proprietarioRepository.deletar(prop);
			}
			if(loc.getImoveisAlugados().size() == 0) {
				locatarioRepository.deletar(loc);
			}
			return true;
		}catch(Exception e) {
			LOGGER.log(Level.SEVERE, "Não foi possível salvar o novo imóvel residencial",e);
			return false;
		}
			
	}
	
	private Locatario removeImovelDoLocadorResidencial(List<ImovelResidencial> imoveis) {
		for (Imovel imovel : imoveis.get(0).getLocatario().getImoveisAlugados()) {
			if(imovel.getNumeroImovel().equals(imoveis.get(0).getNumeroImovel())){
				imoveis.get(0).getLocatario().getImoveisAlugados().remove(imovel);
			}
		}
		return imoveis.get(0).getLocatario();
	}

	private Proprietario removeImovelDoProprietarioResidencial(List<ImovelResidencial> imoveis) {
		for (Imovel imovel : imoveis.get(0).getDonoImovel().getImoveis()) {
			if(imovel.getNumeroImovel().equals(imoveis.get(0).getNumeroImovel())){
				imoveis.get(0).getDonoImovel().getImoveis().remove(imovel);
			}
		}
		return imoveis.get(0).getDonoImovel();
	}

	public Optional<ImovelResidencial> recuperarImovelResidencialPorNumero(String numero){
		List<ImovelResidencial> imoveis = imovelResidencialRepository.buscarTodos()
				.stream().filter(imovel -> imovel.getNumeroImovel().equals(numero)).collect(Collectors.toList());
		
		return Optional.ofNullable(imoveis.get(0));
	}
	
	public Optional<ImovelComercial> recuperarImovelComercialPorNumero(String numero){
		List<ImovelComercial> imoveis = imovelComercialRepository.buscarTodos()
				.stream().filter(imovel -> imovel.getNumeroImovel().equals(numero)).collect(Collectors.toList());
		if(imoveis.isEmpty()) return Optional.empty();
		return Optional.ofNullable(imoveis.get(0));
	}
}

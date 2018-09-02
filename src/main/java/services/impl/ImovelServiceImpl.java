package services.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
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
import resource.dto.ImovelComercialDTO;
import resource.dto.ImovelResidencialDTO;
import services.ImovelService;
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
	private ImovelResidencialAssembler imovelResidencialAssembler;
	
	@Inject
	private ProprietarioService proprietarioSrv;

	public Boolean cadastrarImovelResidencial(ImovelResidencialDTO imovelDTO) {
		
		List<ImovelResidencial> imoveis = imovelResidencialRepository
				.buscarTodos().stream()//
					.filter(residencia -> // 
						(residencia.getNumeroImovel().equals(imovelDTO.getNumeroImovel()))//
					).collect(Collectors.toList());//
		
		if(!imoveis.isEmpty()) 
			return false;
		
		ImovelResidencial novoImovel = new ImovelResidencial();
		
		novoImovel.setNumeroImovel(imovelDTO.getNumeroImovel());
		novoImovel.setRgi(imovelDTO.getRgi());
		novoImovel.setTrocouBarbara(imovelDTO.getTrocouBarbara());
		novoImovel.setPossuiAnimalEstimacao(imovelDTO.getPossuiAnimalEstimacao());
		
		novoImovel.setMoradores(imovelDTO.getMoradores());
		novoImovel.setProcessos(imovelDTO.getProcessos());
		novoImovel.setContatoEmergencia(imovelDTO.getContatoEmergencia());
		
		if(imovelDTO.getOidProprietario() != null && !imovelDTO.getOidProprietario().isEmpty()) {
			Optional<Proprietario> proprietario = proprietarioRepository.buscarPorID(imovelDTO.getOidProprietario());
			
			if(proprietario.isPresent())
				novoImovel.setDonoImovel(proprietario.get());
				proprietario.get().getImoveis().add(novoImovel);
		}
		
		if(imovelDTO.getOidLocador() != null && !imovelDTO.getOidLocador().isEmpty()) {
			Optional<Locatario> locatario = locatarioRepository.buscarPorID(imovelDTO.getOidLocador());
			
			if(locatario.isPresent())
				novoImovel.setLocatario(locatario.get());
			locatario.get().getImoveisAlugados().add(novoImovel);
		}
		
		try {
			imovelResidencialRepository.salvar(novoImovel);
			return true;
		}catch(Exception e) {
			LOGGER.log(Level.SEVERE, "N�o foi poss�vel salvar o novo im�vel residencial");
			return false;
		}
			
	}

	public Boolean cadastrarImovelComercial(ImovelComercialDTO imovel) {

		List<ImovelComercial> imoveis = imovelComercialRepository
				.buscarTodos().stream().filter(comercio -> 
						(comercio.getNumeroImovel().equals(imovel.getNumeroImovel()))
					).collect(Collectors.toList());
		
		if(!imoveis.isEmpty())
			return false;
		ImovelComercial comercio = imovel.build();
		
		if(imovel.getOidProprietario()!=null && !imovel.getOidProprietario().isEmpty()) {
			Optional<Proprietario> prop = proprietarioRepository.buscarPorID(imovel.getOidProprietario());
			if(prop.isPresent()) {
				comercio.setDonoImovel(prop.get());
				prop.get().getImoveis().add(comercio);
			}
		}
		if(imovel.getOidLocador()!=null && !imovel.getOidLocador().isEmpty()) {
			Optional<Locatario> loc = locatarioRepository.buscarPorID(imovel.getOidLocador());
			if(loc.isPresent()) {
				loc.get().getImoveisAlugados().add(comercio);
				comercio.setLocatario(loc.get());
			}
		}
		try {
			imovelComercialRepository.salvar(comercio);
			return true;
		}catch(Exception e) {
			LOGGER.log(Level.SEVERE, "N�o foi poss�vel salvar o novo im�vel comercial");
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
            if(imovel.getRgi()!=null && !imovel.getRgi().isEmpty()) {
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
        		if(imovel.getTipoLoja().getNomeDoTipoComercio()!=null && !imovel.getTipoLoja().getNomeDoTipoComercio().isEmpty()) {
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
		String pathFormatado = path+"relatorio"+java.text.SimpleDateFormat.getTimeInstance().format(data).replace(":","")+".pdf";
		try {
			FileOutputStream fos = new FileOutputStream(pathFormatado);
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
            if(imovel.getRgi()!=null && !imovel.getRgi().isEmpty()) {
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
		    	if(imovel.getContatoEmergencia().getNome()!=null && !imovel.getContatoEmergencia().getNome().isEmpty()) {
				    table.addCell(
				    		new Cell().add(
				                new Paragraph("Contato emerg�ncia: " + imovel.getContatoEmergencia().getNome()).setFont(font)));
		    	}else {
		    		table.addCell(
				    		new Cell().add(
				                new Paragraph("Contato emerg�ncia n�o informado").setFont(font)));
		    	}
		    	if(imovel.getContatoEmergencia().getCelular() !=null && !imovel.getContatoEmergencia().getCelular().isEmpty()) {
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
	    	if(imovel.getLocatario().getNome()!=null && !imovel.getLocatario().getNome().isEmpty()) {
			    table.addCell(
			    		new Cell().add(
			                new Paragraph("Nome Locat�rio: " + imovel.getLocatario().getNome()).setFont(font)));
	    	}else {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Nome locat�rio n�o informado").setFont(font)));
	    	}
	    	if(imovel.getLocatario().getCelular() !=null && !imovel.getLocatario().getCelular().isEmpty()) {
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
	    	if(imovel.getDonoImovel().getNome()!=null && !imovel.getDonoImovel().getNome().isEmpty()) {
			    table.addCell(
			    		new Cell().add(
			                new Paragraph("Nome Propriet�rio: " + imovel.getDonoImovel().getNome()).setFont(font)));
	    	}else {
	    		table.addCell(
			    		new Cell().add(
			                new Paragraph("Nome Propriet�rio: n�o informado").setFont(font)));
	    	}
	    	if(imovel.getDonoImovel().getCelular() !=null && !imovel.getDonoImovel().getCelular() .isEmpty()) {
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
	
	public List<ImovelResidencial> buscarImovelResidencialPorRGI(String rgi){
		List<ImovelResidencial> imoveis = imovelResidencialRepository.buscarTodos()
													.stream().filter(imovel -> imovel.getRgi().contains(rgi))
																	.collect(Collectors.toList());
		return imoveis;
	}
	
	public List<ImovelResidencial> buscarImovelResidencialPorNumero(String numero){
		List<ImovelResidencial> imoveis = imovelResidencialRepository.buscarTodos()
				.stream().filter(imovel -> imovel.getNumeroImovel().toString().contains(numero))
								.collect(Collectors.toList());
		return imoveis;
	}
	
	public List<ImovelResidencial> buscarImovelResidencialPorNomeLocatario(String nome){
		List<ImovelResidencial> imoveis = imovelResidencialRepository.buscarTodos();
		List<ImovelResidencial> temp = new ArrayList<ImovelResidencial>();
		imoveis.forEach(imovel -> {
			if (imovel.getLocatario()!=null) {
				if(imovel.getLocatario().getNome()!=null && !imovel.getLocatario()
																	.getNome().isEmpty()) {
					if(imovel.getLocatario().getNome().contains(nome)) {
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
																	.collect(Collectors.toList());
		return imoveis;
	}
	
	public List<ImovelComercial> buscarImovelComercialPorNumero(String numero){
		List<ImovelComercial> imoveis = imovelComercialRepository.buscarTodos()
				.stream().filter(imovel -> imovel.getNumeroImovel().toString().contains(numero))
								.collect(Collectors.toList());
		return imoveis;
	}
	
	public List<ImovelComercial> buscarImovelComercialPorNomeLocatario(String nome){
		List<ImovelComercial> imoveis = imovelComercialRepository.buscarTodos();
		List<ImovelComercial> temp = new ArrayList<ImovelComercial>();
		imoveis.forEach(imovel -> {
			if (imovel.getLocatario()!=null) {
				if(imovel.getLocatario().getNome()!=null && !imovel.getLocatario()
																	.getNome().isEmpty()) {
					if(imovel.getLocatario().getNome().contains(nome)) {
						temp.add(imovel);
					}
				}
			}
		});
		return temp;
	}
}

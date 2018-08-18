package services.impl;

import java.io.FileNotFoundException;
import java.util.Optional;

import javax.inject.Inject;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import domain.entity.negocio.ImovelResidencial;
import domain.entity.negocio.Relatorio;
import domain.repository.ImovelResidencialRepository;
import services.GeradorRelatorio;

public class GeradoRelatorioImpl implements GeradorRelatorio{
	
	@Inject
	private ImovelResidencialRepository imovelRepository;
	
	public Relatorio gerarRelatorioImoveisResidenciais() {	
		throw new UnsupportedOperationException("Funcionalidade não implementada");
	}
	

	public Relatorio gerarRelatorioImoveisComerciais() {
		throw new UnsupportedOperationException("Funcionalidade não implementada");
	}

	public Relatorio gerarRelatorioTodosImoveis() {
		throw new UnsupportedOperationException("Funcionalidade não implementada");
	}
	

}

package services.impl;

import java.io.FileNotFoundException;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import domain.entity.negocio.Relatorio;
import services.GeradorRelatorio;

public class GeradoRelatorioImpl implements GeradorRelatorio{

	public Relatorio gerarRelatorioImoveisResidenciais() {
		PdfWriter writer;
		try {
			writer = new PdfWriter("C:\\Users\\ronal\\Documents\\projeto goldstar\\gold-star-backend");
			PdfDocument pdf = new PdfDocument(writer);
			Document document = new Document(pdf);
			document.add(new Paragraph("Hello World!"));
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		throw new UnsupportedOperationException("Funcionalidade não implementada");
	}

	public Relatorio gerarRelatorioImoveisComerciais() {
		throw new UnsupportedOperationException("Funcionalidade não implementada");
	}

	public Relatorio gerarRelatorioTodosImoveis() {
		throw new UnsupportedOperationException("Funcionalidade não implementada");
	}
	

}

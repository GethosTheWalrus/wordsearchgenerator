package main;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Driver {
	
	public static void main(String[] args) throws IOException {
		
		WordSearch ws = new WordSearch(20,20,10);
		
		System.out.println("Find these words:");
		for(int i = 0; i < ws.numberOfWords(); i++) {
			
			System.out.println(ws.getWord(i).toUpperCase());
			
		}
		
		// set up the pdf to contain the content
		ws.printWordSearch();
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
		PDPageContentStream contents = new PDPageContentStream(document, page);
		
		// write content to the pdf
		contents.beginText();
		
		PDFont font = PDType1Font.HELVETICA_BOLD;
		contents.setFont(font, 30);
		
		contents.newLineAtOffset(50, 700);
		
		contents.showText("Hello");
		
		contents.endText();
		
		contents.close();
		
		// close the PDF
		document.save("files/wordsearch.pdf");
		System.out.println("PDF created");
		document.close();
		
	}

}

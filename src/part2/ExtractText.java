package part2;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
/**
 * Extracts the text from the pdf and puts it into a .txt file
 * @author Justin Mattix, David Jones, Taden Duerod
 * @version 1.0
 * Compiler Project 3
 * CS322 - Compiler Construction
 * Fall 2021
 */
public class ExtractText {
    public static void main(String filename)
    {
        File file = new File(filename);
        PDDocument document;
        try {
            document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            FileWriter myWriter = new FileWriter("Part2.txt");
            myWriter.write(text);
            myWriter.close();
            //Closing the document
            document.close();
            System.out.println("Text extracted!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    /**
     * Used in ProcessSchedule to call the main method of ExtractText
     */
	public void call() {
        main(null);
    }
}
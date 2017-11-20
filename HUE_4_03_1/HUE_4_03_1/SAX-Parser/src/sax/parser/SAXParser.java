package sax.parser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class SAXParser {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        try {
            
            File inputFile = new File("data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            boolean terminate = false;
            while (!terminate) {
                System.out.println("\nGeben Sie die Trainingseinheit die Sie ansehen wollen ein: ");

                
                Scanner sc = new Scanner(System.in);
                String input = sc.next();

                
                Trainingseinheit te = new Trainingseinheit(doc, input);
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(SAXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

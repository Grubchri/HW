
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class HUE_4_03_1_XML_Parser {

    
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
       
        File f = new File("Sportsdata.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(f);
        
        menue(doc);
        
    }

    private static void menue(Document doc) {
        
        System.out.println("following input values are allowed:\nD for devices \nU for Sportsunit, which contains; pulse, waypoints, used devices and speed\nstop to stop this program");
        Scanner sc=new Scanner(System.in);
        String input=sc.next();
        
        while(!(input.toUpperCase().equals("STOP"))){

            if(input.toUpperCase().equals("U") || input.toUpperCase().equals("D")){
                System.out.print("please enter a sportsname:");
                Scanner su=new Scanner(System.in);
                String sport=su.next();
                Sportsunit s = new Sportsunit();
                s.GetSportsUnit(sport, doc, input);
            }else{
                if(input.toUpperCase().equals("HELP")){
                    System.out.println("following input values are allowed:\nD for devices \nU for Sportsunit, which contains; pulse, waypoints, used devices and speed\nstop to stop this program");
                }else{
                    System.out.println("Wrong input, pls use Help to see which input values are available!");
                }
            }
            
            System.out.println("----------------------");
            input=sc.next();
            
        }
        
    }
    
}

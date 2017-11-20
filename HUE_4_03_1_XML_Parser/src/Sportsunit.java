
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


class Sportsunit {
    int start=0;
    int end=0;
    String name="";
    int [] waypoint=new int[3];
    int [] pulse=new int[3];
    int [] speed=new int[3];
    String kategorie="";
    String Devicename;
    Device d = new Device(name,Devicename);
    
    public void GetSportsUnit(String name,Document doc, String input){
        
        NodeList l = doc.getFirstChild().getChildNodes();
        Node n= null;
        
        for (int i = 0; i < l.getLength(); i++){
            Node temp = l.item(i);
            NamedNodeMap m = temp.getAttributes();
            if (m != null){
                boolean fin = false;
                for (int j = 0; j < m.getLength(); j++){
                    if (m.item(j).getNodeName().equals("name") && m.item(j).getNodeValue().equals(name)){
                        fin = true;
                    }
                }

                if (fin){
                    n = temp;
                    break;
                }
            }
        }
        
        if(n==null){
            System.out.println("Dieses Sportgerät existiert nicht!");
            return;
        }else{
            saveData(l);
        
            if(input.toUpperCase().equals("D")){
                printDeviceInfo();
            }else{
                printSportsInfo();
            }
        }
    }
    
    public void printDeviceInfo(){
        for(int i=0;i<d.sportsname.size();i++){
            System.out.print(d.sportsname.get(i));
            System.out.print(", ");
        }
        System.out.println("use "+Devicename);
    }
    
    public void printSportsInfo(){
        System.out.println("Startpoint: "+start);
        System.out.println("Endpoint: "+end);
        System.out.println("Kategorie: "+kategorie);
        System.out.println("Device: "+d.Devicename);
        System.out.println("Geschwindigkeit: "+(speed[0]+speed[1]+speed[2])/3);
    }

    private void saveData(NodeList l) {
        for(int i=0;i<l.getLength();i++){
            Node n = l.item(i);
            String tn = n.getNodeName();//Name of Tag
            String tv = n.getTextContent();//Name of Value
            String[] valuesplit = tv.split(",");
            switch(tn){
                case "Start": this.start=Integer.parseInt(tv);  break;
                case "Ende": this.end=Integer.parseInt(tv); break;
                case "Kategorie": this.kategorie=tv; break;
                case "Pulse": 
                        for(int j=0;j<pulse.length;j++){
                            waypoint[i]=Integer.parseInt(valuesplit[i]);
                        }
                break;
                case "Geschwindigkeiten": 
                        for(int j=0;j<speed.length;j++){
                            waypoint[i]=Integer.parseInt(valuesplit[i]);
                        }
                        break;
                case "Geraete": d.Devicename=tv; break;
            }
            saveData(n.getChildNodes());
        }
        
    }
    
}

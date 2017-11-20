

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sax.parser.Geraete;
import sax.parser.Route;


public class Trainingseinheit {

    private int start = 0;
    private int ende = 0;
    private String kategorie = "";
    private Route route = new Route();
    private Geraete geraete = new Geraete();

    public Trainingseinheit(Document doc, String attribut) {
        
        NodeList list = doc.getFirstChild().getChildNodes();
        Node n = null;

        for (int i = 0; i < list.getLength(); i++) {
            Node temp = list.item(i);
            NamedNodeMap map = temp.getAttributes();
            if (map != null) {
                boolean finished = false;
                for (int j = 0; j < map.getLength(); j++) {
                    if (map.item(j).getNodeName().equals("name") && map.item(j).getNodeValue().equals(attribut)) {
                        finished = true;
                    }
                }

                if (finished) {
                    n = temp;
                    break;
                }
            }
        }

        if (n == null) {
            System.err.println("Diese Trainingseinheit gibt es nicht!");
            System.out.println();
            return;
        }

        parseNode(n.getChildNodes());

        datenausgabe();
    }

    private void datenausgabe() {
        System.out.println("Startzeit: " + start + " Uhr");
        System.out.println("Endzeit: " + ende + " Uhr");
        System.out.println("Kategorie: " + kategorie);
        System.out.println("Dauer: " + dauer() + " Stunden");
        System.out.println("Durchschnittsgeschwindigkeit: " + durchschnittsgeschwindigkeit() + " km/h");
        System.out.println("Durchschnittspuls: " + durchschnittspuls());
        System.out.println("Von " + geraete() + " zurückgelegte Strecke: " + geraetstrecke() + " km");
    }

    private void parseNode(NodeList list) {
        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
            String tagName = n.getNodeName();
            String tagValue = n.getTextContent();
            String[] temp = tagValue.split(",");

            switch (tagName) {
                case "Start":
                    start = Integer.parseInt(tagValue);
                    break;
                case "Ende":
                    ende = Integer.parseInt(tagValue);
                    break;
                case "Kategorie":
                    kategorie = tagValue;
                    break;
                case "Wegpunkte":
                    for (int j = 0; j < temp.length; j++) {
                        route.addWegunkt(Integer.parseInt(temp[j]));
                    }
                    break;
                case "Pulse":
                    for (int j = 0; j < temp.length; j++) {
                        route.addPuls(Integer.parseInt(temp[j]));
                    }
                    break;
                case "Geschwindigkeiten":
                    for (int j = 0; j < temp.length; j++) {
                        route.addGeschwindigkeit(Integer.parseInt(temp[j]));
                    }
                    break;
                case "Schuhe":
                    geraete.setSchuhe(tagValue);
                    break;
                case "Weiteres":
                    geraete.setWeiteres(tagValue);
                    break;
            }
            parseNode(n.getChildNodes());
        }
    }

    // Prämisse: Es werden keine Falschwerte in die XML-Datei geschrieben
    private int dauer() {
        return start < ende ? ende - start : 24 - (start - ende);
    }

    private int durchschnittsgeschwindigkeit() {
        double ret = 0;
        for (int i = 0; i < route.getGeschwindigkeiten().size(); i++) {
            ret += (int) route.getGeschwindigkeiten().get(i);
        }
        ret /= route.getGeschwindigkeiten().size();
        return (int) Math.round(ret);
    }

    private int durchschnittspuls() {
        double ret = 0;
        for (int i = 0; i < route.getPulse().size(); i++) {
            ret += (int) route.getPulse().get(i);
        }
        ret /= route.getPulse().size();
        return (int) Math.round(ret);
    }

    private int geraetstrecke() {
        return (int) route.getWegpunkte().get(route.getWegpunkte().size() - 1);
    }

    private String geraete() {
        return geraete.getSchuhe() + " und " + geraete.getWeiteres();
    }

}

package sax.parser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexr
 */
public class Route {
    private List wegpunkte = new ArrayList();
    private List pulse = new ArrayList();
    private List geschwindigkeiten = new ArrayList();

    public void addWegunkt(int punkt) {
        wegpunkte.add(punkt);
    }
    
    public void addPuls(int puls) {
        pulse.add(puls);
    }
    
    public void addGeschwindigkeit(int geschwindigkeit) {
        geschwindigkeiten.add(geschwindigkeit);
    }

    public List getWegpunkte() {
        return wegpunkte;
    }

    public List getPulse() {
        return pulse;
    }

    public List getGeschwindigkeiten() {
        return geschwindigkeiten;
    }
    
    
}

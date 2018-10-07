package package01;

import java.util.HashMap;

public class Composant {
    protected HashMap ports = new HashMap<String,Object>();

    void connect(String portName, Object ref){
        this.ports.put(portName,ref);
    }
}

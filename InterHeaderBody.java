package package01;

import java.io.BufferedReader;
import java.util.ArrayList;

public interface InterHeaderBody {
    void flushIn();
    void setIn(BufferedReader in);
    void readResponse();
    ArrayList<String> getHeaders();
    ArrayList<String> getBody();
    ArrayList<String> getCookies();
    String getToken();
    String getLocation();
    String getID();
    String getNumRespense();
    void printBody();

}

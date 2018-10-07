package package01;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class HeaderBody extends Composant implements InterHeaderBody{

    ArrayList<String> headers;
    ArrayList<String> body;
    ArrayList<String> cookies;
    String location;
    String numRespense;
    String id = "";
    BufferedReader in;
    boolean test = true;

    @Override
    public void flushIn() {
        in = null;
    }

    @Override
    public void setIn(BufferedReader in) {
        this.in = in;
        this.test = true;
        headers = new ArrayList<String>();
        body = new ArrayList<String>();

        readResponse();
        System.err.println("----------------------------------------------------");
        printBody();
    }

    @Override
    public void readResponse() {
        try {
            String line = "";
            while((line = in.readLine()) != null){
                // System.out.println(line);
                if(line.isEmpty()){
                    test = false;
                }
                if(test){
                    headers.add(line);
                }
                else{
                    body.add(line);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getHeaders() {
        return headers;
    }

    @Override
    public ArrayList<String> getBody() {
        return body;
    }

    @Override
    public ArrayList<String> getCookies() {
        cookies = new ArrayList<String>();
        for(String header : headers){
            //System.out.println(header.startsWith("Set-Cookie"));
            if(header.startsWith("Set-Cookie")){
                int i = header.indexOf(":");
                int j = header.indexOf(";");
                cookies.add(header.substring(i+2, j));
            }
        }
        return cookies;
    }

    @Override
    public String getToken() {
        for(String line : body){
            if(line.startsWith("<input type=\"hidden\" name=\"_token\"")){
                int i = line.indexOf("name=\"_token\" value=\"")+20;
                int j = line.indexOf(">");
                String token = line.substring(i+1, j-1);
                return token;
            }
            //return null;
        }
        return null;
    }

    @Override
    public String getLocation() {
        for(String header : headers){
            //System.out.println(header.startsWith("Set-Cookie"));
            if(header.startsWith("Location")){
                int i = header.indexOf(":");
                //int j = header.indexOf("\r\n");
                location = header.substring(i+3, header.length());
                int j = location.indexOf("_id=");
                if(j != -1) {
                    System.out.println("------"+j);
                    j = j + 4;
                    id = location.substring(j,location.length());
                }
            }

        }
        return location;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getNumRespense() {
        try {
            numRespense = headers.get(0);
            return numRespense;
        }catch(Exception e){
            return "MEssage error : : "+e.getMessage();
        }

    }

    @Override
    public void printBody() {
        for(String line : body){
            System.out.println(line);
        }
    }
}

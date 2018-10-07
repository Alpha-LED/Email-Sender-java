package package01;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class HttpSocket extends Composant implements InterHttpSocket {

    private final int PORT = 80;
    private final String HOST = "mail.univ-bouira.dz";
    private final String CR = "\r\n";
    private final String HTTP = "HTTP/1.1";
    private final String USER_AGENT ="Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.167 Safari/537.36";
    private final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";

    private InterHeaderBody headerBody;

    private Socket httpSocket;
    private PrintWriter out;
    private BufferedReader in;
    private InetAddress address;

    private int legth=0;
    private String data;
    private String token;
    private String cookie = null;
    private String location = "";
    private String id = "";

    private String user;
    private String pass;
    private String email;
    private String subject;
    private String message;

    //Constructeur


    @Override
    public void requireInter() {
        headerBody = (InterHeaderBody) ports.get("headerBody");
    }

    @Override
    public void initialSIO() {
        try {
            address = InetAddress.getByName(HOST);
            httpSocket = new Socket(address, PORT);
            //System.err.println(httpSocket.getInetAddress());
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(httpSocket.getOutputStream())), true);
            in = new BufferedReader(new InputStreamReader(httpSocket.getInputStream()));
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void httpGET(String request) {

        out.println("GET "+request+" "+HTTP+"\r");
        out.println("Host: "+HOST+"\r");
        out.println("Connection: keep-alive"+"\r");
        out.println("User-Agent: "+USER_AGENT+"\r");
        if(cookie != null){
            out.println("Cookie: "+cookie+"\r");
        }

        out.println("Accept: "+ACCEPT+"\r");
        out.println("Accept-Language: fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7"+"\r");
        out.println("Accept-Encoding: none"+"\r");
        out.println("\r");
    }

    @Override
    public void httpPOST(String location) {

        try {
            data = "_token="+URLEncoder.encode(token, "UTF-8")+"&_task=login&_action=login&_timezone=Europe%2FParis&_url=&_user="+user+"&_pass="+pass;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        legth = data.length();

        out.println("POST "+location+" "+HTTP+"\r");
        // System.err.println("POST "+location+" "+HTTP);
        out.println("Host: "+HOST+"\r");
        out.println("Connection: keep-alive"+"\r");
        out.println("Content-Type: application/x-www-form-urlencoded"+"\r");
        out.println("Cookie: "+cookie+"\r");
        // out.println("Origin: http://"+HOST);
        // out.println("Referer: http://"+HOST+"/");
        out.println("User-Agent: "+USER_AGENT+"\r");
        out.println("Accept: "+ACCEPT+"\r");
        out.println("Accept-Language: fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7"+"\r");
        out.println("Accept-Encoding: none"+"\r");
        out.println("Content-Length: "+legth+"\r");
        out.println("\r");
        out.println(data);
    }

    @Override
    public void readResponse() {
        headerBody.flushIn();
        headerBody.setIn(in);
        ArrayList<String> headers = headerBody.getHeaders();
        ArrayList<String> body = headerBody.getBody();
        ArrayList<String> cookies = headerBody.getCookies();



        System.out.println(headerBody.getNumRespense());

        if(cookies.size() == 3){
            cookie = cookies.get(1)+"; "+cookies.get(2);
        }else{
            try{
                this.cookie = cookies.get(0);
            }catch(Exception e){

            }
        }
        for(String cookie: cookies){
            //System.out.println(cookie);
            //this.cookie = cookie;
        }

        token = headerBody.getToken();
        System.err.println(token);

        location = headerBody.getLocation();
        System.err.println("This is the next Location : "+location);
        if(headerBody.getID().length() >= 5)
            id = headerBody.getID();
        System.err.println("Id === "+id);
    }

    @Override
    public void postMessage(String location) {
        try {
            data = "_token="+URLEncoder.encode(token, "UTF-8")
                    +"&_task=mail&_action=send&_id="+id+
                    "&_attachments=&_from=58&_to="+ email +
                    "&_cc=&_bcc=&_replayto=&_followupto=&_subject="+ subject +
                    "&editorSelector=html&_priority=0&_store_target=Sent&" +
                    "_draft_saveid=&_draft=&_is_html=1&_framed=1&_message="
                    +URLEncoder.encode("<p>"+message+"</p>", "UTF-8") ;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        legth = data.length();

        out.println("POST "+location+" "+HTTP+"\r");
        // System.err.println("POST "+location+" "+HTTP);
        out.println("Host: "+HOST+"\r");
        out.println("Connection: keep-alive"+"\r");
        out.println("Content-Type: application/x-www-form-urlencoded"+"\r");
        out.println("Cookie: "+cookie+"\r");
        // out.println("Origin: http://"+HOST);
        // out.println("Referer: http://"+HOST+"/");
        out.println("User-Agent: "+USER_AGENT+"\r");
        out.println("Accept: "+ACCEPT+"\r");
        out.println("Accept-Language: fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7"+"\r");
        out.println("Accept-Encoding: none"+"\r");
        out.println("Content-Length: "+legth+"\r");
        out.println("\r");
        out.println(data);
    }

    @Override
    public void setUserPass(String user, String pass, String email, String subject, String message) {
        this.user = user;
        this.pass= pass;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void close() {
        try {
            httpSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

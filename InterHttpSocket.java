package package01;

public interface InterHttpSocket {
    void requireInter();
    void initialSIO();
    void httpGET(String request);
    void httpPOST(String location);
    void readResponse();
    void postMessage(String location);
    void setUserPass(String user,String pass,String email, String subject, String message);
    String getLocation();
    void close();
}

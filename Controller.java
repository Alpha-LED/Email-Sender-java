package package01;

public class Controller extends Composant implements InterController{
    InterHttpSocket httpSocket;
    InterIHM ihm;

    public void go(){
        ihm = (InterIHM) ports.get("ihm");
        httpSocket = (InterHttpSocket) ports.get("httpSocket");

        ihm.displayMessage("Username : ");
        String user = ihm.getInPut();
        ihm.displayMessage("Password : ");
        String pass = ihm.getInPut();
        ihm.displayMessage("Email of recever : ");
        String email = ihm.getInPut();
        ihm.displayMessage("Subject");
        String subject = ihm.getInPut();
        ihm.displayMessage("Message : ");
        String message = ihm.getInPut();

        sendMail(user,pass,email,subject,message);
    }
    void sendMail(String user,String pass ,String email ,String subject ,String message ){
        httpSocket.requireInter();
        httpSocket.setUserPass(user,pass,email,subject,message);

        authantification();
        initialRequest();
        sendMessage();
    }

    void authantification(){

        httpSocket.initialSIO();
        httpSocket.httpGET("/");
        httpSocket.readResponse();

        httpSocket.initialSIO();
        httpSocket.httpPOST("/?_task=login");
        httpSocket.readResponse();
    }

    void initialRequest(){
        httpSocket.initialSIO();
        httpSocket.httpGET(httpSocket.getLocation());
        httpSocket.readResponse();
        httpSocket.initialSIO();
        httpSocket.httpGET("/?_task=mail&_mbox=INBOX&_action=compose");
        httpSocket.readResponse();
        httpSocket.initialSIO();
        httpSocket.httpGET(httpSocket.getLocation());
        httpSocket.readResponse();
    }

    void sendMessage(){

        httpSocket.initialSIO();
        httpSocket.postMessage("/?_task=mail&_unlock=loading"+(System.currentTimeMillis() + 96)+"&_lang=fr_FR&_framed=1");
        httpSocket.readResponse();
        httpSocket.initialSIO();
        //httpSocket.close();
    }
}

package package01;

public class MailContainor extends Composant {
    MailContainor(){
        Controller ctr = new Controller();
        HttpSocket httpSocket = new HttpSocket();
        IHM ihm = new IHM();
        HeaderBody headerBody = new HeaderBody();

        httpSocket.connect("headerBody", headerBody);

        ctr.connect("ihm",ihm);
        ctr.connect("httpSocket",httpSocket);

        ctr.go();
    }
}

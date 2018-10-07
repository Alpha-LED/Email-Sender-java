package package01;

import java.util.Scanner;

public class IHM extends Composant implements InterIHM {
    Scanner sc = new Scanner(System.in);
    @Override
    public void displayMessage(String str) {
        System.out.println(str);
    }

    @Override
    public String getInPut() {

        return sc.nextLine();
    }
}

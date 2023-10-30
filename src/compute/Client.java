package compute;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;

public class Client {
    public static void main(String args[]) throws NotBoundException, MalformedURLException, RemoteException {
        try {
            PrinterService service = (PrinterService) Naming.lookup("rmi://localhost:5099/print");
            String authToken = service.login("Cesare", "guancialeLover");
            System.out.println(authToken);

            boolean test = service.print("Hello World!", "HP-1", authToken);
            System.out.println(test);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
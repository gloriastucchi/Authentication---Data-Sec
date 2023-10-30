package compute;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;

public class Client {
    public static void main(String args[]) throws NotBoundException, MalformedURLException, RemoteException {
        try {
            PrinterService service = (PrinterService) Naming.lookup("rmi://localhost:5099/print");

            // "Gloria", "datasec"
            // "Leonardo", "bagigi47"
            // "Cesare", "guancialeLover"
            // "Riccardo", "Erpupone10"
            String authToken = service.login("Cesare", "guancialeLover");
            boolean test = service.print("Hello World!", "HP-1", authToken);
            String[] queue = service.queue("HP-1", authToken);
            for (String job : queue) {
                System.out.println(job);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
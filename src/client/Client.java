
// import java.rmi.registry.LocateRegistry;
// import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
// import java.math.BigDecimal;
import java.net.MalformedURLException;

public class Client {
    public static void main(String args[]) throws NotBoundException, MalformedURLException, RemoteException {
        PrinterService service = (PrinterService) Naming.lookup("rmi://localhost:5099/print");
        System.out.println(service.print("Hello World!", "igjgfyf", "HGGFTYD987"));
    }
}
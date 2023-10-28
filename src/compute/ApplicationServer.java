import java.rmi.RemoteException;
// import java.rmi.registry.LocateRegistry;
// import java.rmi.registry.Registry;

public class ApplicationServer {
 	private int port = 5099;

	public void main(String[] args) throws RemoteException {
		// Registry registry = LocateRegistry.createRegistry(this.port);
		// registry.rebind("print", new Printer());
		System.out.println("Server started on port " + this.port);
	}
}
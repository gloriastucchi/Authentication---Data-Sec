package compute;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

public class Printer {

	public static void main(String args[]) throws NotBoundException, MalformedURLException, RemoteException {
		try {
			ServerService service = (ServerService) Naming.lookup("rmi://localhost:5099/print");

			while (true) {
				Thread.sleep(2000);
				String result = service.removeFirstFromPrintingQueues();
				System.out.println(result);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

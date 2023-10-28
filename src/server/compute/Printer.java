package compute;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Printer extends UnicastRemoteObject implements PrinterService {

	private Authentication a;
	private TokenVerifier t;

	public Printer() throws RemoteException {
		super();
	}

	public String echo(String input) throws RemoteException {// va eliminato
		return "From server: " + input;
	}

	public String print(String filename, String printer, String authToken) throws RemoteException {
		try {
			// If there is no valid token, prompt for authentication
			if (authToken == null || !t.validateToken(authToken)) {
				authToken = a.authenticateUser();
			}

			// Validate the token
			if (validateToken(authToken)) {
				System.out.println("Print request received from authetnicated user ");
				System.out.println("Printing " + filename + " on printer " + printer);
				// Actual print logic here
			} else {
				// Token validation failed
				System.out.println("Token validation failed.");
				throw new RemoteException("Token validation failed.");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new RemoteException("Internal error during print operation.");
		}
	}
}
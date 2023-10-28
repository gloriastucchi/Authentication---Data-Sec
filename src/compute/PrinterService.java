

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrinterService extends Remote {
	public void print(String filename, String printer, String authToken);
}

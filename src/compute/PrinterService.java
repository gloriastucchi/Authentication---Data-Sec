package compute;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public interface PrinterService extends Remote {
   public boolean print(String filename, String printer, String authToken) throws RemoteException;

   public String[] queue(String printer, String authToken) throws RemoteException;

   public String login(String username, String password) throws RemoteException, NoSuchAlgorithmException;

   public boolean tokenNotValid(String authToken) throws RemoteException;

}

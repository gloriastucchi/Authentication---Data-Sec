package compute;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public interface ServerService extends Remote {
   public String print(String filename, String printer, String authToken) throws RemoteException;

   public String queue(String printer, String authToken) throws RemoteException;

   public String topQueue(String printer, int job, String authToken) throws RemoteException;

   public String start(String authToken) throws RemoteException;

   public String stop(String authToken) throws RemoteException;

   public String restart(String authToken) throws RemoteException;

   public String status(String printer, String authToken) throws RemoteException;

   public String readConfig(String parameter, String authToken) throws RemoteException;

   public String setConfig(String parameter, String value, String authToken) throws RemoteException;

   public String login(String username, String password) throws RemoteException, NoSuchAlgorithmException;

   public boolean tokenNotValid(String authToken) throws RemoteException;

   public String removeFirstFromPrintingQueues() throws RemoteException;

   public void log(String string) throws RemoteException;
}

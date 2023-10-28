package compute;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public interface AuthenticationService extends Remote {

    public String authenticate(String username, String password) throws RemoteException, NoSuchAlgorithmException;

    public String hasher(String stringToHash) throws NoSuchAlgorithmException;

    public String generateJwt(String username) throws NoSuchAlgorithmException;

}

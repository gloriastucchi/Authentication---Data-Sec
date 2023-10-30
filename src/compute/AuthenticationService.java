package compute;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public interface AuthenticationService extends Remote {

    public String authenticate(String username, String password) throws NoSuchAlgorithmException;

    public String hash(String stringToHash, String salt) throws NoSuchAlgorithmException;

    public String hash(String stringToHash) throws NoSuchAlgorithmException;

    public String generateJwt(String username) throws NoSuchAlgorithmException;

    public void storeJwt(String jwt) throws NoSuchAlgorithmException;

}

package compute;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public interface TokenVerifierService extends Remote {
    public boolean validate(String authToken);
}
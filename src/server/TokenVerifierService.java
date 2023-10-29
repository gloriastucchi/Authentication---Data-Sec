
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public interface TokenVerifierService extends Remote {
    public boolean validateToken(String authToken);

    public boolean storeToken(String authToken);
}
package compute;

import java.rmi.Remote;

public interface TokenVerifierService extends Remote {
    public boolean validate(String authToken);
}
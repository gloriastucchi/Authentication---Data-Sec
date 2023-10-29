
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class TokenVerifier implements TokenVerifierService {

    static private final Map<String, Integer> map = new HashMap<String, Integer>();

    static private final TokenVerifier tok = new TokenVerifier();

    public boolean validateToken(String authToken) {
        throw new UnsupportedOperationException("Unimplemented method 'validateToken'");
    }

    public boolean storeToken(String authToken) {
        int offset = 10; // offset di 1 ora in secondi
        int timestamp = (int) (System.currentTimeMillis() / 1000L) + offset;

        TokenVerifier.map.put(authToken, timestamp);

        System.out.println(map);

        return true;
    }

}

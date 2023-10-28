package compute;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class TokenVerifier implements TokenVerifierService {

    static private final Map<String, String[]> map = new HashMap<String, String[]>();

    public boolean validateToken(String authToken) {
        throw new UnsupportedOperationException("Unimplemented method 'validateToken'");
    }

    public boolean storeToken(String authToken, String username) {
        String[] tokenArray = new String[2];
        tokenArray[0] = "token";
        int offset = 10; // offset di 1 ora in secondi
        int timestamp = (int) (System.currentTimeMillis() / 1000L) + offset;
        tokenArray[1] = Integer.toString(timestamp);
        TokenVerifier.map.put(username, tokenArray);
        return true;
    }

}

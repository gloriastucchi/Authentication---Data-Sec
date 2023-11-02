package compute;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TokenVerifier implements TokenVerifierService {
    static private final String tokensFile = "./src/data/tokens.txt";

    public boolean validate(String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(tokensFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String token = parts[0];
                if (token.equals(authToken)) {
                    String expiration = parts[1];

                    long currentTime = System.currentTimeMillis();
                    long expirationTime = Long.parseLong(expiration);
                    if (currentTime > expirationTime) {
                        // maybe delete token from file
                        return false;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}

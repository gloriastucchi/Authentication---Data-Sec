package compute;

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

                    return currentTime < expirationTime;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public String getUsername(String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(tokensFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String token = parts[0];
                if (token.equals(authToken)) {
                    String username = parts[2];
                    return username;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }
}

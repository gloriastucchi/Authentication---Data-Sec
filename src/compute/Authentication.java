package compute;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Authentication implements AuthenticationService {
    static private final String databaseFile = "./src/compute/data/database.txt";
    static private final String tokensFile = "./src/compute/data/tokens.txt";
    static private final Integer tokenExpirationTime = 10800000; // 3h in milliseconds
    public Map<String, String> tokenMap = new HashMap<>();
    private Map<String, String> userMap = new HashMap<>();

    public Authentication() {
        try {
            loadDatabase();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String authenticate(String username, String password) throws NoSuchAlgorithmException {
        String storedPassword = userMap.get(username);
        if (storedPassword == null) {
            return null;
        }

        String[] parts = storedPassword.split(" ");
        String storedHashedPassword = parts[0];
        String storedSalt = parts[1];

        String hashedPassword = hash(password, storedSalt);

        if (storedHashedPassword.equals(hashedPassword)) {
            String newToken = generateJwt(username);
            storeJwt(newToken);
            return newToken;
        }

        return null;
    }

    private void loadDatabase() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(databaseFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            String username = parts[0];
            String userEntry = parts[1] + " " + parts[2];
            userMap.put(username, userEntry);
        }
        reader.close();
    }

    public String hash(String stringToHash, String salt) throws NoSuchAlgorithmException { // hash with salt, for the
                                                                                           // password
        String data = stringToHash + salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(data.getBytes());
        return Base64.getEncoder().encodeToString(hash); // encoded hash
    }

    public String hash(String stringToHash) throws NoSuchAlgorithmException { // hash implementation with random salt,
                                                                              // for the JWT
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);
        String data = stringToHash + salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(data.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    public String generateJwt(String username) throws NoSuchAlgorithmException {
        Date timestamp = new Date(System.currentTimeMillis());
        return hash(username + timestamp);
    }

    public void storeJwt(String jwt) throws NoSuchAlgorithmException {
        long timestamp = System.currentTimeMillis();
        long expiration = (new Date(timestamp + tokenExpirationTime)).getTime(); // Set expiration to 3 hours from now
        String expirationString = Long.toString(expiration);
        String tokenData = jwt + " " + expirationString;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(tokensFile, true));
            writer.write(tokenData);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTokens() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(tokensFile));
        writer.write("");
        writer.close();
    }
}

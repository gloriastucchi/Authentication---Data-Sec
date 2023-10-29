
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Authentication implements AuthenticationService {
    static private final TokenVerifier tok = new TokenVerifier();
    static private final String databaseFile = "./src/server/database.txt";

    public String authenticate(String username, String password) throws RemoteException, NoSuchAlgorithmException {
        String storedUsername = null;
        String storedPassword = null;

        try (BufferedReader br = new BufferedReader(new FileReader(databaseFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                storedUsername = parts[0];
                storedPassword = parts[1];

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    String newToken = generateJwt(username);
                    if (tok.storeToken(newToken)) // va sistemato lo storing del token nella mappa
                        return newToken;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (storedPassword == null) {
            throw new RemoteException("User not found");
        }
        return null;
    }

    public String hasher(String stringToHash) throws NoSuchAlgorithmException {// hash di jwt o password a seconda delle
                                                                               // necessità
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);
        String data = stringToHash + salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(data.getBytes());
        String encodedHash = Base64.getEncoder().encodeToString(hash);
        return encodedHash;
    }

    public String generateJwt(String username) throws NoSuchAlgorithmException {
        long timestamp = new Date().getTime();
        String data = username + timestamp;
        String encodedJwt = hasher(data);

        return encodedJwt;
    }
}

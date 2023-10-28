import java.util.Map;
import java.security.KeyPairGenerator;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
//import HashUtil;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthenticationLogic {
    private Map<String, User> users;
    private KeyPairGenerator keyPairGenerator;
    private Map<String, String> tokenMap;
    private static final String USERS_DIRECTORY = "users";
    private static final String SECRET_KEY = "mySecretKey"; // Chiave segreta per la firma del token

    public AuthenticationLogic() {
        // Initialization logic (keyPairGenerator, etc.)
    }

    public String authenticate(String username, String password) throws RemoteException, NoSuchAlgorithmException {
        // Implement user authentication logic
        // Return authentication token on success, null on failure
        if (users.containsKey(username) && hasher(users.get(username).toString()).equals(password)) {
            // Generate authentication token
            String authToken = generateJwt(username);

            // Update user with the new token
            long timestamp = new Date().getTime();

            // Store the token in the token map
            tokenMap.put(authToken, String.valueOf(timestamp));

            return authToken;
        }
        return null;
    }

    public boolean validateToken(String authToken) {
        // Implement token validation logic
        // Check if the token is valid and not expired
        return false;
    }

    public static String hasher(String stringToHash) throws NoSuchAlgorithmException {
        // Implement string hashing logic
        // Return the hashed string
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(stringToHash.getBytes(StandardCharsets.UTF_8));
        String hashString = Base64.getEncoder().encodeToString(hash);
        return hashString;
    }

    private static String generateJwt(String username) throws NoSuchAlgorithmException {
        // Genera una chiave segreta casuale per la firma del token
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(secureRandom);
        Key key = keyGenerator.generateKey();

        // Converte la chiave segreta in una stringa Base64
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());// ??????????????

        // Calcola l'hash del nome utente e del timestamp corrente
        long timestamp = new Date().getTime();
        String data = username + timestamp;

        String hashString = hasher(data);

        // Crea il token JWT
        String jwt = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Scadenza dopo 1 ora
                .claim("hash", hashString)
                .signWith(new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                .compact();

        return jwt;
    }

    // Additional methods for password hashing, token generation, user storage, etc.
}

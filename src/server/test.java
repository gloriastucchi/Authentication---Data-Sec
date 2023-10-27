import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class test {
    public static void main(String[] args) throws NoSuchAlgorithmException{
        for (int i = 0; i < 100; i++) {
           
        
        String data = "Hello, world!";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        String hashString = Base64.getEncoder().encodeToString(hash);

        String data2 = "Hello, world!";
        MessageDigest digest2 = MessageDigest.getInstance("SHA-256");
        byte[] hash2 = digest2.digest(data2.getBytes(StandardCharsets.UTF_8));
        String hashString2 = Base64.getEncoder().encodeToString(hash2);
        if (!hashString.equals(hashString2))
        System.out.println("Hashes are different");}
        System.out.println("Hashes are equal");
    }
}
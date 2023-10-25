import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password; // in plain-text, we are assuming safe connection bertween client and server
    // we anre not using privateand public keys for the cummincation, because we are
    // assuming that the connection is safe by default
    private String authToken;

    public User(String username, String password, String publicKey, String privateKey) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}

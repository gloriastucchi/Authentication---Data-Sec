

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.io.Serializable;

public class UserClient implements Serializable {
    private String username;
    private String password; // in plain-text, we are assuming safe connection bertween client and server
    // we anre not using privateand public keys for the cummincation, because we are
    // assuming that the connection is safe by default
    private String authToken;
    
    public static void main(String args[]) throws NotBoundException, MalformedURLException, RemoteException {
        PrinterService service = (PrinterService) Naming.lookup("rmi://localhost:5099/print");
        System.out.println(service.print("Hello World!", "printer string", "123o98yqoiwdbf98q3y4gtr"));
    }

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

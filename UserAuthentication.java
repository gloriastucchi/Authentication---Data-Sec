import java.rmi.RemoteException;

public class UserAuthentication {
    private AuthenticationLogic authenticationLogic;

    public UserAuthentication(AuthenticationLogic authenticationLogic) {
        this.authenticationLogic = authenticationLogic;
    }

    public String authenticateUser(String username, String password) {
        try {
            // Attempt authentication
            String authToken = authenticationLogic.authenticate(username, password);

            if (authToken != null) {
                System.out.println("Authentication successful. Token generated.");
                return authToken;
            } else {
                System.out.println("Authentication failed. Please try again.");
                return authenticateUser(); // Retry authentication
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Internal error during authentication. Please try again.");
            return authenticateUser(); // Retry authentication
        }
    }
}

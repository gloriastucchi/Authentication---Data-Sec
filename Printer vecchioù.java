import java.rmi.RemoteException;

public class Printer {
    private AuthenticationLogic authenticationLogic;
    private UserAuthentication userAuthentication;
    private TokenVerifier tokenVerifier;

    public Printer(AuthenticationLogic authenticationLogic) {
        this.authenticationLogic = authenticationLogic;
        this.userAuthentication = new UserAuthentication(authenticationLogic);
        this.tokenVerifier = new TokenVerifier(authenticationLogic);
    }

    public void print(String filename, String printer, String authToken) throws RemoteException {
        try {
            // If there is no valid token, prompt for authentication
            if (authToken == null || !tokenVerifier.validateToken(authToken)) {
                authToken = userAuthentication.authenticateUser();
            }

            // Validate the token
            if (tokenVerifier.validateToken(authToken)) {
                System.out.println("Print request received from authetnicated user ");
                System.out.println("Printing " + filename + " on printer " + printer);
                // Actual print logic here
            } else {
                // Token validation failed
                System.out.println("Token validation failed.");
                throw new RemoteException("Token validation failed.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RemoteException("Internal error during print operation.");
        }
    }
}

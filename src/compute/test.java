package compute;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;

public class test {

    public static void main(String[] args) throws RemoteException, NoSuchAlgorithmException {
        Authentication auth = new Authentication();

        auth.authenticate("Gloria", "datasec");
        auth.authenticate("Leonardo", "bagigi47");
        auth.authenticate("Cesare", "guancialeLover");
        String test = auth.authenticate("Riccardo", "Erpupone10");

        System.out.println(auth.tokenMap.toString());
    }
}

// no changes needed

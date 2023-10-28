package compute;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class test {

    public static void main(String[] args) throws RemoteException, NoSuchAlgorithmException {
        Authentication a = new Authentication();
        System.out.println(a.authenticate("Gloria", "datasec"));
        System.out.println(a.authenticate("Leonardo", "bagigi47"));
        a.generateJwt("Aaaaaa");
    }
}

// no changes needed

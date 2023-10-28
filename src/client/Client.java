package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.math.BigDecimal;
import compute.Compute;

public class Client {
    public static void main(String args[]) throws NotBoundException {
        HelloService service = (HelloService) Naming.lookup("rmi://localhost:5000/hello");
        System.println(service.echo("Hello World!")); 
    }    
}
package compute;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

public class Client {
    public static void main(String args[]) throws NotBoundException, MalformedURLException, RemoteException {

        final String authToken;

        try {
            ServerService service = (ServerService) Naming.lookup("rmi://localhost:5099/print");

            // "Gloria", "datasec"
            // "Leonardo", "bagigi47"
            // "Cesare", "guancialeLover"
            // "Riccardo", "Erpupone10"
            authToken = service.login("Cesare", "guancialeLover");
            // authToken = null;
            execute(service, authToken, "start");
            // service.start(authToken);
            // execute(service, authToken, "print", "Hello World!", "HP-1");
            // service.print("Hello World!", "HP-1", authToken);
            service.print("ciao", "HP-1", authToken);
            service.print("come va", "HP-1", authToken);
            service.print("non me lo stampa", "HP-2", authToken);
            // execute(service, "HP-1", authToken, "printQueue");
            printQueue(service, "HP-1", authToken);
            // Thread.sleep(4000);
            // printQueue(service, "HP-1", authToken);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String execute(ServerService service, String authToken, String methodName, Object... args)
            throws RemoteException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            SecurityException {

        String result = null;
        if (args.length > 0) {
            result = (String) service.getClass().getMethod(methodName, String.class, Object[].class).invoke(
                    service,
                    authToken,
                    args);
        } else {
            result = (String) service.getClass().getMethod(methodName, String.class).invoke(service, authToken);
        }

        if (result != null && result.equals("TOKEN_NOT_VALID")) {
            System.out.println("Token not valid");
            return null;
        }
        return result;
    }

    private static void printQueue(ServerService service, String printer, String authToken) throws RemoteException {
        // verifica messaggi di errore

        String queue = service.queue(printer, authToken);
        if (queue != null && queue.equals("TOKEN_NOT_VALID")) {
            System.out.println("Token not valid");
        } else
            System.out.println(queue);
    }
}
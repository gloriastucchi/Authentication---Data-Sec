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
            ServerService s = (ServerService) Naming.lookup("rmi://localhost:5099/print");

            // "Gloria", "datasec"
            // "Leonardo", "bagigi47"
            // "Cesare", "guancialeLover"
            // "Riccardo", "Erpupone10"

            authToken = s.login("Cesare", "guancialeLover");
            execute(s, "start", authToken);

            execute(s, "print", "file 1", "HP-1", authToken);
            execute(s, "print", "file 2", "HP-1", authToken);
            execute(s, "print", "file 3", "HP-1", authToken);
            execute(s, "print", "file 1", "HP-2", authToken);
            execute(s, "restart", authToken);

            execute(s, "setConfig", "format", "A0", authToken);
            printStatus(s, "HP-1", authToken);
            printConfig(s, "format", authToken);
            // execute(s, "stop", authToken);

            printQueue(s, "HP-1", authToken);
            // Thread.sleep(4000);
            printQueue(s, "HP-1", authToken);

            // printQueue(s, "HP-1", authToken);

            execute(s, "restart", authToken);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String execute(ServerService service, String methodName, Object... args)
            throws RemoteException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        Class<?>[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        String result = (String) service.getClass().getMethod(methodName,
                argTypes).invoke(service, args);

        if (result != null) {
            if (result.equals("TOKEN_NOT_VALID")) {
                System.out.println("Authentication on valid or expired. Please login again.");
                return null;
            }
            if (result.equals("SERVER_IS_OFF")) {
                System.out.println("The server is not running. Start it and try again.");
                return null;
            }
        }

        return result;
    }

    private static void printQueue(ServerService service, String printer, String authToken) throws RemoteException {
        try {
            String queue = execute(service, "queue", printer, authToken);
            System.out.println(queue);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void printStatus(ServerService service, String printer, String authToken) throws RemoteException {
        try {
            String status = execute(service, "status", printer, authToken);
            System.out.println(status);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void printConfig(ServerService service, String parameter, String authToken)
            throws RemoteException {
        try {
            String config = execute(service, "readConfig", parameter, authToken);
            System.out.println(config);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

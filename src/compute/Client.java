package compute;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

public class Client {
    public static void main(String args[]) throws NotBoundException, MalformedURLException, RemoteException {

        String authToken = "";

        try {
            ServerService s = (ServerService) Naming.lookup("rmi://localhost:5099/print");

            // Passwords:
            // "Gloria", "datasec"
            // "Leonardo", "bagigi47"
            // "Cesare", "guancialeLover"
            // "Riccardo", "Erpupone10"
            // for every other user the password is the same as the username

            execute(s, "print", "funny-meme.gif", "HP", authToken);

            // execute(s, "start", authToken);

            authToken = loginWithResponse(s, "Cesare", "guanciale");
            authToken = loginWithResponse(s, "Cesare", "guancialeLover");

            execute(s, "start", authToken);

            execute(s, "print", "funny-meme.gif", "HP", authToken);

            execute(s, "print", "funny-meme.gif", "HP", authToken);
            execute(s, "print", "kitty.png", "HP", authToken);
            execute(s, "print", "project review.pdf", "HP", authToken);
            execute(s, "print", "project review.pdf", "Canon", authToken);
            execute(s, "print", "poster.jpeg", "Canon", authToken);

            execute(s, "topQueue", "HP", "2", authToken);

            printConfig(s, "format", authToken);

            execute(s, "setConfig", "format", "A0", authToken);
            printStatus(s, "HP", authToken);
            printConfig(s, "format", authToken);

            printQueue(s, "HP", authToken);
            printQueue(s, "Canon", authToken);

            Thread.sleep(4000); // wait some time for the printer to print (Printer.java needs to be running)

            printQueue(s, "HP", authToken);

            execute(s, "restart", authToken);

            printQueue(s, "HP", authToken);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String execute(ServerService service, String methodName, Object... args)
            throws RemoteException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            SecurityException {
        Class<?>[] argTypes = new Class[args.length];
        String argsString = "";
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
            if (i < args.length - 1)
                argsString += args[i].toString() + ", ";
        }
        String result = (String) service.getClass().getMethod(methodName,
                argTypes).invoke(service, args);

        System.out.println("\n - " + methodName + ": " + argsString);

        if (result != null) {
            if (result.equals("TOKEN_NOT_VALID")) {
                System.out.println("Authentication not valid or expired. Please login again.");
                return null;
            }
            if (result.equals("SERVER_IS_OFF")) {
                System.out.println("The server is not running. Start it and try again.");
                return null;
            }
        }

        return result;
    }

    private static String loginWithResponse(ServerService service, String username, String password)
            throws RemoteException {
        try {
            String token = service.login(username, password);
            if (token != null) {
                System.out.println("\nlogged in as \"" + username + "\"");
            }
            return token;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
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

package compute;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Printer extends UnicastRemoteObject implements PrinterService {

	private Authentication auth;
	private TokenVerifier token;
	private Map<String, List<String>> printQueue;
	private Map<String, String> printerStatus;
	private Map<String, String> printServerConfig;

	public Printer() throws RemoteException {
		super();
		auth = new Authentication();
		token = new TokenVerifier();
		printQueue = new HashMap<>();
		printerStatus = new HashMap<>();
		printServerConfig = new HashMap<>();
	}

	public boolean print(String filename, String printer, String authToken) throws RemoteException {
		if (tokenNotValid(authToken)) {
			return false;
		}

		List<String> queue = printQueue.get(printer);
		if (queue == null) {
			queue = new ArrayList<>();
			printQueue.put(printer, queue);
		}
		queue.add(filename);

		System.out.println("Print request received from authenticated user ");
		System.out.println("Printing on " + printer + ": \"" + filename + "\"");

		// Delay for 2 seconds
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		queue.remove(filename);

		return true;
	}

	public String[] queue(String printer, String authToken) throws RemoteException {
		List<String> queue = printQueue.get(printer);
		String[] queueResult = new String[queue.size()];

		if (queue == null || queue.isEmpty()) {
			return new String[] { "Print queue for printer " + printer + " is empty." };
		} else {
			for (int i = 0; i < queue.size(); i++) {
				queueResult[i] = (i + 1) + ". " + queue.get(i);
			}
			return queueResult;
		}
	}

	public String topQueue(String printer, int job) throws RemoteException {
		List<String> queue = printQueue.get(printer);
		if (queue == null || queue.isEmpty()) {
			return "Print queue for printer " + printer + " is empty.";
		} else if (job < 1 || job > queue.size()) {
			return "Invalid job number.";
		} else {
			String jobToMove = queue.remove(job - 1);
			queue.add(0, jobToMove);
			return "Moved job " + job + " to the top of the queue for printer " + printer + ".";
		}
	}

	public void start() throws RemoteException {
		System.out.println("Print server started.");
	}

	public void stop() throws RemoteException {
		System.out.println("Print server stopped.");
	}

	public void restart() throws RemoteException {
		printQueue.clear();
		System.out.println("Print queue cleared.");
		start();
	}

	public void status(String printer) throws RemoteException {
		String status = printerStatus.get(printer);
		if (status == null) {
			System.out.println("Printer " + printer + " is not available.");
		}
		System.out.println("Printer " + printer + " is " + status + ".");
	}

	public String readConfig(String parameter) throws RemoteException {
		String value = printServerConfig.get(parameter);
		if (value == null) {
			return "Parameter " + parameter + " is not set.";
		}
		return parameter + ": " + value;
	}

	public void setConfig(String parameter, String value) throws RemoteException {
		printServerConfig.put(parameter, value);
		System.out.println("Parameter " + parameter + " set to " + value + ".");
	}

	public String login(String username, String password) throws RemoteException, NoSuchAlgorithmException {
		String authToken = auth.authenticate(username, password);

		if (authToken == null) {
			System.out.println("Login failed: credentials not valid.");
			return null;
		}

		System.out.println("Login successful. Token generated and stored.");
		return authToken;
	}

	public boolean tokenNotValid(String authToken) throws RemoteException {
		if (authToken == null || !token.validate(authToken)) {
			System.out.println("Token validation failed: token not valid or expired.");
			return true;
		}
		return false;
	}
}

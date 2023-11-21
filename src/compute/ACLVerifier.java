package compute;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ACLVerifier {
    static private final String aclFile = "src/data/acl.csv";


    public static boolean canExecuteFunction(String username, String functionName) {
        try (BufferedReader br = new BufferedReader(new FileReader(aclFile))) {
            String line;
            String[] functions = null;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Initialize the column headers (functions) from the first row
                if (functions == null) {
                    functions = values;
                    continue;
                }

                // Check if the current line is for the specified user
                if (values[0].equals(username)) {
                    for (int i = 1; i < functions.length; i++) {
                        if (functions[i].equals(functionName) && values[i].equals("1")) {
                            return true; // User has permission for the function
                        }
                    }
                    break; // User found but does not have permission
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // User not found or does not have permission
    }

    public static void main(String[] args) {
        boolean hasPermission = canExecuteFunction("riccardo", "print");
        System.out.println("User has permission: " + hasPermission);
    }
}

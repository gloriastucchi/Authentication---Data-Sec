package compute;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ACLVerifier {
    static private final String userAccessPolicyFile = "src/data/user-access-policy.csv";
    static private final String RoleAccessPolicyFile = "src/data/role-access-policy.csv";

    public static Map<String, Boolean> getPermissionMap(String username, String role) {
        Map<String, Boolean> permissions = new HashMap<>();

        String accessPolicyFile = role == null ? userAccessPolicyFile : RoleAccessPolicyFile;
        try (BufferedReader br = new BufferedReader(new FileReader(accessPolicyFile))) {
            String line;
            String[] functions = null;
            boolean found = false; // either user or role
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Initialize the column headers (functions) from the first row
                if (functions == null) {
                    functions = values;
                    continue;
                }

                // Check if the current line is for the specified user or role
                String entry = role == null ? username : role;
                if (values[0].equals(entry)) {
                    found = true;
                    for (int i = 1; i < functions.length; i++) {
                        if (values[i].equals("1")) {
                            permissions.put(functions[i], true); // User has permission for the function
                        } else {
                            permissions.put(functions[i], false);
                        }
                    }
                    break;

                }
            }

            if (!found) {
                String msg = role == null ? "User not found in the CSV file for retrieving acl list."
                        : "Role not found in the CSV file for retrieving acl list.";
                System.out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return permissions;
    }
}

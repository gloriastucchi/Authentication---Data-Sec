package compute;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.io.FileWriter;
import java.io.IOException;

public class CreateUser {

	static private final String databaseFile = "./src/data/database_changed.txt";

	public static void main(String[] args) {
		addUser("Henry", "Henry", "User");
		addUser("Ida", "Ida", "PowerUser");
	}

	public static void addUser(String username, String password, String role) {
		try {
			Authentication auth = new Authentication();

			String salt = generateSalt();
			String hashedPassword = auth.hash(password, salt);

			// Create the new row to be added to the database
			String newRow = username + " " + role + " " + hashedPassword + " " + salt;

			// Append the new row to the database.txt file
			try {
				FileWriter writer = new FileWriter(databaseFile, true);
				writer.write(newRow + "\n");
				writer.close();
				System.out.println("User added successfully.");
			} catch (IOException e) {
				System.out.println("An error occurred while adding the user.");
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println("An error occurred while adding the user.");
			e.printStackTrace();
		}
	}

	private static String generateSalt() {
		int length = 11; // default length
		String saltChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*";
		SecureRandom random = new SecureRandom();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(saltChars.length());
			sb.append(saltChars.charAt(index));
		}
		return sb.toString();
	}
}

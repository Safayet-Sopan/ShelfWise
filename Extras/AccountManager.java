package Extras;

import java.io.*;

public class AccountManager {
    private static final String ACCOUNT_FILE = "./assets/accountData.txt";

    // Sign up — returns true if successful, false if username exists
    public static boolean signUp(String username, String password) {
        if (isAccountExists(username)) {
            return false;
        }

        try (FileWriter fw = new FileWriter(ACCOUNT_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(username + "," + password);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sign in — returns true if username/password pair matches
    public static boolean login(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
        return false;
    }

    // Check if username exists
    public static boolean isAccountExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // Ignore — treat as no accounts exist yet
        }
        return false;
    }
}
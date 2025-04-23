package utils;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        return password.matches(passwordRegex);
    }

    public static List<String> getPasswordErrors(String password) {
        List<String> err = new ArrayList<>();

        if (password.length() < 8) {
            err.add("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*[a-z].*")) {
            err.add("Password must include at least one lowercase letter.");
        }
        if (!password.matches(".*[A-Z].*")) {
            err.add("Password must include at least one uppercase letter.");
        }
        if (!password.matches(".*\\d.*")) {
            err.add("Password must include at least one digit.");
        }

        return err;
    }

    public static boolean isValidPhoneNumber(String phone) {
        String phoneRegex = "^[0-9]{10}$";
        return phone.matches(phoneRegex);
    }

    public static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
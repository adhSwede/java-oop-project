package utils;

import java.util.Scanner;
import java.util.function.Predicate;

public class InputUtils {

    public static String promptUntilValid(Scanner sc, String prompt, Predicate<String> validator, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if (validator.test(input)) {
                return input;
            } else {
                System.out.println(errorMessage);
            }
        }
    }

    public static int promptInt(Scanner sc, String prompt) {
        String input = promptUntilValid(sc, prompt, ValidationUtils::isValidInteger, "Invalid number.");
        return Integer.parseInt(input);
    }

    public static double promptDouble(Scanner sc, String prompt) {
        String input = promptUntilValid(sc, prompt, ValidationUtils::isValidDouble, "Invalid number.");
        return Double.parseDouble(input);
    }
}
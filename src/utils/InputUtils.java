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

    public static int promptPositiveInt(Scanner sc, String prompt) {
        String input = promptUntilValid(
                sc,
                prompt,
                i -> ValidationUtils.isValidInteger(i) && Integer.parseInt(i) > 0,
                "Invalid number. Must be a positive whole number."
        );
        return Integer.parseInt(input);
    }


    public static double promptPositiveDouble(Scanner sc, String prompt) {
        return Double.parseDouble(promptUntilValid(
                sc, prompt,
                input -> ValidationUtils.isValidDouble(input) && Double.parseDouble(input) > 0,
                "Invalid number. Must be a positive value."
        ));
    }
}
package utils;

import static constants.Colors.*;
import java.util.List;

public class ConsoleHelper {

    public static <T> void printList(List<T> items) {
        if (items.isEmpty()) {
            System.out.println(YELLOW + "No matching items found." + RESET);
        } else {
            for (T item : items) {
                System.out.println(item);
            }
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printHeader(String title) {
        clearConsole();
        String bar = "=".repeat(title.length() + 8);
        System.out.println("\n" + CYAN + bar);
        System.out.println("==  " + BOLD + title + RESET + CYAN + "  ==");
        System.out.println(bar + RESET + "\n");
    }

    public static void printDivider() {
        System.out.println(CYAN + "-".repeat(40) + RESET);
    }

    public static void printOption(String key, String description) {
        System.out.printf(" [%s%s%s] %s%n", BOLD, key.toUpperCase(), RESET, description);
    }

    public static void printSuccess(String msg) {
        System.out.println(GREEN + "✅ " + msg + RESET);
    }

    public static void printError(String msg) {
        System.out.println(RED + "❌ " + msg + RESET);
    }

    public static void printWarning(String msg) {
        System.out.println(YELLOW + "⚠️  " + msg + RESET);
    }

    public static void prompt(String message) {
        System.out.print(BLUE + "👉 " + message + RESET);
    }

    public static void printTitle(String message) {
        System.out.println(BOLD + UNDERLINE + message + RESET);
    }
}

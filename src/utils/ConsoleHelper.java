package utils;
import java.util.List;

public class ConsoleHelper {
    public static <T> void printList(List<T> items) {
        if (items.isEmpty()) {
            System.out.println("No matching items found.");
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
}
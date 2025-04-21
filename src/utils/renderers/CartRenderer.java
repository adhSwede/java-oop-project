package renderers;

import entities.Product;
import utils.ConsoleHelper;
import static constants.Colors.*;

public class CartRenderer {

    public static void printCartItem(Product p, int quantity) {
        double subtotal = p.getPrice() * quantity;
        System.out.println(BOLD + "🧺 " + p.getName() + " x" + quantity + RESET);
        System.out.println("     💰 $" + p.getPrice() + " each → $" + subtotal);
        ConsoleHelper.printDivider();
    }

    public static void printCartTotal(double total) {
        System.out.println(BOLD + "💵 Total: $" + String.format("%.2f", total) + RESET);
        ConsoleHelper.printDivider();
    }

    public static void printCartHeader(String cartName, String ownerName) {
        System.out.println(BOLD + "🛒 Cart: " + cartName + RESET);
        System.out.println("👤 Owner: " + ownerName);
        ConsoleHelper.printDivider();
    }
}

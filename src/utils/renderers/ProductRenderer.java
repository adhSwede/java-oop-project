package renderers;

import entities.Product;
import utils.ConsoleHelper;

import static constants.Colors.BOLD;
import static constants.Colors.RESET;

public class ProductRenderer {

    public static void printProduct(Product p,
                                    String manufacturerName,
                                    double averageRating) {
        System.out.println(BOLD + "📦 Product ID: " + p.getProductId() + RESET);
        System.out.println("Name: " + p.getName());
        System.out.println("Manufacturer: " + manufacturerName);
        System.out.println("Price: $" + p.getPrice());
        System.out.println("In Stock: " + p.getStockQuantity());
        System.out.println("Rating: " + ConsoleHelper.renderStars((int) Math.round(averageRating)));
        ConsoleHelper.printDivider();
    }
}

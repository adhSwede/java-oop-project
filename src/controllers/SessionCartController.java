package controllers;

import factories.ServiceFactory;
import services.ProductService;
import services.SessionCartService;
import utils.ConsoleHelper;
import utils.InputUtils;
import entities.Product;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class SessionCartController {
    private final SessionCartService cartService = ServiceFactory.getSessionCartService();
    private final ProductService productService = ServiceFactory.getProductService();

    public void runMenu() {
        Scanner sc = new Scanner(System.in);
        String select;

        do {
            ConsoleHelper.clearConsole();
            System.out.println("\n--- Your Cart ---");
            System.out.println("1. View cart");
            System.out.println("2. Add product");
            System.out.println("3. Remove product");
            System.out.println("4. Update quantity");
            System.out.println("5. Clear cart");
            System.out.println("B. Back to main menu");
            System.out.print("Select an option: ");
            select = sc.nextLine().trim().toLowerCase();

            switch (select) {
                case "1" -> viewCart();
                case "2" -> addProduct();
                case "3" -> removeProduct();
                case "4" -> updateQuantity();
                case "5" -> clearCart();
                case "b" -> System.out.println("Returning...");
                default -> System.out.println("Invalid input. Try again.");
            }

        } while (!select.equals("b"));
    }

    private void viewCart() {
        Map<Product, Integer> items = cartService.getProductList();

        if (items.isEmpty()) {
            ConsoleHelper.printWarning("🛒 Your cart is empty.");
            return;
        }

        ConsoleHelper.printHeader("🛒 Your Cart");

        double total = 0;

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double subtotal = product.getPrice() * quantity;
            total += subtotal;

            System.out.printf("• %s x%d — %.2f kr%n", product.getName(), quantity, subtotal);
            ConsoleHelper.printDivider();
        }

        System.out.printf("💰 Total: %.2f kr%n", total);
    }

    private void addProduct() {
        Scanner sc = new Scanner(System.in);
        int productId = InputUtils.promptPositiveInt(sc, "Enter product ID: ");
        int quantity = InputUtils.promptPositiveInt(sc, "Enter quantity: ");

        cartService.addProductToCart(productId, quantity);
        System.out.println("✅ Product added to cart.");
    }

    private void removeProduct() {
        Scanner sc = new Scanner(System.in);
        int productId = InputUtils.promptPositiveInt(sc, "Enter product ID to remove: ");

        cartService.removeProductFromCart(productId);
        System.out.println("✅ Product removed from cart.");
    }

    private void updateQuantity() {
        Scanner sc = new Scanner(System.in);
        int productId = InputUtils.promptPositiveInt(sc, "Enter product ID: ");
        int quantity = InputUtils.promptPositiveInt(sc, "Enter new quantity: ");

        cartService.updateProductQuantity(productId, quantity);
        System.out.println("✅ Quantity updated.");
    }

    private void clearCart() {
        cartService.clearCart();
        System.out.println("🧹 Cart cleared.");
    }
}

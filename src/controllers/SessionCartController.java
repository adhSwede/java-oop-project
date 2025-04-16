package controllers;

import services.ProductService;
import services.SessionCartService;
import utils.ConsoleHelper;
import utils.InputUtils;
import entities.Product;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class SessionCartController {
    private final SessionCartService cartService = new SessionCartService();
    private final ProductService productService = new ProductService();

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
        Map<Integer, Integer> items = cartService.getProductList();
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("Your cart:");
        items.forEach((productId, qty) -> {
            try {
                Product p = productService.getProductById(productId);
                System.out.printf("%s x%d = %.2f%n", p.getName(), qty, p.getPrice() * qty);
            } catch (SQLException e) {
                System.out.println("Failed to retrieve product: ID " + productId);
            }
        });

        System.out.printf("Total: %.2f%n", cartService.calculateTotalPrice());
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

package controllers;

import contexts.SessionContext;
import entities.Product;
import entities.carts.StoredCart;
import exceptions.CartException;
import factories.ServiceFactory;
import services.ProductService;
import services.StoredCartService;
import utils.ConsoleHelper;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StoredCartController {
    private final StoredCartService storedCartService = ServiceFactory.getStoredCartService();
    private final ProductService productService = ServiceFactory.getProductService();
    private final Scanner sc = new Scanner(System.in); // Reuse

    public void runMenu() {
        boolean back = false;

        while (!back) {
            ConsoleHelper.printHeader("Your Saved Carts");
            ConsoleHelper.printOption("1", "View all carts");
            ConsoleHelper.printOption("2", "Switch active cart");
            ConsoleHelper.printOption("3", "Create new cart");
            ConsoleHelper.printOption("4", "Rename cart");
            ConsoleHelper.printOption("5", "Add item to active cart");
            ConsoleHelper.printOption("6", "View active cart contents");
            ConsoleHelper.printOption("7", "Convert cart to order");
            ConsoleHelper.printOption("B", "Back to previous menu");
            ConsoleHelper.prompt("Choose an option: ");

            String choice = sc.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1" -> viewAllCarts();
                case "2" -> setActiveCart();
                case "3" -> createNewCart();
                case "4" -> renameCart();
                case "5" -> addItemToActiveCart();
                case "6" -> viewActiveCart();
                case "7" -> convertCartToOrder();
                case "b" -> back = true;
                default -> ConsoleHelper.printWarning("Invalid input.");
            }
        }
    }

    private void viewAllCarts() {
        int userId = SessionContext.getCurrentCustomer().getUserId();
        List<StoredCart> cartList = storedCartService.getAllCarts(userId);

        for (StoredCart cart : cartList) {
            String status = cart.isActive() ? "✅" : "⬜";
            System.out.printf("%s Cart #%d - \"%s\" | Total: %.2f%n",
                    status, cart.getCartId(), cart.getName(), cart.getTotalPrice());
            ConsoleHelper.printDivider();
        }
    }

    public void setActiveCart() {
        int userId = SessionContext.getCurrentCustomer().getUserId();

        ConsoleHelper.printHeader("Set active cart");
        ConsoleHelper.prompt("Set which cart to active? ");
        int cartId = Integer.parseInt(sc.nextLine().trim());

        try {
            storedCartService.setActiveCart(userId, cartId);
            ConsoleHelper.printSuccess("Set cart #" + cartId + " to active successfully.");
        } catch (CartException e) {
            ConsoleHelper.printError("Failed to set active cart: " + e.getMessage());
        }
    }

    private void createNewCart() {
        int userId = SessionContext.getCurrentCustomer().getUserId();

        ConsoleHelper.printHeader("Create new cart");
        ConsoleHelper.prompt("Enter a name for the new cart: ");

        try {
            storedCartService.createCart(userId, sc.nextLine());
            ConsoleHelper.printSuccess("Cart created successfully!");
        } catch (CartException e) {
            ConsoleHelper.printError(e.getMessage());
        }
    }

    private void renameCart() {
        ConsoleHelper.printHeader("Rename cart");
        viewAllCarts();

        ConsoleHelper.prompt("Which cart would you like to rename? (ID) ");
        int id = Integer.parseInt(sc.nextLine().trim());
        ConsoleHelper.prompt("Enter a new name for the cart: ");
        String newName = sc.nextLine().trim();

        try {
            storedCartService.renameCart(id, newName);
        } catch (CartException e) {
            ConsoleHelper.printError(e.getMessage());
        }
    }

    private void addItemToActiveCart() {
        int userId = SessionContext.getCurrentCustomer().getUserId();
        StoredCart cart = storedCartService.getActiveCart(userId);

        if (cart == null) {
            ConsoleHelper.printWarning("No active cart found.");
            return;
        }

        ConsoleHelper.printHeader("Add item to active cart");
        ConsoleHelper.prompt("Enter product ID: ");
        int productId = Integer.parseInt(sc.nextLine().trim());
        ConsoleHelper.prompt("Enter quantity: ");
        int quantity = Integer.parseInt(sc.nextLine().trim());

        try {
            storedCartService.addProduct(cart.getCartId(), productId, quantity);
            ConsoleHelper.printSuccess("Item added successfully!");
        } catch (CartException e) {
            ConsoleHelper.printError(e.getMessage());
        }
    }

    private void viewActiveCart() {
        int userId = SessionContext.getCurrentCustomer().getUserId();
        StoredCart cart = storedCartService.getActiveCart(userId);

        if (cart == null) {
            ConsoleHelper.printWarning("No active cart found.");
            return;
        }

        ConsoleHelper.printHeader("View active cart");

        Map<Product, Integer> items = cart.getItems();

        if (items.isEmpty()) {
            ConsoleHelper.printWarning("Cart is empty.");
            return;
        }

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double subtotal = product.getPrice() * quantity;

            System.out.printf("🛒 %s x%d - %.2f kr (%.2f kr each)%n",
                    product.getName(), quantity, subtotal, product.getPrice());
            ConsoleHelper.printDivider();
        }

        System.out.printf("💰 Total: %.2f kr%n", cart.getTotalPrice());
    }

    private void convertCartToOrder() {
        int userId = SessionContext.getCurrentCustomer().getUserId();
        StoredCart activeCart = storedCartService.getActiveCart(userId);
        List<StoredCart> allCarts = storedCartService.getAllCarts(userId);

        ConsoleHelper.printHeader("Convert Cart to Order");

        System.out.printf("Active: ✅ Cart #%d - \"%s\" | Total: %.2f kr%n",
                activeCart.getCartId(), activeCart.getName(), activeCart.getTotalPrice());

        System.out.println("\nOther carts:");
        for (StoredCart cart : allCarts) {
            if (!cart.isActive()) {
                System.out.printf("⬜ Cart #%d - \"%s\" | Total: %.2f kr%n",
                        cart.getCartId(), cart.getName(), cart.getTotalPrice());
            }
        }

        ConsoleHelper.prompt("Use active cart? (y/n): ");
        String choice = sc.nextLine().trim().toLowerCase();

        int selectedCartId;
        if (choice.equals("n")) {
            ConsoleHelper.prompt("Enter cart ID to convert: ");
            selectedCartId = Integer.parseInt(sc.nextLine().trim());
        } else {
            selectedCartId = activeCart.getCartId();
        }

        try {
            StoredCart selectedCart = allCarts.stream()
                    .filter(c -> c.getCartId() == selectedCartId)
                    .findFirst()
                    .orElse(null);

            if (selectedCart == null) {
                ConsoleHelper.printError("No cart found with that ID.");
                return;
            }

            if (selectedCart.getItems().isEmpty()) {
                ConsoleHelper.printWarning("That cart is empty. Add items before placing an order.");
                return;
            }

            storedCartService.convertCartToOrder(selectedCartId);
            ConsoleHelper.printSuccess("Order placed successfully and cart cleared.");
        } catch (CartException e) {
            ConsoleHelper.printError("Failed to convert cart: " + e.getMessage());
        }
    }
}

package controllers;

import contexts.LoginContext;
import contexts.SessionContext;
import entities.Product;
import factories.ServiceFactory;
import renderers.CartRenderer;
import renderers.ProductRenderer;
import services.ProductService;
import services.SessionCartService;
import utils.ConsoleHelper;
import utils.InputUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CustomerShopController {
    private final ProductService productService = ServiceFactory.getProductService();
    private final SessionCartService sessionCartService = ServiceFactory.getSessionCartService();
    private final Scanner sc = new Scanner(System.in);

    public void runCustomerShopMenu() throws SQLException {
        boolean back = false;

        while (!back) {
            displayShopOptions();
            String choice = sc
                    .nextLine()
                    .trim()
                    .toLowerCase();

            switch (choice) {
                case "1" -> browseAllProducts();
                case "2" -> new FilterController().runMenu();
                case "3" -> viewProductDetails();
                case "4" -> addProductToCart();
                case "5" -> viewCart();
                case "6" -> {
                    new ReviewController().runMenu();
                    ConsoleHelper.printDivider();
                }
                case "7" -> checkoutCart();
                case "8" -> {
                    if (SessionContext.isGuest()) {
                        ConsoleHelper.printWarning("You must be logged in to manage saved carts.");
                    } else {
                        new StoredCartController().runMenu();
                    }
                }
                case "9" -> {
                    try {
                        new OrderController().viewCustomerOrderHistory();
                    } catch (SQLException e) {
                        ConsoleHelper.printError("Could not load order history: " + e.getMessage());
                    }
                }
                case "l" -> {
                    if (!SessionContext.isGuest()) {
                        LoginContext.logout();
                        ConsoleHelper.printSuccess("You have been logged out.");
                        back = true;
                    } else {
                        ConsoleHelper.printWarning("You are not logged in.");
                    }
                }
                case "b" -> back = true;
                default -> ConsoleHelper.printWarning("Invalid input.");
            }
        }
    }

    private void displayShopOptions() {
        ConsoleHelper.printHeader("Customer Shop Menu");
        ConsoleHelper.printOption("1",
                "Browse all products");
        ConsoleHelper.printOption("2",
                "Filter products");
        ConsoleHelper.printOption("3",
                "View product details");
        ConsoleHelper.printOption("4",
                "Add product to cart");
        ConsoleHelper.printOption("5",
                "View cart");
        ConsoleHelper.printOption("6",
                "Reviews (view or leave)");
        ConsoleHelper.printOption("7",
                "Checkout");
        ConsoleHelper.printOption("8",
                "Manage saved carts");
        ConsoleHelper.printOption("9",
                "Order History");

        if (!SessionContext.isGuest()) {
            ConsoleHelper.printOption("L",
                    "Logout");
        }

        ConsoleHelper.printOption("B",
                "Back to main menu");
        ConsoleHelper.prompt("Choose an option: ");
    }

    private void browseAllProducts() {
        ConsoleHelper.printHeader("🎩️ Product Browser");
        displayAllProducts();
    }

    private void viewProductDetails() {
        ConsoleHelper.prompt("Enter product ID: ");
        int productId = InputUtils.promptPositiveInt(sc,
                "");

        try {
            Product p = productService.getProductById(productId);
            if (p != null) {
                ConsoleHelper.printDivider();
                displaySingleProduct(p);
            } else {
                ConsoleHelper.printWarning("Product not found.");
            }
        } catch (SQLException e) {
            ConsoleHelper.printError("Could not load product: " + e.getMessage());
        }
    }

    private void addProductToCart() {
        try {
            displayAllProducts();

            ConsoleHelper.prompt("Enter product ID to add to cart: ");
            int productId = InputUtils.promptPositiveInt(sc,
                    "");

            Product selected = productService.getProductById(productId);
            if (selected == null) {
                ConsoleHelper.printWarning("Product not found.");
                return;
            }

            displaySingleProduct(selected);

            ConsoleHelper.prompt("Enter quantity: ");
            int quantity = InputUtils.promptPositiveInt(sc,
                    "");

            if (quantity > selected.getStockQuantity()) {
                ConsoleHelper.printWarning("Not enough stock available.");
                return;
            }

            sessionCartService.addProductToCart(productId,
                    quantity);
            ConsoleHelper.printSuccess("Added to cart.");

        } catch (SQLException e) {
            ConsoleHelper.printError("Could not load products: " + e.getMessage());
        }
        ConsoleHelper.printDivider();
    }

    private void viewCart() {
        Map<Product, Integer> cartItems = sessionCartService.getProductList();
        ConsoleHelper.printHeader("🛒 Your Cart");

        String cartName = "Session Cart";
        String ownerName = SessionContext.isCustomer() ? SessionContext
                .getCurrentCustomer()
                .getName() : "Guest";
        CartRenderer.printCartHeader(cartName,
                ownerName);

        if (cartItems.isEmpty()) {
            ConsoleHelper.printWarning("Your cart is empty.");
        } else {
            cartItems.forEach(CartRenderer::printCartItem);
            CartRenderer.printCartTotal(sessionCartService.getTotalPrice());
        }
    }

    private void checkoutCart() {
        if (SessionContext.isGuest()) {
            ConsoleHelper.printWarning("You must be logged in to place an order.");
            return;
        }

        Map<Product, Integer> cartItems = sessionCartService.getProductList();
        if (cartItems.isEmpty()) {
            ConsoleHelper.printWarning("Your cart is empty.");
            return;
        }

        ConsoleHelper.printHeader("🛒 Checkout");

        cartItems.forEach(CartRenderer::printCartItem);
        CartRenderer.printCartTotal(sessionCartService.getTotalPrice());

        ConsoleHelper.prompt("Proceed with checkout? (y/n): ");
        String checkoutChoice = sc
                .nextLine()
                .trim()
                .toLowerCase();

        if (checkoutChoice.equals("y")) {
            try {
                Integer customerId = SessionContext.getLoggedInUserId();
                if (customerId == null) {
                    ConsoleHelper.printError("Unexpected error: could not find logged-in user.");
                    return;
                }

                int orderId = sessionCartService.convertToOrder(customerId);
                ConsoleHelper.printSuccess("Order placed successfully! Order ID: " + orderId);
            } catch (Exception e) {
                ConsoleHelper.printError("Checkout failed: " + e.getMessage());
            }
        } else {
            ConsoleHelper.printWarning("Checkout cancelled.");
        }
    }

    private void displayAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            for (Product p : products) {
                displaySingleProduct(p);
            }
        } catch (SQLException e) {
            ConsoleHelper.printError("Could not load products: " + e.getMessage());
        }
    }

    private void displaySingleProduct(Product p) throws SQLException {
        String manufacturerName =
                ServiceFactory
                        .getManufacturerService()
                        .getManufacturerNameById(p.getManufacturerId());
        double avgRating = ServiceFactory
                .getReviewService()
                .getAverageRatingByProductId(p.getProductId());
        ProductRenderer.printProduct(p,
                manufacturerName,
                avgRating);
    }
}

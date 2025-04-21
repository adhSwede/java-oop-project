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

    public void runCustomerShopMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean back = false;

        while (!back) {
            ConsoleHelper.printHeader("Customer Shop Menu");
            ConsoleHelper.printOption("1", "Browse all products");
            ConsoleHelper.printOption("2", "Filter products");
            ConsoleHelper.printOption("3", "View product details");
            ConsoleHelper.printOption("4", "Add product to cart");
            ConsoleHelper.printOption("5", "View cart");
            ConsoleHelper.printOption("6", "Reviews (view or leave)");

            if (!SessionContext.isGuest()) {
                ConsoleHelper.printOption("L", "Logout");
            }

            ConsoleHelper.printOption("B", "Back to main menu");

            ConsoleHelper.prompt("Choose an option: ");

            String choice = sc.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1" -> {
                    ConsoleHelper.printHeader("🎩️ Product Browser");
                    try {
                        List<Product> products = productService.getAllProducts();
                        for (Product p : products) {
                            String manufacturerName = ServiceFactory.getManufacturerService().getManufacturerNameById(p.getManufacturerId());
                            double avgRating = ServiceFactory.getReviewService().getAverageRatingByProductId(p.getProductId());
                            ProductRenderer.printProduct(p, manufacturerName, avgRating);
                        }
                    } catch (SQLException e) {
                        ConsoleHelper.printError("Could not load products: " + e.getMessage());
                    }
                }

                case "2" -> new FilterController().runMenu();

                case "3" -> {
                    ConsoleHelper.prompt("Enter product ID: ");
                    int productId = InputUtils.promptPositiveInt(sc, "");
                    try {
                        Product p = productService.getProductById(productId);
                        if (p != null) {
                            ConsoleHelper.printDivider();
                            String manufacturerName = ServiceFactory.getManufacturerService().getManufacturerNameById(p.getManufacturerId());
                            double avgRating = ServiceFactory.getReviewService().getAverageRatingByProductId(p.getProductId());
                            ProductRenderer.printProduct(p, manufacturerName, avgRating);
                        } else {
                            ConsoleHelper.printWarning("Product not found.");
                        }
                    } catch (SQLException e) {
                        ConsoleHelper.printError("Could not load product: " + e.getMessage());
                    }
                }

                case "4" -> {
                    try {
                        List<Product> allProducts = productService.getAllProducts();
                        for (Product p : allProducts) {
                            String manufacturer = ServiceFactory.getManufacturerService().getManufacturerNameById(p.getManufacturerId());
                            double avgRating = ServiceFactory.getReviewService().getAverageRatingByProductId(p.getProductId());
                            ProductRenderer.printProduct(p, manufacturer, avgRating);
                        }

                        ConsoleHelper.prompt("Enter product ID to add to cart: ");
                        int productId = InputUtils.promptPositiveInt(sc, "");

                        Product selected = productService.getProductById(productId);
                        if (selected == null) {
                            ConsoleHelper.printWarning("Product not found.");
                            return;
                        }

                        String manufacturer = ServiceFactory.getManufacturerService().getManufacturerNameById(selected.getManufacturerId());
                        double avgRating = ServiceFactory.getReviewService().getAverageRatingByProductId(selected.getProductId());
                        ProductRenderer.printProduct(selected, manufacturer, avgRating);

                        ConsoleHelper.prompt("Enter quantity: ");
                        int quantity = InputUtils.promptPositiveInt(sc, "");

                        if (quantity > selected.getStockQuantity()) {
                            ConsoleHelper.printWarning("Not enough stock available.");
                            return;
                        }

                        sessionCartService.addProductToCart(productId, quantity);
                        ConsoleHelper.printSuccess("Added to cart.");

                    } catch (SQLException e) {
                        ConsoleHelper.printError("Could not load products: " + e.getMessage());
                    }
                    ConsoleHelper.printDivider();
                }

                case "5" -> {
                    Map<Product, Integer> cartItems = sessionCartService.getProductList();
                    ConsoleHelper.printHeader("🛒 Your Cart");

                    String cartName = "Session Cart";
                    String ownerName = SessionContext.isCustomer()
                            ? SessionContext.getCurrentCustomer().getName()
                            : "Guest";
                    CartRenderer.printCartHeader(cartName, ownerName);

                    if (cartItems.isEmpty()) {
                        ConsoleHelper.printWarning("Your cart is empty.");
                    } else {
                        cartItems.forEach(CartRenderer::printCartItem);
                        CartRenderer.printCartTotal(sessionCartService.getTotalPrice());
                    }
                }

                case "6" -> {
                    new ReviewController().runMenu();
                    ConsoleHelper.printDivider();
                }

                case "l" -> {
                        LoginContext.logout();
                        ConsoleHelper.printSuccess("You have been logged out.");
                        back = true;
                }

                case "b" -> back = true;
                default -> ConsoleHelper.printWarning("Invalid input.");
            }
        }
    }
}
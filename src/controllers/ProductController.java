package controllers;

import entities.Product;
import factories.ServiceFactory;
import renderers.ProductRenderer;
import services.CategoryService;
import services.ProductService;
import utils.ConsoleHelper;
import utils.InputUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProductController {
    private final ProductService productService = ServiceFactory.getProductService();
    private final CategoryService categoryService = ServiceFactory.getCategoryService();

    public void runMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String select;

        do {
            ConsoleHelper.printHeader("📦 Product Menu");
            ConsoleHelper.printOption("1",
                    "Get all products");
            ConsoleHelper.printOption("2",
                    "Find product by ID");
            ConsoleHelper.printOption("3",
                    "Find product by name");
            ConsoleHelper.printOption("4",
                    "Find product by category");
            ConsoleHelper.printOption("5",
                    "Add new product");
            ConsoleHelper.printOption("6",
                    "Update product price");
            ConsoleHelper.printOption("7",
                    "Update product stock quantity");
            ConsoleHelper.printOption("B",
                    "Back to previous menu");
            ConsoleHelper.prompt("Select an option: ");

            select = sc
                    .nextLine()
                    .trim()
                    .toLowerCase();

            switch (select) {
                case "1" -> {
                    List<Product> products = productService.getAllProducts();
                    if (products.isEmpty()) {
                        ConsoleHelper.printWarning("No products found.");
                    } else {
                        for (Product p : products) {
                            String manufacturer =
                                    ServiceFactory
                                            .getManufacturerService()
                                            .getManufacturerNameById(p.getManufacturerId());
                            double avgRating =
                                    ServiceFactory
                                            .getReviewService()
                                            .getAverageRatingByProductId(p.getProductId());
                            ProductRenderer.printProduct(p,
                                    manufacturer,
                                    avgRating);
                        }
                    }
                    ConsoleHelper.printDivider();
                }

                case "2" -> {
                    int id = InputUtils.promptPositiveInt(sc,
                            "Enter product ID: ");
                    Product p = productService.getProductById(id);
                    if (p != null) {
                        String manufacturer =
                                ServiceFactory
                                        .getManufacturerService()
                                        .getManufacturerNameById(p.getManufacturerId());
                        double avgRating =
                                ServiceFactory
                                        .getReviewService()
                                        .getAverageRatingByProductId(p.getProductId());
                        ProductRenderer.printProduct(p,
                                manufacturer,
                                avgRating);
                    } else {
                        ConsoleHelper.printWarning("Product not found.");
                    }
                    ConsoleHelper.printDivider();
                }

                case "3" -> {
                    ConsoleHelper.prompt("Enter product name: ");
                    String name = sc.nextLine();
                    List<Product> results = productService.getProductByName(name);
                    if (results.isEmpty()) {
                        ConsoleHelper.printWarning("No products found with that name.");
                    } else {
                        for (Product p : results) {
                            String manufacturer =
                                    ServiceFactory
                                            .getManufacturerService()
                                            .getManufacturerNameById(p.getManufacturerId());
                            double avgRating =
                                    ServiceFactory
                                            .getReviewService()
                                            .getAverageRatingByProductId(p.getProductId());
                            ProductRenderer.printProduct(p,
                                    manufacturer,
                                    avgRating);
                        }
                    }
                    ConsoleHelper.printDivider();
                }

                case "4" -> {
                    ConsoleHelper.prompt("Enter product category: ");
                    String category = sc.nextLine();
                    List<Product> results = productService.getProductByCategory(category);
                    if (results.isEmpty()) {
                        ConsoleHelper.printWarning("No products found in that category.");
                    } else {
                        for (Product p : results) {
                            String manufacturer =
                                    ServiceFactory
                                            .getManufacturerService()
                                            .getManufacturerNameById(p.getManufacturerId());
                            double avgRating =
                                    ServiceFactory
                                            .getReviewService()
                                            .getAverageRatingByProductId(p.getProductId());
                            ProductRenderer.printProduct(p,
                                    manufacturer,
                                    avgRating);
                        }
                    }
                    ConsoleHelper.printDivider();
                }

                case "5" -> {
                    ConsoleHelper.prompt("Enter product name: ");
                    String name = sc.nextLine();

                    ConsoleHelper.prompt("Enter product category: ");
                    String categoryName = sc.nextLine();

                    int manufacturerId = InputUtils.promptPositiveInt(sc,
                            "Enter manufacturer ID: ");
                    ConsoleHelper.prompt("Enter product description: ");
                    String description = sc.nextLine();
                    double price = InputUtils.promptPositiveDouble(sc,
                            "Enter product price: ");
                    int stockQuantity = InputUtils.promptPositiveInt(sc,
                            "Enter stock quantity: ");

                    Product newProduct = new Product(manufacturerId,
                            name,
                            description,
                            price,
                            stockQuantity);
                    int productId = productService.addProductAndReturnId(newProduct);
                    int categoryId = categoryService.getOrCreateCategoryId(categoryName);
                    productService.addProductToCategory(productId,
                            categoryId);

                    ConsoleHelper.printSuccess("Product added and linked to category successfully.");
                    ConsoleHelper.printDivider();
                }

                case "6" -> {
                    int id = InputUtils.promptPositiveInt(sc,
                            "Enter product ID: ");
                    double price = InputUtils.promptPositiveDouble(sc,
                            "Enter new price: ");
                    productService.updatePrice(id,
                            price);
                    ConsoleHelper.printSuccess("Product price updated.");
                    ConsoleHelper.printDivider();
                }

                case "7" -> {
                    int id = InputUtils.promptPositiveInt(sc,
                            "Enter product ID: ");
                    int quantity = InputUtils.promptPositiveInt(sc,
                            "Enter new stock quantity: ");
                    productService.updateStock(id,
                            quantity);
                    ConsoleHelper.printSuccess("Product stock updated.");
                    ConsoleHelper.printDivider();
                }

                case "b" -> ConsoleHelper.printSuccess("Returning to main menu...");
                default -> ConsoleHelper.printWarning("Invalid option. Please try again.");
            }

        } while (!select.equals("b"));
    }
}
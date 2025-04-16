package controllers;

import entities.Product;
import services.CategoryService;
import services.ProductService;
import utils.ConsoleHelper;
import utils.InputUtils;

import java.sql.SQLException;
import java.util.Scanner;

public class ProductController {
    private final ProductService productService = new ProductService();
    private final CategoryService categoryService = new CategoryService();

    public void runMenu() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String select;

        do {
            ConsoleHelper.clearConsole();

            System.out.println("\n--- Product Menu ---");
            System.out.println("1. Get all products");
            System.out.println("2. Find product by ID");
            System.out.println("3. Find product by name");
            System.out.println("4. Find product by category");
            System.out.println("5. Add new product");
            System.out.println("6. Update product price");
            System.out.println("7. Update product stock quantity");
            System.out.println("B. Back to main menu");
            System.out.print("Select an option: ");
            select = sc.nextLine().trim().toLowerCase();

            switch (select) {
                case "1" -> ConsoleHelper.printList(productService.getAllProducts());

                case "2" -> {
                    int id = InputUtils.promptPositiveInt(sc, "Enter product ID: ");
                    Product product = productService.getProductById(id);
                    System.out.println(product != null ? product : "Product not found.");
                }

                case "3" -> {
                    System.out.print("Enter product name: ");
                    String name = sc.nextLine();
                    ConsoleHelper.printList(productService.getProductByName(name));
                }

                case "4" -> {
                    System.out.print("Enter product category: ");
                    String category = sc.nextLine();
                    ConsoleHelper.printList(productService.getProductByCategory(category));
                }

                case "5" -> {
                    System.out.print("Enter product name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter product category: ");
                    String categoryName = sc.nextLine();

                    int manufacturerId = InputUtils.promptPositiveInt(sc, "Enter manufacturer ID: ");
                    System.out.print("Enter product description: ");
                    String description = sc.nextLine();
                    double price = InputUtils.promptPositiveDouble(sc, "Enter product price: ");
                    int stockQuantity = InputUtils.promptPositiveInt(sc, "Enter stock quantity: ");

                    Product newProduct = new Product(manufacturerId, name, description, price, stockQuantity);
                    int productId = productService.addProductAndReturnId(newProduct);
                    int categoryId = categoryService.getOrCreateCategoryId(categoryName);
                    productService.addProductToCategory(productId, categoryId);

                    System.out.println("Product added and linked to category successfully.");
                }

                case "6" -> {
                    int id = InputUtils.promptPositiveInt(sc, "Enter product ID: ");
                    double price = InputUtils.promptPositiveDouble(sc, "Enter new price: ");
                    productService.updatePrice(id, price);
                }

                case "7" -> {
                    int id = InputUtils.promptPositiveInt(sc, "Enter product ID: ");
                    int quantity = InputUtils.promptPositiveInt(sc, "Enter new stock quantity: ");
                    productService.updateStock(id, quantity);
                }

                case "b" -> System.out.println("Returning to main menu...");

                default -> System.out.println("Invalid option. Please try again.");
            }

        } while (!select.equals("b"));
    }
}
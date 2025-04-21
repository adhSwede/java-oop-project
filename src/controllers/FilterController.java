package controllers;

import entities.Product;
import exceptions.FilterException;
import filters.FilterValidator;
import filters.ProductFilter;
import factories.ServiceFactory;
import services.CategoryService;
import services.ProductService;
import renderers.ProductRenderer;
import utils.ConsoleHelper;
import utils.InputUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class FilterController {
    public static Object runMenu;
    private final ProductService productService = ServiceFactory.getProductService();
    private final CategoryService categoryService = ServiceFactory.getCategoryService();
    private final Scanner sc = new Scanner(System.in);

    public void runMenu() {
        boolean back = false;

        while (!back) {
            ConsoleHelper.printHeader("🔍 Filter Products");
            ConsoleHelper.printOption("1", "Filter by category");
            ConsoleHelper.printOption("2", "Filter by price range");
            ConsoleHelper.printOption("3", "Filter by category + price");
            ConsoleHelper.printOption("B", "Back");
            ConsoleHelper.prompt("Choose a filter option: ");
            String choice = sc.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1" -> filterByCategory();
                case "2" -> filterByPriceRange();
                case "3" -> filterByCombined();
                case "b" -> back = true;
                default -> ConsoleHelper.printWarning("Invalid selection.");
            }
        }
    }

    private void filterByCategory() {
        ConsoleHelper.prompt("Enter category name: ");
        String category = sc.nextLine().trim();

        try {
            FilterValidator.validateCategoryName(category);
            FilterValidator.validateCategoryExists(category, categoryService);

            List<Product> products = productService.getAllProducts();
            List<Product> results = ProductFilter.filterByCategory(products, category);
            printResults(results);

        } catch (FilterException e) {
            ConsoleHelper.printWarning(e.getMessage());
        } catch (SQLException e) {
            ConsoleHelper.printError("Could not retrieve products: " + e.getMessage());
        }
    }

    private void filterByPriceRange() {
        double min = InputUtils.promptPositiveDouble(sc, "Enter minimum price: ");
        double max = InputUtils.promptPositiveDouble(sc, "Enter maximum price: ");

        try {
            FilterValidator.validatePriceRange(min, max);

            List<Product> products = productService.getAllProducts();
            List<Product> results = ProductFilter.filterByPriceRange(products, min, max);
            printResults(results);

        } catch (FilterException e) {
            ConsoleHelper.printWarning(e.getMessage());
        } catch (SQLException e) {
            ConsoleHelper.printError("Could not retrieve products: " + e.getMessage());
        }
    }

    private void filterByCombined() {
        ConsoleHelper.prompt("Enter category name: ");
        String category = sc.nextLine().trim();
        double min = InputUtils.promptPositiveDouble(sc, "Enter minimum price: ");
        double max = InputUtils.promptPositiveDouble(sc, "Enter maximum price: ");

        try {
            FilterValidator.validateCategoryName(category);
            FilterValidator.validateCategoryExists(category, categoryService);
            FilterValidator.validatePriceRange(min, max);

            List<Product> products = productService.getAllProducts();
            List<Product> byCategory = ProductFilter.filterByCategory(products, category);
            List<Product> results = ProductFilter.filterByCategoryAndPriceRange(byCategory, min, max);
            printResults(results);

        } catch (FilterException e) {
            ConsoleHelper.printWarning(e.getMessage());
        } catch (SQLException e) {
            ConsoleHelper.printError("Could not retrieve products: " + e.getMessage());
        }
    }

    private void printResults(List<Product> results) throws SQLException {
        if (results.isEmpty()) {
            ConsoleHelper.printWarning("No products matched your filters.");
        } else {
            ConsoleHelper.printDivider();
            for (Product p : results) {
                String manufacturer = ServiceFactory.getManufacturerService().getManufacturerNameById(p.getManufacturerId());
                double avgRating = ServiceFactory.getReviewService().getAverageRatingByProductId(p.getProductId());
                ProductRenderer.printProduct(p, manufacturer, avgRating);
            }
        }
    }

}
package controllers;

import contexts.SessionContext;
import entities.Product;
import entities.Review;
import exceptions.ReviewException;
import factories.ServiceFactory;
import renderers.ProductRenderer;
import renderers.ReviewRenderer;
import services.ProductService;
import services.ReviewService;
import utils.ConsoleHelper;
import utils.InputUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ReviewController {
    private final ReviewService reviewService = ServiceFactory.getReviewService();
    private final ProductService productService = ServiceFactory.getProductService();

    public void runMenu() {
        Scanner sc = new Scanner(System.in);
        boolean back = false;

        while (!back) {
            ConsoleHelper.printHeader("⭐ Product Reviews");
            ConsoleHelper.printOption("1",
                    "View reviews for a product");
            ConsoleHelper.printOption("2",
                    "Leave a review");
            ConsoleHelper.printOption("B",
                    "Back to previous menu");
            ConsoleHelper.prompt("Choose an option: ");

            String choice = sc
                    .nextLine()
                    .trim()
                    .toLowerCase();

            switch (choice) {
                case "1" -> {
                    ConsoleHelper.prompt("Enter product ID: ");
                    int productId = InputUtils.promptPositiveInt(sc,
                            "");

                    try {
                        Product p = productService.getProductById(productId);
                        if (p == null) {
                            ConsoleHelper.printWarning("Product not found.");
                            break;
                        }

                        String manufacturer =
                                ServiceFactory
                                        .getManufacturerService()
                                        .getManufacturerNameById(p.getManufacturerId());
                        double avgRating = reviewService.getAverageRatingByProductId(p.getProductId());
                        ProductRenderer.printProduct(p,
                                manufacturer,
                                avgRating);

                        List<Review> reviews = reviewService.getReviewsByProductId(productId);
                        if (reviews.isEmpty()) {
                            ConsoleHelper.printWarning("No reviews yet.");
                        } else {
                            reviews.forEach(ReviewRenderer::printReview);
                        }

                    } catch (SQLException e) {
                        ConsoleHelper.printError("Could not load reviews: " + e.getMessage());
                    }
                }

                case "2" -> {
                    ConsoleHelper.prompt("Enter product ID: ");
                    int productId = InputUtils.promptPositiveInt(sc,
                            "");
                    int userId = SessionContext
                            .getCurrentCustomer()
                            .getUserId();

                    try {
                        Product p = productService.getProductById(productId);
                        if (p == null) {
                            ConsoleHelper.printWarning("Product not found.");
                            break;
                        }

                        String manufacturer =
                                ServiceFactory
                                        .getManufacturerService()
                                        .getManufacturerNameById(p.getManufacturerId());
                        double avgRating = reviewService.getAverageRatingByProductId(p.getProductId());
                        ProductRenderer.printProduct(p,
                                manufacturer,
                                avgRating);

                        ConsoleHelper.prompt("Enter rating (1–5): ");
                        int rating = InputUtils.promptPositiveInt(sc,
                                "");

                        ConsoleHelper.prompt("Enter comment (optional): ");
                        String comment = sc.nextLine();

                        try {
                            reviewService.addReview(userId,
                                    productId,
                                    rating,
                                    comment);
                            ConsoleHelper.printSuccess("Review submitted.");
                        } catch (ReviewException e) {
                            ConsoleHelper.printWarning(e.getMessage()); // <- friendly message here
                        }

                    } catch (SQLException e) {
                        ConsoleHelper.printError("Could not submit review: " + e.getMessage());
                    }

                    ConsoleHelper.printDivider();
                }

                case "b" -> back = true;
                default -> ConsoleHelper.printWarning("Invalid input.");
            }
        }
    }
}
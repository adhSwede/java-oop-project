package services;

import entities.Review;
import exceptions.ReviewException;
import factories.RepositoryFactory;
import repositories.OrderRepository;
import repositories.ReviewRepository;

import java.sql.SQLException;
import java.util.List;

public class ReviewService {
    private final ReviewRepository reviewRepository = RepositoryFactory.getReviewRepository();
    private final OrderRepository orderRepository = RepositoryFactory.getOrderRepository();

    // #################### [ Create ] ####################
    public Review addReview(int customerId,
                            int productId,
                            int rating,
                            String comment) throws SQLException {
        comment = comment == null ? "" : comment;

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        if (!orderRepository.hasCustomerPurchased(customerId,
                productId)) {
            throw new ReviewException("You must purchase the product first.");
        }

        if (reviewRepository.hasCustomerReviewed(customerId,
                productId)) {
            throw new ReviewException("You already reviewed this product.");
        }

        return reviewRepository.addReview(customerId,
                productId,
                rating,
                comment);
    }

    // #################### [ Read ] ####################
    public List<Review> getReviewsByProductId(int productId) throws SQLException {
        return reviewRepository.getReviewsByProductId(productId);
    }

    public double getAverageRatingByProductId(int productId) throws SQLException {
        return reviewRepository.getAverageRatingByProductId(productId);
    }

    // #################### [ Validation ] ####################
    public boolean canReviewProduct(int customerId,
                                    int productId) throws SQLException {
        return orderRepository.hasCustomerPurchased(customerId,
                productId) && !reviewRepository.hasCustomerReviewed(customerId,
                productId);
    }

}
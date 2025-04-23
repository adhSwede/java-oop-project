package repositories;

import entities.Review;
import utils.Mappers;
import utils.SqlUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {

    // #################### [ Insert ] ####################

    public Review addReview(int productId,
                            int customerId,
                            int rating,
                            String comment) throws SQLException {
        String query = "INSERT INTO reviews (product_id, customer_id, rating, comment) VALUES (?, ?, ?, ?)";
        int reviewId = SqlUtils.executeInsertAndReturnId(query,
                productId,
                customerId,
                rating,
                comment);
        return new Review(reviewId,
                productId,
                customerId,
                rating,
                comment);
    }

    // #################### [ Get Reviews ] ####################

    public ArrayList<Review> getReviewsByProductId(int productId) throws SQLException {
        String query = "SELECT * FROM reviews WHERE product_id = ?";
        return SqlUtils.executeAndMap(query,
                Mappers.reviewMapper,
                String.valueOf(productId));
    }

    public int getRating(int customerId,
                         int productId) throws SQLException {
        String query = "SELECT rating FROM reviews WHERE customer_id = ? AND product_id = ?";
        List<Integer> result = SqlUtils.executeAndMap(query,
                rs -> rs.getInt("rating"),
                String.valueOf(customerId),
                String.valueOf(productId));
        return result.isEmpty() ? 0 : result.get(0);
    }

    // #################### [ Get Aggregates ] ####################

    public double getAverageRatingByProductId(int productId) throws SQLException {
        String query = "SELECT AVG(rating) AS avg_rating FROM reviews WHERE product_id = ?";
        List<Double> result = SqlUtils.executeAndMap(query,
                rs -> rs.getDouble("avg_rating"),
                String.valueOf(productId));
        return result.isEmpty() ? 0.0 : result.get(0);
    }

    public int getReviewCountByProductId(int productId) throws SQLException {
        String query = "SELECT COUNT(*) AS review_count FROM reviews WHERE product_id = ?";
        List<Integer> result = SqlUtils.executeAndMap(query,
                rs -> rs.getInt("review_count"),
                String.valueOf(productId));
        return result.isEmpty() ? 0 : result.get(0);
    }

    public int getReviewCountByCustomerId(int customerId) throws SQLException {
        String query = "SELECT COUNT(*) AS review_count FROM reviews WHERE customer_id = ?";
        List<Integer> result = SqlUtils.executeAndMap(query,
                rs -> rs.getInt("review_count"),
                String.valueOf(customerId));
        return result.isEmpty() ? 0 : result.get(0);
    }

    // #################### [ Validation ] ####################

    public boolean hasCustomerReviewed(int customerId,
                                       int productId) throws SQLException {
        String query = "SELECT * FROM reviews WHERE customer_id = ? AND product_id = ?";
        List<Review> reviews = SqlUtils.executeAndMap(query,
                Mappers.reviewMapper,
                String.valueOf(customerId),
                String.valueOf(productId));
        return !reviews.isEmpty();
    }
}
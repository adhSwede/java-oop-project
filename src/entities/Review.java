package entities;

public class Review {
    // #################### [ Fields ] ####################
    private int reviewId;
    private int productId;
    private int customerId;
    private int rating;
    private String comment;

    // #################### [ Constructor ] ####################
    public Review(int reviewId, int productId, int customerId, int rating, String comment) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.customerId = customerId;
        this.rating = rating;
        this.comment = comment;
    }

    // #################### [ Getters ] ####################
    public int getReviewId() {
        return reviewId;
    }

    public int getProductId() {
        return productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    // #################### [ Setters ] ####################
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // #################### [ ToString ] ####################
    @Override
    public String toString() {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < rating; i++) {
            stars.append("⭐");
        }

        return stars + " (" + rating + "/5) from Customer #" + customerId + ": " +
                (comment == null || comment.isEmpty() ? "(No comment)" : "\"" + comment + "\"");
    }
}
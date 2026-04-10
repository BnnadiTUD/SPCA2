package dtos;

import java.time.LocalDateTime;

import model.Review;

public class ReviewResponse {
    public Long id;
    public Long itemId;
    public Long customerId;
    public String customerName;
    public int rating;
    public String comment;
    public LocalDateTime createdAt;

    public static ReviewResponse fromEntity(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.id = review.id;
        response.itemId = review.item != null ? review.item.id : null;
        response.customerId = review.customer != null ? review.customer.id : null;
        response.customerName = review.customer != null ? review.customer.name : null;
        response.rating = review.rating;
        response.comment = review.comment;
        response.createdAt = review.createdAt;
        return response;
    }
}


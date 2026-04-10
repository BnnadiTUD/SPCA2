package services;

import dtos.ReviewRequest;
import dtos.ReviewResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import model.Customer;
import model.Item;
import model.Review;
import repos.CustomerRepo;
import repos.ItemRepo;
import repos.ReviewRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ReviewService {

    @Inject
    ReviewRepo reviewRepo;

    @Inject
    ItemRepo itemRepo;

    @Inject
    CustomerRepo customerRepo;

    @Transactional
    public ReviewResponse addReview(Long itemId, ReviewRequest request) {
        Item item = itemRepo.findById(itemId);
        if (item == null) {
            throw new NotFoundException("Item not found");
        }

        Customer customer = customerRepo.findById(request.customerId);
        if (customer == null) {
            throw new NotFoundException("Customer not found");
        }

        if (request.rating < 1 || request.rating > 5) {
            throw new BadRequestException("Rating must be between 1 and 5");
        }

        Review review = new Review();
        review.item = item;
        review.customer = customer;
        review.rating = request.rating;
        review.comment = request.comment;
        review.createdAt = LocalDateTime.now();

        reviewRepo.persist(review);

        return buildReviewResponse(review);
    }

    public List<ReviewResponse> getReviewsForItem(Long itemId) {
        Item item = itemRepo.findById(itemId);
        if (item == null) {
            throw new NotFoundException("Item not found");
        }

        List<Review> reviews = reviewRepo.find("item.id", itemId).list();
        List<ReviewResponse> responses = new ArrayList<>();

        for (Review review : reviews) {
            responses.add(buildReviewResponse(review));
        }

        return responses;
    }

    public double getAverageRatingForItem(Long itemId) {
        List<Review> reviews = reviewRepo.find("item.id", itemId).list();

        if (reviews.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Review review : reviews) {
            total += review.rating;
        }

        return total / reviews.size();
    }

    public long getReviewCountForItem(Long itemId) {
        return reviewRepo.count("item.id", itemId);
    }

    private ReviewResponse buildReviewResponse(Review review) {
        ReviewResponse response = new ReviewResponse();

        response.id = review.id;
        response.customerId = review.customer.id;
        response.customerName = review.customer.name;
        response.rating = review.rating;
        response.comment = review.comment;
        response.createdAt = review.createdAt;

        return response;
    }
}

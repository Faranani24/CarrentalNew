/*
 * ReviewFactory.java
 * Author: Inam Jim (222086939)
 * Date: 11 May 2025
 *
 */

package co.za.carrental.factory;

import co.za.carrental.domain.Review;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ReviewFactory {


    public static Review buildReview(UUID bookingId, UUID customerId, int rating, String comment) {
        if (bookingId == null) {
            throw new IllegalArgumentException("BookingId is required for creating a review");
        }

        if (customerId == null) {
            throw new IllegalArgumentException("CustomerIs is required for creating a review"); // Default to empty string if no comment is provided
        }

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        LocalDateTime now = LocalDateTime.now();

        return new Review.Builder()
                .setReviewId(UUID.randomUUID())
                .setBookingId(bookingId)
                .setCustomerId(customerId)
                .setRating(rating)
                .setComment(comment)
                .build();
    }
}

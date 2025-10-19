package co.za.carrental.factory;

import co.za.carrental.domain.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class ReviewFactoryTest {

    private ReviewFactory reviewFactory;

    @BeforeEach
    void setUp() {
        reviewFactory = new ReviewFactory();
    }

    @Test
    void buildReviewWithGivenValues() {
        UUID bookingId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        int rating = 5;
        String comment = "Excellent service!";

        Review review = ReviewFactory.buildReview(bookingId, customerId, rating, comment);

        assertNotNull(review);
        assertNotNull(review.getReviewId());
        assertEquals(bookingId, review.getBookingId());
        assertEquals(customerId, review.getCustomerId());
        assertEquals(rating, review.getRating());
        assertEquals(comment, review.getComment());
    }


    @Test
    void buildReviewExceptionForNullBookingId() {
        UUID customerId = UUID.randomUUID();
        int rating = 5;
        String comment = "Test";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReviewFactory.buildReview(null, customerId, rating, comment);
        });

        assertEquals("BookingId is required for creating a review", exception.getMessage());
    }

    @Test
    void buildReviewExceptionForInvalidRating() {
        UUID bookingId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        String comment = "Test";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReviewFactory.buildReview(bookingId, customerId, 0, comment);
        });
        assertEquals("Rating must be between 1 and 5", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            ReviewFactory.buildReview(bookingId, customerId, 6, comment);
        });
        assertEquals("Rating must be between 1 and 5", exception.getMessage());
    }

}
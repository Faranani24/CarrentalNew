package co.za.carrental.factory;

import co.za.carrental.domain.Review;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReviewFactoryTest {

    // Test method to create a Review object
    @Test
    void createReview() {
        Review review = ReviewFactory.createReview("R123", 4, "Great service!");

        assertNotNull(review);
        assertEquals("R123", review.getReviewId());
        assertEquals(4, review.getRating());
        assertEquals("Great service!", review.getComment());
    }
}

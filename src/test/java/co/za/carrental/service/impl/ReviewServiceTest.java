package co.za.carrental.service.impl;

import co.za.carrental.domain.Review;
import co.za.carrental.factory.ReviewFactory;
import co.za.carrental.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    private Review review1;
    private Review review2;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();

        review1 = ReviewFactory.buildReview(
                UUID.randomUUID(), UUID.randomUUID(),
                5,"Excellent service!"
        );

        review2 = ReviewFactory.buildReview(
                UUID.randomUUID(), UUID.randomUUID(),
                4,"Good experience."
        );


        reviewService.create(review1);
        reviewService.create(review2);
    }

    @Test
    void testCreate_SaveAndReturnReview() {
        Review newReview = ReviewFactory.buildReview(
                UUID.randomUUID(), UUID.randomUUID(),
                5,"Great service!"
        );

        Review createdReview = reviewService.create(newReview);
        assertNotNull(createdReview);
        Optional<Review> found = reviewService.read(createdReview.getReviewId());
        assertTrue(found.isPresent());
        assertEquals(createdReview.getComment(), found.get().getComment());
    }

    @Test
    void testRead_ReturnReviewById() {
        Optional<Review> foundReview = reviewService.read(review1.getReviewId());
        assertTrue(foundReview.isPresent());
        assertEquals(review1.getComment(), foundReview.get().getComment());
    }

    @Test
    void testRead_ReturnEmptyForNonExistentId() {
        UUID nonExistentId = UUID.randomUUID();
        Optional<Review> foundReview = reviewService.read(nonExistentId);
        assertFalse(foundReview.isPresent());
    }

    @Test
    void testUpdate_UpdateAndReturnReview() {
        review1.setComment("Updated comment");
        Review updatedReview = reviewService.update(review1);
        assertNotNull(updatedReview);
        assertEquals("Updated comment", updatedReview.getComment());

        Optional<Review> found = reviewService.read(review1.getReviewId());
        assertTrue(found.isPresent());
        assertEquals("Updated comment", found.get().getComment());
    }

    @Test
    void testDelete_DeleteReview() {
        UUID reviewIdToDelete = review2.getReviewId();
        reviewService.delete(reviewIdToDelete);

        Optional<Review> found = reviewService.read(reviewIdToDelete);
        assertFalse(found.isPresent(), "Review should be deleted and not found");
    }

    @Test
    void testGetAll_ReturnAllReviews() {
        List<Review> reviews = reviewService.getAll();
        assertEquals(2, reviews.size());
        assertTrue(reviews.stream().anyMatch(r -> r.getReviewId().equals(review1.getReviewId())));
        assertTrue(reviews.stream().anyMatch(r -> r.getReviewId().equals(review2.getReviewId())));
    }
}
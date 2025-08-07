package co.za.carrental.controller;

import co.za.carrental.domain.Review;
import co.za.carrental.factory.ReviewFactory;
import co.za.carrental.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewControllerTest {

    @Autowired
    private ReviewController reviewController;

    @Autowired
    private ReviewRepository reviewRepository;

    private Review testReview;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();

        testReview = ReviewFactory.buildReview(
                UUID.randomUUID(), UUID.randomUUID(),
                5,"Excellent service!"
        );
        reviewRepository.save(testReview);
    }

    @Test
    void create_shouldCreateReview() {
        Review newReview = ReviewFactory.buildReview(
                UUID.randomUUID(), UUID.randomUUID(),
                4,"Good experience."
        );
        ResponseEntity<Review> response = reviewController.createReview(newReview);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Good experience.", response.getBody().getComment());
        assertEquals(2, reviewRepository.count());
    }

    @Test
    void read_shouldReturnReviewById() {
        ResponseEntity<Review> response = reviewController.readReview(testReview.getReviewId());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testReview.getComment(), response.getBody().getComment());
    }

    @Test
    void update_shouldUpdateReview() {
        testReview.setComment("Updated comment");
        ResponseEntity<Review> response = reviewController.updateReview(testReview);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated comment", response.getBody().getComment());

        // Verify the change in the database
        assertEquals("Updated comment", reviewRepository.findById(testReview.getReviewId()).get().getComment());
    }

    @Test
    void delete_shouldDeleteReview() {
        reviewController.deleteReview(testReview.getReviewId());

        // Verify the review is deleted from the database
        assertEquals(0, reviewRepository.count());
    }

    @Test
    void getAll_shouldReturnAllReviews() {
        ResponseEntity<List<Review>> response = reviewController.getAllReviews();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}
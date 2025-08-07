/*
 * ReviewService.java
 * Author: Inam Jim (222086939)
 * Date: 11 May 2025
 */
//
package co.za.carrental.service.impl;

import co.za.carrental.domain.Review;
import co.za.carrental.repository.ReviewRepository;
import co.za.carrental.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review create(Review review){
        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> read(UUID reviewId) {
        return reviewRepository.findById(reviewId);
    }

    @Override
    public Review update(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void delete(UUID reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }
}
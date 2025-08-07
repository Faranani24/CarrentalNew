/*
 * IReviewService.java
 * Author: Inam Jim (222086939)
 * Date: 11 May 2025
 *
 */
//
package co.za.carrental.service;

import co.za.carrental.domain.Review;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IReviewService {

    Review create(Review review);

    Optional<Review> read(UUID reviewId);

    Review update(Review review);

    void delete(UUID reviewId);

    List<Review> getAll();
}
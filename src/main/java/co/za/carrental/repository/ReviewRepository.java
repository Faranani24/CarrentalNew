/*
 * ReviewRepository.java
 * Author: Inam Jim (222086939)
 * Date: 11 May 2025
 *
 */
//
package co.za.carrental.repository;

import co.za.carrental.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findByCustomerId(UUID customerId);
    List<Review> findByBookingId(UUID bookingId);
    List<Review> findByRating(int rating);
}

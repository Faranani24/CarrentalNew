/*
 * Review.java
 * Author: Inam Jim (222086939)
 * Date: 11 May 2025
 */
//
package co.za.carrental.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @Column(name = "review_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private final UUID reviewId;

    @Column(name = "booking_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private final UUID bookingId;

    @Column(name = "customer_id", nullable = false, columnDefinition = "VARCHAR(36)")
    private final UUID customerId;

    @Column(name = "rating", nullable = false)
    private int rating; // Changed to mutable

    @Column(name = "comment")
    private String comment; // Changed to mutable

    @Column(name = "created_at", nullable = false, updatable = false)
    private final LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // Changed to mutable

    // Private constructor for the Builder pattern
    private Review(Builder builder) {
        this.reviewId = builder.reviewId;
        this.bookingId = builder.bookingId;
        this.customerId = builder.customerId;
        this.rating = builder.rating;
        this.comment = builder.comment;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    protected Review() {
        this.reviewId = null;
        this.bookingId = null;
        this.customerId = null;
        this.rating = 0;
        this.comment = null;
        this.createdAt = null;
        this.updatedAt = null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getReviewId() {
        return reviewId;
    }
    public UUID getBookingId() {
        return bookingId;
    }
    public UUID getCustomerId() {
        return customerId;
    }
    public int getRating() {
        return rating;
    }
    public String getComment() {
        return comment;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setRating(int rating) {
        if (this.rating != rating) {
            this.rating = rating;
            this.updatedAt = LocalDateTime.now();
        }
    }

    public void setComment(String comment) {
        if (!Objects.equals(this.comment, comment)) {
            this.comment = comment;
            this.updatedAt = LocalDateTime.now();
        }
    }

    // Standard Object methods (remain unchanged as they rely on the final reviewId)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(reviewId, review.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", bookingId=" + bookingId +
                ", customerId=" + customerId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public static class Builder {
        private UUID reviewId;
        private UUID bookingId;
        private UUID customerId;
        private int rating;
        private String comment;
        private LocalDateTime createdAt = LocalDateTime.now();
        private LocalDateTime updatedAt = LocalDateTime.now();

        public Builder setReviewId(UUID reviewId) {
            this.reviewId = reviewId;
            return this;
        }
        public Builder setBookingId(UUID bookingId) {
            this.bookingId = bookingId;
            return this;
        }
        public Builder setCustomerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }
        public Builder setRating(int rating) {
            this.rating = rating;
            return this;
        }
        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Review build() {
            if (reviewId == null || bookingId == null || customerId == null) {
                throw new IllegalStateException("ReviewId, BookingId, and CustomerId must be provided.");
            }
            if (rating < 1 || rating > 5) {
                throw new IllegalStateException("Rating must be between 1 and 5.");
            }
            return new Review(this);
        }
    }
}
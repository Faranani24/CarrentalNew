/*
    Review.java
    Author: Inam Jim (222086939)
    Date: 11 May 2025
*/
package co.za.carrental.domain;

public class Review {

    private String reviewId;
    private int rating;
    private String comment;
    private Car car; // Each review is associated with a specific Car

    private Review(Builder builder) {
        this.reviewId = builder.reviewId;
        this.rating = builder.rating;
        this.comment = builder.comment;
        this.car = builder.car;
    }

    public String getReviewId() {
        return reviewId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='" + reviewId + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", car=" + (car != null ? car.toString() : "null") +
                '}';
    }

    public static class Builder {

        private String reviewId;
        private int rating;
        private String comment;
        private Car car;

        public Builder setReviewId(String reviewId) {
            this.reviewId = reviewId;
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

        public Builder setCar(Car car) {
            this.car = car;
            return this;
        }

        public Builder copy(Review review) {
            if (review != null) {
                this.reviewId = review.getReviewId();
                this.rating = review.getRating();
                this.comment = review.getComment();
                this.car = review.getCar();
            }
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}

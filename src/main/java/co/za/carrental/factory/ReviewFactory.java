package co.za.carrental.factory;

import co.za.carrental.domain.Review;

public class ReviewFactory {

    public static Review createReview (String reviewId, int rating, String comment) {

        return new Review.Builder()
                .setReviewId("R123")
                .setRating((int) 4.5)
                .setComment("Great service!")
                .build();
    }
}

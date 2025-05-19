package co.za.carrental.factory;

import co.za.carrental.domain.Review;

public class ReviewFactory {

    public static Review createReview (String reviewId, int rating, String comment) {

        return new Review.Builder()
                .setreviewId("R123")
                .setrating((int) 4.5)
                .setcomment("Great service!")
                .build();
    }
}

/*  Review.java
    Author:Inam Jim (222086939)
    Date: 11 May 2025
 */
package co.za.carrental.domain;



public class Review extends Car{

    //Sting reviewId  int rating(1-5)  String comment

    private String reviewId;
    private int rating;
    private String comment;

    private Review() {
        super();
    }

    private Review(Builder builder) {
        this.reviewId = builder.reviewId;
        this.rating = builder.rating;
        this.comment = builder.comment;
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

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='" + reviewId + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }

    public static class Builder {

        private String reviewId;
        private int rating;
        private String comment;

        public Builder setreviewId(String reviewId) {
            this.reviewId = reviewId;
            return this;
        }
        public Builder setrating(int rating) {
            this.rating = rating;
            return this;
        }
        public Builder setcomment(String comment) {
            this.comment = comment;
            return this;
        }

        public Review build() {
            return new Review(this);
        }

        public Builder copy(Review review) {

            if (review != null){
                this.reviewId = review.getReviewId();
                this.rating = review.getRating();
                this.comment = review.getComment();
            }
            return this;
        }

    }
}

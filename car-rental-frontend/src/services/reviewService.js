
const mockReviews = {
    '1': [
        { id: 101, user: 'Sarah J.', rating: 5, comment: 'Smooth drive, fuel efficient, and comfortable for long journeys.' },
        { id: 102, user: 'John D.', rating: 4, comment: 'Great car! Handled the city and highway well.' },
    ],
    '2': [
        { id: 201, user: 'Mike R.', rating: 5, comment: 'Awesome compact car. Perfect for city parking and easy on gas.' },
    ],
    '3': [],
};

export async function fetchReviewsByCarId(carId) {
    await new Promise(resolve => setTimeout(resolve, 300));
    return mockReviews[carId] || [];

}


export async function submitReview(reviewData) {
    await new Promise(resolve => setTimeout(resolve, 500));

    const carId = reviewData.carId;
    if (!mockReviews[carId]) {
        mockReviews[carId] = [];
    }

    const newReview = {
        id: Date.now(),
        user: reviewData.fullName,
        rating: reviewData.rating,
        comment: reviewData.comment
    };
    mockReviews[carId].push(newReview);

    console.log('New review submitted:', newReview);
    return { success: true };
}
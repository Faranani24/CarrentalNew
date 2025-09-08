const API_BASE_URL = "http://localhost:8082/api/cars";

/**
 * Adds a new car to the backend API.
 * Sends car JSON as @RequestPart("car") + image as @RequestPart("image").
 */
export async function addCar(carDetails, imageFile) {
    const formData = new FormData();
    formData.append(
        "car",
        new Blob([JSON.stringify(carDetails)], { type: "application/json" })
    );
    if (imageFile) {
        formData.append("image", imageFile);

    }

    try {
        const response = await fetch(API_BASE_URL, {
            method: "POST",
            body: formData,
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Failed to add car: ${errorText}`);
        }

        return await response.json();
    } catch (error) {
        console.error("Error adding car:", error);
        throw error;
    }
}

/**
 * Fetches all cars from the backend API.
 * Each car gets an imageUrl pointing to /{carId}/image.
 */
export async function fetchAllCars() {
    try {
        const response = await fetch(API_BASE_URL);

        if (!response.ok) {
            throw new Error("Failed to fetch cars.");
        }

        const fetchedCars = await response.json();
        return fetchedCars.map((car) => ({
            ...car,
            imageUrl: `${API_BASE_URL}/${car.carId}/image`,
        }));
    } catch (error) {
        console.error("Error fetching all cars:", error);
        throw error;
    }
}

/**
 * Fetches a single car by its ID.
 */
export async function fetchCarById(id) {
    try {
        const res = await fetch(`${API_BASE_URL}/${id}`);
        if (!res.ok) {
            throw new Error(`Failed to fetch car with ID: ${id}`);
        }
        const data = await res.json();
        return {
            ...data,
            imageUrl: `${API_BASE_URL}/${data.carId}/image`,
        };
    } catch (error) {
        console.error(`Error fetching car with ID ${id}:`, error);
        throw error;
    }
}

/**
 * Creates a booking.
 */
export async function bookCar(bookingDetails) {
    try {
        const response = await fetch("http://localhost:8082/api/bookings", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(bookingDetails),
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Failed to book car: ${errorText}`);
        }

        return await response.json();
    } catch (error) {
        console.error("Booking API call failed:", error);
        throw error;
    }
}

/**
 * Submits a review.
 * (Replace with real API when backend is ready.)
 */
export async function submitReview(reviewData) {
    console.log("Submitting review:", reviewData);
    return new Promise((resolve) =>
        setTimeout(() => {
            console.log("Review submitted successfully!");
            resolve({ success: true });
        }, 1000)
    );
}

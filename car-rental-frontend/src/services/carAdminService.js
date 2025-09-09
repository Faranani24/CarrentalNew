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
            body: formData
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Failed to add car: ${errorText || response.statusText}`);
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
        const cars = await response.json();
        return cars.map(car => ({
            ...car,
            imageUrl: `${API_BASE_URL}/${car.carId}/image`
        }));
    } catch (error) {
        console.error("Error fetching cars:", error);
        throw error;
    }
}

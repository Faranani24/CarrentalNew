// services/carService.js
import axios from 'axios'

const API_URL = 'http://localhost:8080/api/cars'

// Fetch all cars
export async function fetchCars() {
    try {
        const response = await axios.get(API_URL)
        return response.data.map(car => ({
            ...car,
            imageUrl: `${API_URL}/${car.carId}/image` // build image URL
        }))
    } catch (error) {
        console.error('Error fetching cars:', error)
        throw error
    }

}


// Fetch a single car by ID
export async function fetchCarById(carId) {
    try {
        const response = await axios.get(`${API_URL}/${carId}`)
        const car = response.data
        return {
            ...car,
            imageUrl: `${API_URL}/${car.carId}/image`
        }
    } catch (error) {
        console.error(`Error fetching car with ID ${carId}:`, error)
        throw error
    }
}

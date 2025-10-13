import axios from 'axios'

const API_URL = 'http://localhost:8082/api/cars'

// Fetch all cars (used for admin panel)
export async function fetchCars() {
    try {
        const response = await axios.get(API_URL)
        return response.data.map(car => ({
            ...car,
            // Now the image URL is based on the new backend endpoint
            imageUrl: `${API_URL}/${car.carId}/image`
        }))
    } catch (error) {
        console.error('Error fetching cars:', error)
        throw error
    }
}

// Fetch cars available for specific date range
export async function fetchAvailableCars(startDate, endDate) {
    try {
        const response = await axios.get(`${API_URL}/available`, {
            params: { startDate, endDate }
        })
        return response.data.map(car => ({
            ...car,
            imageUrl: `${API_URL}/${car.carId}/image` // Use backend endpoint
        }))
    } catch (error) {
        console.error('Error fetching available cars:', error)
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
            // You already updated this one, so it's correct
            imageUrl: `${API_URL}/${car.carId}/image`
        }
    } catch (error) {
        console.error(`Error fetching car with ID ${carId}:`, error)
        throw error
    }
}

// Delete a car by carId
export async function deleteCar(carId) {
    try {
        await axios.delete(`${API_URL}/${carId}`)
    } catch (error) {
        console.error(`Error deleting car with ID ${carId}:`, error)
        throw error
    }
}

// REMOVE THIS HELPER FUNCTION - IT'S NO LONGER NEEDED AND IS CAUSING THE ERROR
// function arrayBufferToBase64(buffer) {
//     if (!buffer) return ''
//     if (typeof buffer === 'string') return buffer
//     if (buffer.data && Array.isArray(buffer.data)) {
//         buffer = Uint8Array.from(buffer.data).buffer
//     }
//     let binary = ''
//     const bytes = new Uint8Array(buffer)
//     for (let i = 0; i < bytes.byteLength; i++)
//         binary += String.fromCharCode(bytes[i])
//     return window.btoa(binary)
// }
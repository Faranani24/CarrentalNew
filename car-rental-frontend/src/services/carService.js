import axios from 'axios'

const API_URL = 'http://localhost:8082/api/cars'

export async function fetchCars() {
    try {
        const response = await axios.get(API_URL)
        return response.data.map(car => ({
            ...car,
            imageUrl: `${API_URL}/${car.carId}/image`
        }))
    } catch (error) {
        console.error('Error fetching cars:', error)
        throw error
    }
}

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

export async function deleteCar(carId) {
    try {
        await axios.delete(`${API_URL}/${carId}`)
    } catch (error) {
        console.error(`Error deleting car with ID ${carId}:`, error)
        throw error
    }
}

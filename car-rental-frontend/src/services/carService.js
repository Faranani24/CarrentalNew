import api from './api'

const API_URL = 'http://localhost:8082/api/cars'

export async function fetchCars() {
    try {
        const response = await api.get('/cars')
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
        const response = await api.get('/cars/available', {
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
        const response = await api.get(`/cars/${carId}`)
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
        await api.delete(`/cars/${carId}`)
    } catch (error) {
        console.error(`Error deleting car with ID ${carId}:`, error)
        throw error
    }
}


import axios from 'axios'

const API_URL = 'http://localhost:8082/api/cars'

// Create axios instance with CORS configuration
const axiosInstance = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
    withCredentials: false // Important for CORS
})

// Add request interceptor for debugging
axiosInstance.interceptors.request.use(
    config => {
        console.log('[carService] Request:', config.method.toUpperCase(), config.url)
        return config
    },
    error => {
        console.error('[carService] Request error:', error)
        return Promise.reject(error)
    }
)

// Add response interceptor for debugging
axiosInstance.interceptors.response.use(
    response => {
        console.log('[carService] Response status:', response.status)
        return response
    },
    error => {
        console.error('[carService] Response error:', {
            status: error.response?.status,
            message: error.message,
            url: error.config?.url
        })
        return Promise.reject(error)
    }
)

export async function fetchCars() {
    try {
        const response = await axiosInstance.get('')
        return response.data.map(car => ({
            ...car,
            imageUrl: `${API_URL}/${car.carId}/image`
        }))
    } catch (error) {
        console.error('Error fetching cars:', error.message)
        throw new Error(error.response?.data?.message || 'Failed to fetch cars')
    }
}

export async function fetchAvailableCars(startDate, endDate) {
    try {
        console.log('[carService] Fetching available cars:', { startDate, endDate })

        const response = await axiosInstance.get('/available', {
            params: {
                startDate,
                endDate
            }
        })

        console.log('[carService] Available cars response:', response.data)

        return response.data.map(car => ({
            ...car,
            imageUrl: `${API_URL}/${car.carId}/image`
        }))
    } catch (error) {
        console.error('Error fetching available cars:', error.message)

        if (error.response) {
            // Server responded with error status
            throw new Error(error.response.data?.message || `Server error: ${error.response.status}`)
        } else if (error.request) {
            // Request was made but no response received
            throw new Error('No response from server. Check if backend is running on port 8082')
        } else {
            // Error in request setup
            throw new Error(error.message)
        }
    }
}

export async function fetchCarById(carId) {
    try {
        console.log('[carService] Fetching car:', carId)

        const response = await axiosInstance.get(`/${carId}`)

        const car = response.data
        return {
            ...car,
            imageUrl: `${API_URL}/${car.carId}/image`
        }
    } catch (error) {
        console.error(`Error fetching car with ID ${carId}:`, error.message)
        throw new Error(error.response?.data?.message || `Failed to fetch car ${carId}`)
    }
}

export async function deleteCar(carId) {
    try {
        console.log('[carService] Deleting car:', carId)

        await axiosInstance.delete(`/${carId}`)

        console.log('[carService] Car deleted successfully')
    } catch (error) {
        console.error(`Error deleting car with ID ${carId}:`, error.message)
        throw new Error(error.response?.data?.message || `Failed to delete car ${carId}`)
    }
}
import axios from 'axios'
import AuthService from './auth.js'

const api = axios.create({
    baseURL: 'http://localhost:8082/api',
})


api.interceptors.request.use(
    config => {
        const user = AuthService.getCurrentUser()
        if (user && user.token) {
            config.headers.Authorization = `Bearer ${user.token}`
            console.log('[carAdminService] Added Authorization header for:', config.url)
        }
        return config
    },
    error => {
        console.error('[carAdminService] Request error:', error)
        return Promise.reject(error)
    }
)


api.interceptors.response.use(
    response => {
        console.log('[carAdminService] Response received:', response.status)
        return response
    },
    error => {
        console.error('[carAdminService] Response error:', {
            status: error.response?.status,
            message: error.message,
            data: error.response?.data
        })

        if (error.response?.status === 401 || error.response?.status === 403) {
            console.error('[carAdminService] Authentication error - token may be expired')
        }

        return Promise.reject(error)
    }
)

export async function deleteCar(carId) {
    try {
        console.log('[carAdminService] Deleting car:', carId)
        await api.delete(`/cars/${carId}`)
        console.log('[carAdminService] Car deleted successfully')
    } catch (error) {
        if (error.response?.status === 409) {
            alert(`Cannot delete car "${carId}": it has related records.`)
        } else if (error.response?.status === 404) {
            alert(`Car "${carId}" not found.`)
        } else if (error.response?.status === 403) {
            alert('You do not have permission to delete cars. Admin access required.')
        } else {
            console.error(`Error deleting car with ID ${carId}:`, error)
            alert(`Unexpected error deleting car "${carId}".`)
        }
        throw error
    }
}

export const addCar = async (car, imageFile) => {
    try {
        console.log('[carAdminService] Adding new car:', car)

        const formData = new FormData()
        formData.append('car', new Blob([JSON.stringify(car)], { type: 'application/json' }))
        if (imageFile) {
            formData.append('image', imageFile)
            console.log('[carAdminService] Image file included')
        }

        const { data } = await api.post('/cars', formData, {
            headers: { 'Content-Type': 'multipart/form-data' },
        })

        console.log('[carAdminService] Car added successfully')
        return data
    } catch (error) {
        if (error.response?.status === 403) {
            throw new Error('You do not have permission to add cars. Admin access required.')
        }
        throw new Error(error.response?.data?.message || 'Failed to add car')
    }
}

export const fetchAllCars = async () => {
    try {
        console.log('[carAdminService] Fetching all cars')
        const { data } = await api.get('/cars')
        console.log('[carAdminService] Cars fetched successfully:', data.length, 'cars')
        return data
    } catch (error) {
        console.error('[carAdminService] Error fetching cars:', error)
        throw new Error(error.response?.data?.message || 'Failed to fetch cars')
    }
}
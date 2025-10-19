import AuthService from './auth.js'

const API_BASE_URL = 'http://localhost:8082/api'

// Helper function to get auth headers
function getAuthHeaders() {
    const user = AuthService.getCurrentUser()
    const headers = {
        'Content-Type': 'application/json',
    }

    if (user && user.token) {
        headers['Authorization'] = `Bearer ${user.token}`
        console.log('[bookingService] Added Authorization header')
    } else {
        console.warn('[bookingService] No token found in user object')
    }

    return headers
}

export const createBooking = async (bookingData) => {
    try {
        console.log('[bookingService] Creating booking:', bookingData)

        const response = await fetch(`${API_BASE_URL}/bookings`, {
            method: 'POST',
            headers: getAuthHeaders(),
            body: JSON.stringify(bookingData)
        })

        if (!response.ok) {
            const errorData = await response.json()
            console.error('[bookingService] Error response:', errorData)
            throw new Error(errorData.message || `Failed to create booking (${response.status})`)
        }

        const result = await response.json()
        console.log('[bookingService] Booking created successfully:', result)
        return result

    } catch (error) {
        console.error('[bookingService] Error creating booking:', error.message)
        throw error
    }
}

export const getAllBookings = async (userEmail = null) => {
    try {
        console.log('[bookingService] Fetching all bookings')

        // Build URL with optional email parameter
        const url = new URL(`${API_BASE_URL}/bookings`)
        if (userEmail) {
            url.searchParams.append('email', userEmail)
        }

        console.log('[bookingService] Request URL:', url.toString())
        console.log('[bookingService] Request headers:', getAuthHeaders())

        const response = await fetch(url.toString(), {
            method: 'GET',
            headers: getAuthHeaders()
        })

        if (!response.ok) {
            const errorData = await response.text()
            console.error('[bookingService] Error response status:', response.status)
            console.error('[bookingService] Error response body:', errorData)

            if (response.status === 403) {
                throw new Error('Unauthorized: Invalid or missing authentication token')
            } else if (response.status === 401) {
                throw new Error('Authentication required: Please log in again')
            }
            throw new Error(`Failed to fetch bookings (${response.status})`)
        }

        const bookings = await response.json()
        console.log('[bookingService] Bookings fetched successfully:', bookings)
        return bookings

    } catch (error) {
        console.error('[bookingService] Error fetching bookings:', error.message)
        throw error
    }
}

export const getBookingById = async (bookingId) => {
    try {
        console.log('[bookingService] Fetching booking:', bookingId)

        const response = await fetch(`${API_BASE_URL}/bookings/${bookingId}`, {
            method: 'GET',
            headers: getAuthHeaders()
        })

        if (!response.ok) {
            const errorData = await response.json()
            console.error('[bookingService] Error response:', errorData)
            throw new Error(errorData.message || `Failed to fetch booking (${response.status})`)
        }

        const booking = await response.json()
        console.log('[bookingService] Booking fetched successfully:', booking)
        return booking

    } catch (error) {
        console.error('[bookingService] Error fetching booking:', error.message)
        throw error
    }
}
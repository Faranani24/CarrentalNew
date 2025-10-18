const API_BASE_URL = 'http://localhost:8082/api';

export const createBooking = async (bookingData) => {
    try {
        const response = await fetch(`${API_BASE_URL}/bookings`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(bookingData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Failed to create booking on the server.');
        }

        const result = await response.json();
        return result;

    } catch (error) {
        console.error('Error creating booking:', error);
        throw error;
    }
};

export const getAllBookings = async (userEmail = null) => {
    try {
        // Build URL with optional email parameter
        const url = new URL(`${API_BASE_URL}/bookings`);
        if (userEmail) {
            url.searchParams.append('email', userEmail);
        }

        const response = await fetch(url.toString(), {
            method: 'GET',
            headers: {'Content-Type': 'application/json'}
        });

        if (!response.ok) {
            throw new Error('Failed to fetch bookings');
        }

        return await response.json();
    } catch (error) {
        console.error('Error fetching bookings:', error);
        throw error;
    }
};

export const getBookingById = async (bookingId) => {
    try {
        const response = await fetch(`${API_BASE_URL}/bookings/${bookingId}`, {
            method: 'GET',
            headers: {'Content-Type': 'application/json'}
        });

        if (!response.ok) {
            throw new Error('Failed to fetch booking');
        }

        return await response.json();
    } catch (error) {
        console.error('Error fetching booking:', error);
        throw error;
    }
};
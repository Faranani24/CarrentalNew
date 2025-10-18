const API_BASE_URL = 'http://localhost:8082/api';

export const createBooking = async (bookingData) => {
    try {
        const response = await fetch(`${API_BASE_URL}/bookings`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json',},
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

import api from './api';

export const createBooking = async (bookingData) => {
    try {
        const response = await api.post('/bookings', bookingData);
        return response.data;
    } catch (error) {
        console.error('Error creating booking:', error);
        const message = error.response?.data?.message || 'Failed to create booking on the server.';
        throw new Error(message);
    }
};

export const getAllBookings = async (userEmail = null) => {
    try {
        const response = await api.get('/bookings', {
            params: userEmail ? { email: userEmail } : {}
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching bookings:', error);
        throw new Error('Failed to fetch bookings');
    }
};

export const getBookingById = async (bookingId) => {
    try {
        const response = await api.get(`/bookings/${bookingId}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching booking:', error);
        throw new Error('Failed to fetch booking');
    }
};

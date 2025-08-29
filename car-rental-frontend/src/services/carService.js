import { api } from './api';

// Get all cars
export function fetchCars() {
    return api.get('/cars').then(res => res.data);
}

// Get car by ID
export function fetchCarById(id) {
    return api.get(`/cars/${id}`).then(res => res.data);
}

// Get by carId (string)
export function fetchCarByCarId(carId) {
    return api.get(`/cars/carId/${encodeURIComponent(carId)}`).then(res => res.data);
}

// Create car
export function createCar(car) {
    return api.post('/cars', car).then(res => res.data);
}

// Update car
export function updateCar(id, updates) {
    return api.put(`/cars/${id}`, updates).then(res => res.data);
}

// Delete by ID
export function deleteCar(id) {
    return api.delete(`/cars/${id}`).then(res => res.data);
}

// Delete by carId
export function deleteCarByCarId(carId) {
    return api.delete(`/cars/carId/${encodeURIComponent(carId)}`).then(res => res.data);
}

// Update status
export function updateCarStatus(id, status) {
    return api.patch(`/cars/${id}/status`, { status }).then(res => res.data);
}

// Create booking
export function createBooking(bookingDetails) {
    return api.post('/bookings', bookingDetails).then(res => res.data);
}

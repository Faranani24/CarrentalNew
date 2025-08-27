// Service for Car API calls.
// Assumes `api` is a preconfigured Axios instance (base URL or proxy set in Vite).
import { api } from './api';

// Get all cars
export function fetchCars() {
    return api.get('/api/cars').then(res => res.data);
}

// Get a single car by its numeric `id`
export function fetchCarById(id) {
    return api.get(`/api/cars/${id}`).then(res => res.data);
}

// (If backend supports) Get by business carId (string)
export function fetchCarByCarId(carId) {
    return api.get(`/api/cars/carId/${encodeURIComponent(carId)}`).then(res => res.data);
}

// Create a new car (payload matches backend CarDto / entity fields)
export function createCar(car) {
    return api.post('/api/cars', car).then(res => res.data);
}

// Update car by id (partial or full depending on backend controller)
export function updateCar(id, updates) {
    return api.put(`/api/cars/${id}`, updates).then(res => res.data);
}

// Delete by id
export function deleteCar(id) {
    return api.delete(`/api/cars/${id}`).then(res => res.data);
}

// Delete by business carId (if endpoint exists)
export function deleteCarByCarId(carId) {
    return api.delete(`/api/cars/carId/${encodeURIComponent(carId)}`).then(res => res.data);
}

// Update only status (if PATCH supported)
export function updateCarStatus(id, status) {
    return api.patch(`/api/cars/${id}/status`, { status }).then(res => res.data);
}
import { api } from '/src/services/api.js';

const MOCK_CARS = [
    {
        id: '1',
        make: 'BMW',
        model: 'X5 M Competition',
        year: 2024,
        dailyRate: 500.00,
        licensePlate: 'BMW-001',
        imageUrl: 'https://i.ibb.co/N6hmTKmX/2024-bmw-x5-m-competition.png',
        description: 'A luxurious and high-performance SUV with a twin-turbo V8 engine and cutting-edge technology.',
        features: ['All-wheel drive', 'Heated seats', 'Panoramic sunroof', 'GPS Navigation'],
        status: 'AVAILABLE',
        location: 'Johannesburg Airport'
    },
    {
        id: '2',
        make: 'Toyota',
        model: 'Corolla',
        year: 2023,
        dailyRate: 250.00,
        licensePlate: 'T-COR-23',
        imageUrl: 'https://i.ibb.co/Fbk8ySFq/Toyota.png',
        description: 'The Toyota Corolla is a reliable and fuel-efficient compact car, perfect for city travel.',
        features: ['Fuel-efficient engine', 'Bluetooth connectivity', 'Backup camera'],
        status: 'AVAILABLE',
        location: 'Cape Town City'
    },
    {
        id: '3',
        make: 'Honda',
        model: 'Civic',
        year: 2023,
        dailyRate: 300.00,
        licensePlate: 'H-CIV-23',
        imageUrl: 'https://i.ibb.co/TBQJpVTV/honda.png',
        description: 'A stylish and practical sedan known for its smooth ride and spacious interior.',
        features: ['Apple CarPlay', 'Lane departure warning', 'Adaptive cruise control'],
        status: 'AVAILABLE',
        location: 'Johannesburg Airport'
    }
];

export async function fetchCars(params, config) {
    console.log('Fetching mock cars with params:', params);
    await new Promise(resolve => setTimeout(resolve, 500));

    let filteredCars = MOCK_CARS;

    if (params) {
        if (params.location) {
            const searchTerm = params.location.toLowerCase();
            filteredCars = filteredCars.filter(car =>
                car.location.toLowerCase().includes(searchTerm)
            );
        }
    }

    return filteredCars;
}

export async function fetchCarById(id) {
    await new Promise(resolve => setTimeout(resolve, 500));
    const car = MOCK_CARS.find(c => c.id === id);
    if (!car) {
        throw new Error('Car not found');
    }
    return car;
}

// These functions will remain unused for this setup, but are kept for future use.
export function fetchCarByCarId(carId) {
    return api.get(`/cars/carId/${encodeURIComponent(carId)}`).then(res => res.data);
}

export function createCar(car) {
    return api.post('/cars', car).then(res => res.data);
}

export function updateCar(id, updates) {
    return api.put(`/cars/${id}`, updates).then(res => res.data);
}

export function deleteCar(id) {
    return api.delete(`/cars/${id}`).then(res => res.data);
}

export function deleteCarByCarId(carId) {
    return api.delete(`/cars/carId/${encodeURIComponent(carId)}`).then(res => res.data);
}

export function updateCarStatus(id, status) {
    return api.patch(`/cars/${id}/status`, { status }).then(res => res.data);
}

/**
 * REAL API implementation - calls your Spring Boot backend
 * Replaces the previous mock implementation
 */
export async function bookCar(bookingDetails) {
    console.log('Creating real booking with details:', bookingDetails);

    try {
        // Transform the Vue form data to match your BookingRequest DTO
        const bookingData = {
            carId: bookingDetails.carId,
            from: bookingDetails.from,    // Keep as string - backend will parse
            to: bookingDetails.to,        // Keep as string - backend will parse
            fullName: bookingDetails.fullName,
            email: bookingDetails.email,
            phone: bookingDetails.phone || null
        };

        const response = await api.post('/bookings', bookingData);

        // Transform the response to match what your Vue app expects
        return {
            bookingId: response.data.bookingId,
            status: response.data.status,
            totalCost: response.data.totalCost,
            startDate: response.data.startDate,
            endDate: response.data.endDate,
            customer: response.data.customer,
            vehicleId: response.data.vehicleId
        };

    } catch (error) {
        console.error('Booking API call failed:', error);

        // Handle different types of errors
        if (error.response) {
            // Server responded with error status
            const message = error.response.data?.message ||
                error.response.data ||
                `Server error: ${error.response.status}`;
            throw new Error(message);
        } else if (error.request) {
            // Network error
            throw new Error('Unable to connect to booking service. Please check your connection.');
        } else {
            // Other error
            throw new Error(error.message || 'An unexpected error occurred during booking.');
        }
    }
}
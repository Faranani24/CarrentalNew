import { api } from '/src/services/api.js';

const MOCK_CARS = [
    {
        id: '1',
        make: 'BMW',
        model: 'X5 M Competition',
        year: 2024,
        dailyRate: 150.00,
        licensePlate: 'BMW-001',
        imageUrl: 'https://i.ibb.co/N6hmTKmX/2024-bmw-x5-m-competition.png',
        description: 'A luxurious and high-performance SUV with a twin-turbo V8 engine and cutting-edge technology.',
        features: ['All-wheel drive', 'Heated seats', 'Panoramic sunroof', 'GPS Navigation'],
        status: 'AVAILABLE',
        location: 'Johannesburg Airport' // <-- Added location property
    },
    {
        id: '2',
        make: 'Toyota',
        model: 'Corolla',
        year: 2023,
        dailyRate: 75.00,
        licensePlate: 'T-COR-23',
        imageUrl: 'https://i.ibb.co/Fbk8ySFq/Toyota.png',
        description: 'The Toyota Corolla is a reliable and fuel-efficient compact car, perfect for city travel.',
        features: ['Fuel-efficient engine', 'Bluetooth connectivity', 'Backup camera'],
        status: 'AVAILABLE',
        location: 'Cape Town City' // <-- Added location property
    },
    {
        id: '3',
        make: 'Honda',
        model: 'Civic',
        year: 2023,
        dailyRate: 85.00,
        licensePlate: 'H-CIV-23',
        imageUrl: 'https://i.ibb.co/TBQJpVTV/honda.png',
        description: 'A stylish and practical sedan known for its smooth ride and spacious interior.',
        features: ['Apple CarPlay', 'Lane departure warning', 'Adaptive cruise control'],
        status: 'AVAILABLE',
        location: 'Johannesburg Airport' // <-- Added location property
    }
];

export async function fetchCars(params, config) {
    console.log('Fetching mock cars with params:', params);
    await new Promise(resolve => setTimeout(resolve, 500));

    let filteredCars = MOCK_CARS;

    if (params) {
        if (params.location) {
            const searchTerm = params.location.toLowerCase();
            // Now we correctly filter the cars by their location property
            filteredCars = filteredCars.filter(car =>
                car.location.toLowerCase().includes(searchTerm)
            );
        }

        // We can add date-based filtering here if needed.
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
 * MOCK implementation of the booking function.
 * This function is now correctly named `bookCar` and exported.
 */
export async function bookCar(bookingDetails) {
    console.log('Creating mock booking with details:', bookingDetails);
    await new Promise(resolve => setTimeout(resolve, 1000));
    // Simulate a successful booking with a unique ID
    return {
        bookingId: `mock-booking-${Date.now()}`,
        status: 'confirmed',
        ...bookingDetails
    };
}
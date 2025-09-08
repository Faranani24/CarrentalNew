const express = require('express');
const multer = require('multer');
const cors = require('cors');

const app = express();
const port = 8082;

// In a real application, you would use a database.
// For this mock, we'll store cars in an in-memory array.
const cars = [];

// Middleware setup
app.use(cors()); // Allow cross-origin requests from your Vue app
app.use(express.json()); // For parsing application/json




// Configure Multer for file uploads
const upload = multer();

// Middleware to simulate authentication
// This checks for the mock token from AuthService
const authMiddleware = (req, res, next) => {
    const authHeader = req.headers.authorization;
    if (authHeader && authHeader.startsWith('Bearer your-mock-auth-token')) {
        next(); // Token is valid, proceed
    } else {
        res.status(401).send('Unauthorized: Invalid or missing token.');
    }
};

// GET endpoint to fetch all cars
app.get('/api/cars', authMiddleware, (req, res) => {
    console.log('GET request received for /api/cars. Returning all cars.');
    res.json(cars);
});

// POST endpoint to add a new car
app.post('/api/cars', authMiddleware, upload.single('image'), (req, res) => {
    console.log('POST request received for /api/cars.');

    if (!req.body.car || !req.file) {
        return res.status(400).send('Bad Request: Car data or image file missing.');
    }

    try {
        const carData = JSON.parse(req.body.car);
        const newCar = {
            ...carData,
            id: cars.length + 1, // Simple ID generation
            image: req.file.buffer // Store image as a buffer
        };
        cars.push(newCar);
        console.log('New car added:', newCar.make, newCar.model);
        res.status(201).json({ message: 'Car added successfully!', car: newCar });
    } catch (error) {
        console.error('Failed to parse car data:', error);
        res.status(400).send('Invalid car data format.');
    }
});

// Start the server
app.listen(port, () => {
    console.log(`Mock Car API listening at http://localhost:${port}`);
});

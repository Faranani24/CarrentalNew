const express = require('express');
const multer = require('multer');
const cors = require('cors');

const app = express();
const port = 8082;


const cars = [];


app.use(cors());
app.use(express.json());






const upload = multer();



const authMiddleware = (req, res, next) => {
    const authHeader = req.headers.authorization;
    if (authHeader && authHeader.startsWith('Bearer your-mock-auth-token')) {
        next(); // Token is valid, proceed
    } else {
        res.status(401).send('Unauthorized: Invalid or missing token.');
    }
};


app.get('/api/cars', authMiddleware, (req, res) => {
    console.log('GET request received for /api/cars. Returning all cars.');
    res.json(cars);
});


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


app.listen(port, () => {
    console.log(`Mock Car API listening at http://localhost:${port}`);
});

import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8082/api',
});
export async function deleteCar(carId) {
    try {
        await api.delete(`/cars/${carId}`);
    } catch (error) {
        if (error.response?.status === 409) {
            alert(`Cannot delete car "${carId}": it has related records.`);
        } else if (error.response?.status === 404) {
            alert(`Car "${carId}" not found.`);
        } else {
            console.error(`Error deleting car with ID ${carId}:`, error);
            alert(`Unexpected error deleting car "${carId}".`);
        }
        throw error;
    }
}



export const addCar = async (car, imageFile) => {
    const formData = new FormData();
    formData.append('car', new Blob([JSON.stringify(car)], { type: 'application/json' }));
    if (imageFile) formData.append('image', imageFile);

    const { data } = await api.post('/cars', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
    });
    return data;
};

export const fetchAllCars = async () => {
    const { data } = await api.get('/cars');
    return data;
};

// car-rental-frontend/src/services/api.js
import axios from 'axios';

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8082/api',
  headers: {
    'Content-Type': 'application/json',
  },
});
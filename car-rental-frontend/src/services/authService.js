import axios from 'axios';

export class AuthService {
    constructor() {
        this.storageKey = 'car_rental_user';
        this.apiUrl = 'http://localhost:8082/api/auth';
    }

    async login(credentials) {
        const response = await axios.post(`${this.apiUrl}/login`, credentials);
        const user = response.data;
        const userSession = { ...user, isAuthenticated: true };
        localStorage.setItem(this.storageKey, JSON.stringify(userSession));
        return userSession;
    }

    async signup(userData) {
        const response = await axios.post(`${this.apiUrl}/signup`, userData);
        return response.data;
    }

    getCurrentUser() {
        const session = localStorage.getItem(this.storageKey);
        return session ? JSON.parse(session) : null;
    }

    logout() {
        localStorage.removeItem(this.storageKey);
    }
}

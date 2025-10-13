import axios from 'axios';

export class AuthService {
    constructor() {
        this.storageKey = 'car_rental_user';
        this.apiUrl = 'http://localhost:8082/api/auth';
    }

    async login(credentials) {
        try {
            const response = await axios.post(`${this.apiUrl}/login`, credentials);
            const { token, user } = response.data;

            const userSession = { ...user, token, isAuthenticated: true };
            localStorage.setItem(this.storageKey, JSON.stringify(userSession));
            return userSession;
        } catch (error) {
            console.error('Login failed:', error);
            throw error;
        }
    }

    async signup(userData) {
        try {
            const response = await axios.post(`${this.apiUrl}/signup`, userData);
            return response.data; // return new user from backend
        } catch (error) {
            console.error('Signup failed:', error);
            throw error;
        }
    }

    getCurrentUser() {
        const userSession = localStorage.getItem(this.storageKey);
        return userSession ? JSON.parse(userSession) : null;
    }

    logout() {
        localStorage.removeItem(this.storageKey);
    }

    getAuthToken() {
        const user = this.getCurrentUser();
        return user ? user.token : null;
    }
}

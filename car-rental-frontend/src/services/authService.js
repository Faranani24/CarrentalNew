import axios from 'axios';

export class AuthService {
    constructor() {
        this.storageKey = 'auth2_user';
        this.apiUrl = 'http://localhost:8082/api/auth';
    }

    async login(credentials) {
        const response = await axios.post(`${this.apiUrl}/login`, credentials);
        const user = response.data;

        if (user.token) {
            localStorage.setItem('token', user.token);
            console.log('authService.js - Token stored:', user.token.substring(0, 20) + '...');
        }

        const userSession = { ...user, isAuthenticated: true };
        localStorage.setItem(this.storageKey, JSON.stringify(userSession));
        console.log('authService.js - User stored in:', this.storageKey);

        return userSession;
    }

    async signup(userData) {
        const response = await axios.post(`${this.apiUrl}/signup`, userData);

        if (response.data.token) {
            localStorage.setItem('token', response.data.token);
            console.log('authService.js - Token stored:', response.data.token.substring(0, 20) + '...');
        }

        return response.data;
    }

    getCurrentUser() {
        const session = localStorage.getItem(this.storageKey);
        if (session) {
            console.log('authService.js - getCurrentUser from:', this.storageKey);
        }
        return session ? JSON.parse(session) : null;
    }

    logout() {
        localStorage.removeItem(this.storageKey);
        localStorage.removeItem('token');
        console.log('authService.js - User and token removed');
    }
}

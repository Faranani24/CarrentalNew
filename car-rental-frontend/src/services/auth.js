import axios from 'axios';

const API_URL = 'http://localhost:8082/api/auth/';
const NAMESPACE = 'auth1_';

class AuthService {
    async signup(userData) {
        const headers = {
            'Content-Type': 'application/json'
        };
        try {
            const response = await axios.post(API_URL + 'signup', userData, { headers });
            if (response.data && response.data.token) {
                localStorage.setItem('token', response.data.token);

                const userSession = {
                    ...response.data.user,
                    token: response.data.token
                };
                localStorage.setItem(NAMESPACE + 'user', JSON.stringify(userSession));
                console.log('auth.js - Token stored:', response.data.token.substring(0, 20) + '...');
            }
            return response.data;
        } catch (error) {
            console.error("Signup failed:", error);
            throw error;
        }
    }

    async login(userData) {
        const headers = {
            'Content-Type': 'application/json'
        };
        try {
            const response = await axios.post(API_URL + 'login', userData, { headers });
            if (response.data.token) {
                localStorage.setItem('token', response.data.token); // ✅ Shared key

                const userSession = {
                    ...response.data.user,
                    token: response.data.token
                };
                localStorage.setItem(NAMESPACE + 'user', JSON.stringify(userSession)); // ✅ Namespaced
                console.log('auth.js - User stored:', userSession);
                console.log('auth.js - Token stored:', response.data.token.substring(0, 20) + '...');
            }
            return response.data;
        } catch (error) {
            console.error("Login failed:", error);
            throw error;
        }
    }

    logout() {
        localStorage.removeItem(NAMESPACE + 'user');
        localStorage.removeItem('token');
        console.log('auth.js - User and token removed');
    }

    getCurrentUser() {
        const userStr = localStorage.getItem(NAMESPACE + 'user');
        if (userStr) {
            try {
                const user = JSON.parse(userStr);
                console.log('auth.js - getCurrentUser returned:', user);
                return user;
            } catch (e) {
                console.error('Error parsing user from localStorage:', e);
                return null;
            }
        }
        console.log('auth.js - No user in localStorage');
        return null;
    }
}

export default new AuthService();

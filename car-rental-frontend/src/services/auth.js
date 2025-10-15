import axios from 'axios';

const API_URL = 'http://localhost:8082/api/auth/';

class AuthService {
    async signup(userData) {
        const headers = {
            'Content-Type': 'application/json'
        };
        try {
            const response = await axios.post(API_URL + 'signup', userData, { headers });
            if (response.data && response.data.token) {
                // Store both user and token
                const userSession = {
                    ...response.data.user,
                    token: response.data.token
                };
                localStorage.setItem('user', JSON.stringify(userSession));
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
                // Store both user and token
                const userSession = {
                    ...response.data.user,
                    token: response.data.token
                };
                localStorage.setItem('user', JSON.stringify(userSession));
                console.log('User stored in localStorage:', userSession);
            }
            return response.data;
        } catch (error) {
            console.error("Login failed:", error);
            throw error;
        }
    }

    logout() {
        localStorage.removeItem('user');
        console.log('User removed from localStorage');
    }

    getCurrentUser() {
        const userStr = localStorage.getItem('user');
        if (userStr) {
            try {
                const user = JSON.parse(userStr);
                console.log('getCurrentUser returned:', user);
                return user;
            } catch (e) {
                console.error('Error parsing user from localStorage:', e);
                return null;
            }
        }
        console.log('No user in localStorage');
        return null;
    }
}

export default new AuthService();
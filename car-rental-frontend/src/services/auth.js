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
                localStorage.setItem('user', JSON.stringify(response.data));
            }
            return response.data;
        } catch (error) {
            console.error("Signup failed:", error);
            throw error;
        }
    }

    login(userData) {
        const headers = {
            'Content-Type': 'application/json'
        };
        return axios.post(API_URL + 'login', userData, { headers })
            .then(response => {
                if (response.data.token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    // --- THIS IS THE NEW FUNCTION ---
    // Add this method to get the current user from localStorage
    getCurrentUser() {
        const user = localStorage.getItem('user');
        return user ? JSON.parse(user) : null;
    }
}

export default new AuthService();
// services/auth.js - Authentication service
export class AuthService {
    constructor() {
        this.storageKey = 'car_rental_user';
    }

    // Login user and store locally
    login(userData) {
        try {
            const userSession = {
                id: userData.id,
                email: userData.email,
                username: userData.username,
                firstName: userData.firstName,
                lastName: userData.lastName,
                loginTime: new Date().toISOString(),
                isAuthenticated: true,
                // Adding a placeholder token to allow other services to function
                token: 'your-mock-auth-token'



            };

            localStorage.setItem(this.storageKey, JSON.stringify(userSession));
            return true;
        } catch (error) {
            console.error('Error storing user session:', error);
            return false;
        }
    }

    // Sign up user and store locally
    signup(userData) {
        try {
            // In a real app, you'd send this to your Java backend first
            const newUser = {
                id: this.generateUserId(),
                email: userData.email,
                username: userData.username,
                firstName: userData.firstName,
                lastName: userData.lastName,
                createdAt: new Date().toISOString(),
                loginTime: new Date().toISOString(),
                isAuthenticated: true,
                // Adding a placeholder token for newly signed up users
                token: 'your-mock-auth-token'
            };

            // Store existing users
            const existingUsers = this.getAllUsers();
            existingUsers.push(newUser);
            localStorage.setItem('car_rental_all_users', JSON.stringify(existingUsers));

            // Set current user session
            localStorage.setItem(this.storageKey, JSON.stringify(newUser));
            return newUser;
        } catch (error) {
            console.error('Error creating user session:', error);
            return null;
        }
    }

    // Get current user
    getCurrentUser() {
        try {
            const userSession = localStorage.getItem(this.storageKey);
            return userSession ? JSON.parse(userSession) : null;
        } catch (error) {
            console.error('Error retrieving user session:', error);
            return null;
        }
    }

    // Check if user is authenticated
    isAuthenticated() {
        const user = this.getCurrentUser();
        return user && user.isAuthenticated;
    }

    // Get the authentication token
    getAuthToken() {
        const user = this.getCurrentUser();
        return user ? user.token : null;
    }

    // Logout user
    logout() {
        try {
            localStorage.removeItem(this.storageKey);
            return true;
        } catch (error) {
            console.error('Error removing user session:', error);
            return false;
        }
    }

    // Update user data
    updateUser(updatedData) {
        try {
            const currentUser = this.getCurrentUser();
            if (currentUser) {
                const updatedUser = { ...currentUser, ...updatedData };
                localStorage.setItem(this.storageKey, JSON.stringify(updatedUser));
                return updatedUser;
            }
            return null;
        } catch (error) {
            console.error('Error updating user session:', error);
            return null;
        }
    }

    // Helper methods
    generateUserId() {
        return 'user_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
    }

    getAllUsers() {
        try {
            const users = localStorage.getItem('car_rental_all_users');
            return users ? JSON.parse(users) : [];
        } catch (error) {
            return [];
        }
    }

    // Check if email already exists (for signup validation)
    emailExists(email) {
        const users = this.getAllUsers();
        return users.some(user => user.email.toLowerCase() === email.toLowerCase());
    }
}

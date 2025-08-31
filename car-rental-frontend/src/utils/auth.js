// src/utils/auth.js
export function isAuthenticated() {
    return !!localStorage.getItem('token'); // assuming token is saved on login
}

export function saveToken(token) {
    localStorage.setItem('token', token);
}

export function getToken() {
    return localStorage.getItem('token');
}

export function logout() {
    localStorage.removeItem('token');
}

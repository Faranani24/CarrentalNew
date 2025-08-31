// src/utils/auth.js
import axios from 'axios';

const TOKEN_KEY = 'auth_token';

export function saveToken(token) {
  localStorage.setItem(TOKEN_KEY, token);
}

export function getToken() {
  return localStorage.getItem(TOKEN_KEY);
}

export function isAuthenticated() {
  return !!getToken();
}

export function logout() {
  localStorage.removeItem(TOKEN_KEY);
}

// Login API call
export async function loginUser(email, password) {
  const res = await axios.post('/api/auth/login', { email, password });
  if (res.data.token) {
    saveToken(res.data.token);
  }
  return res.data;
}

// Signup API call
export async function signupUser(userData) {
  const res = await axios.post('/api/auth/signup', userData);
  return res.data;
}

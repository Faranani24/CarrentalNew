// src/utils/api.js
import axios from 'axios';

const API_BASE = 'http://localhost:8080/api';

// Admin APIs
export const getAdmins = () => axios.get(`${API_BASE}/admins`);
export const addAdmin = (data) => axios.post(`${API_BASE}/admins`, data);
export const updateAdmin = (id, data) => axios.put(`${API_BASE}/admins/${id}`, data);
export const deleteAdmin = (id) => axios.delete(`${API_BASE}/admins/${id}`);

// Service APIs
export const getServices = () => axios.get(`${API_BASE}/services`);
export const addService = (data) => axios.post(`${API_BASE}/services`, data);
export const updateService = (id, data) => axios.put(`${API_BASE}/services/${id}`, data);
export const deleteService = (id) => axios.delete(`${API_BASE}/services/${id}`);

// Car APIs (if needed)
export const getCars = () => axios.get(`${API_BASE}/cars`);
export const getCarById = (id) => axios.get(`${API_BASE}/cars/${id}`);

// src/utils/api.js
import axios from 'axios';

const API_BASE = 'http://localhost:8080/api';

export const getAdmins = () => axios.get(`${API_BASE}/admins`);
export const addAdmin = (data) => axios.post(`${API_BASE}/admins`, data);
export const deleteAdmin = (id) => axios.delete(`${API_BASE}/admins/${id}`);

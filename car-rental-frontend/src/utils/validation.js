// src/utils/validation.js
export const isEmail = (email) => /\S+@\S+\.\S+/.test(email);
export const isNotEmpty = (str) => str && str.trim() !== '';

import axios from 'axios';

export const api = axios.create({
  baseURL: process.env.NODE_ENV === 'production'
    ? 'https://playfolio-hs4h.onrender.com'
    : 'http://localhost:5000'
});

export const igdbApi = axios.create({
  baseURL: process.env.NODE_ENV === 'production'
    ? 'https://api.igdb.com'
    : ''
});
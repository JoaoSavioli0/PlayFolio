import axios from 'axios';

export const api = axios.create({
  baseURL: process.env.NODE_ENV === 'production'
    ? 'https://sua-api-render.com'
    : 'http://localhost:5000'
});

export const igdbApi = axios.create({
  baseURL: process.env.NODE_ENV === 'production'
    ? 'https://api.igdb.com'
    : ''
});
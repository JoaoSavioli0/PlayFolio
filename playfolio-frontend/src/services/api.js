import axios from 'axios';

export const api = axios.create({
  baseURL: process.env.VUE_APP_API_URL
});

export const igdbApi = axios.create({
  baseURL: process.env.NODE_ENV === 'production'
    ? 'https://api.igdb.com'
    : ''
});
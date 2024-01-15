import axios from 'axios';

const baseUrl = process.env.BASE_URL;

export const defaultInstance = axios.create({
    baseURL: baseUrl,
    headers: {
        'Content-Type': 'application/json',
    },
});

defaultInstance.interceptors.request.use();
defaultInstance.interceptors.response.use();

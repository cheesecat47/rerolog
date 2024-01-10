import axios from 'axios';

const baseUrl = process.env.BASE_URL;

export const authInstance = axios.create({
    baseURL: baseUrl,
    headers: {
        'Content-Type': 'application/json',
    },
});

authInstance.interceptors.request.use();
authInstance.interceptors.response.use();

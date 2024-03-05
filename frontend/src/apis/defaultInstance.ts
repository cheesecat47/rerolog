import axios from 'axios';
import { serverURL } from 'constants/url';

<<<<<<< HEAD:src/apis/defaultInstance.ts
const baseUrl = import.meta.env.VITE_BASE_URL;
=======
const baseUrl = serverURL;
>>>>>>> rerolog/main:frontend/src/apis/defaultInstance.ts

export const defaultInstance = axios.create({
    baseURL: baseUrl,
    headers: {
        'Content-Type': 'application/json',
    },
});

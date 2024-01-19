import axios from 'axios';
import { getAccessToken } from 'utils/localStorage';

const baseUrl = process.env.REACT_APP_BASE_URL;

export const authInstance = axios.create({
    baseURL: baseUrl,
    headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${getAccessToken()}`,
    },
});

authInstance.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        // @TODO: 401 에러 처리
    }
);

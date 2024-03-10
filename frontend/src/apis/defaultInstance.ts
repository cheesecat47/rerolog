
import axios from 'axios';
import { serverURL } from 'constants/url';

const baseUrl = serverURL;

export const defaultInstance = axios.create({
    baseURL: baseUrl,
    headers: {
        'Content-Type': 'application/json',
    },
});

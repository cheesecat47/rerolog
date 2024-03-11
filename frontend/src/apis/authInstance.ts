import { getAccessToken, getRefreshToken } from '@/utils/localStorage';
import axios, { AxiosError, AxiosResponse } from 'axios';

const baseUrl = import.meta.env.VITE_BASE_URL;
    
let retry: boolean = false;

type refreshResponseType = {
    userId: 'string';
    accessToken: 'string';
    refreshToken: 'string';
};

const authInstance = axios.create({
    baseURL: baseUrl,
    headers: {
        'Content-Type': 'application/json',
        [`Authorization`]: `Bearer ${getAccessToken()}`,
    },
});

authInstance.interceptors.response.use(
    (response: AxiosResponse) => response,
    async (error: AxiosError) => {
        const originalRequest = error.config;

        if (!originalRequest || !error.response || retry) {
            return;
        }

        if (error.response.status === 401 && !retry) {
            retry = true;

            try {
                const refreshToken: string = getRefreshToken();

                const tokenRefreshResponse: AxiosResponse<refreshResponseType> =
                    await authInstance.post('user/refresh', {
                        refreshToken,
                    });

                const newAccessToken = tokenRefreshResponse.data.accessToken;
                originalRequest.headers['Authorization'] =
                    `Bearer ${newAccessToken}`;

                retry = false;

                return authInstance(originalRequest);
            } catch (refreshError) {
                window.location.href = '/login';
                return Promise.reject(refreshError);
            }
        }
        return Promise.reject(error);
    }
);

export { authInstance };

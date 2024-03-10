
/* eslint-disable */
import axios, { AxiosError, AxiosResponse } from 'axios';
import { serverURL } from 'constants/url';
import { getAccessToken } from 'utils/localStorage';

const baseUrl = serverURL;

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
            window.location.href = '/login';
            console.log('test');
            return;
        }

        if (error.response.status === 401 && !retry) {
            retry = true;

            try {
                const refreshToken: string = 'test';

                const tokenRefreshResponse: AxiosResponse<refreshResponseType> =
                    await authInstance.post('/user/refresh', {
                        refreshToken,
                    });

                const newAccessToken = tokenRefreshResponse.data.accessToken;
                originalRequest.headers['Authorization'] =
                    `Bearer ${newAccessToken}`;

                retry = false;

                return authInstance(originalRequest);
            } catch (refreshError) {
                console.log('에러', refreshError);
                window.location.href = '/login';
                return Promise.reject(refreshError);
            }
        }
        return Promise.reject(error);
    }
);

export { authInstance };

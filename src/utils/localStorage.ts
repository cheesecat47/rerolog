const accessTokenKey = 'ML_accessToken';
const refreshTokenKey = 'ML_refreshToken';

export const getAccessToken = (): string | null => {
    return localStorage.getItem(accessTokenKey) || null;
};

export const getRefreshToken = (): string | null => {
    return localStorage.getItem(refreshTokenKey) || null;
};

export const setAccessToken = (value: string) => {
    localStorage.setItem(accessTokenKey, value);
};

export const setRefreshToken = (value: string) => {
    localStorage.setItem(refreshTokenKey, value);
};

export const deleteAccessToken = () => {
    localStorage.setItem(accessTokenKey, '');
};

export const deleteRefreshToken = () => {
    localStorage.setItem(refreshTokenKey, '');
};

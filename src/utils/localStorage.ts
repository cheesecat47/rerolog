const isLoginKey = 'ML_isLogin';
const userIdKey = 'ML_userId';
const accessTokenKey = 'ML_accessToken';
const refreshTokenKey = 'ML_refreshToken';

export const getIsLogin = (): boolean => {
    return Boolean(localStorage.getItem(isLoginKey));
};

export const getUserId = (): string => {
    return localStorage.getItem(userIdKey) || '';
};

export const getAccessToken = (): string => {
    return localStorage.getItem(accessTokenKey) || '';
};

export const getRefreshToken = (): string => {
    return localStorage.getItem(refreshTokenKey) || '';
};

export const setIsLogin = (value: boolean) => {
    localStorage.setItem(isLoginKey, String(value));
};

export const setUserId = (value: string) => {
    localStorage.setItem(userIdKey, value);
};

export const setAccessToken = (value: string) => {
    localStorage.setItem(accessTokenKey, value);
};

export const setRefreshToken = (value: string) => {
    localStorage.setItem(refreshTokenKey, value);
};

export const deleteIsLogin = () => {
    localStorage.removeItem(isLoginKey);
};

export const deleteUserId = () => {
    localStorage.removeItem(userIdKey);
};

export const deleteAccessToken = () => {
    localStorage.removeItem(accessTokenKey);
};

export const deleteRefreshToken = () => {
    localStorage.removeItem(refreshTokenKey);
};

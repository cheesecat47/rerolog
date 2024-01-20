const isLoginKey = 'ML_isLogin';
const userIdKey = 'ML_userId';
const accessTokenKey = 'ML_accessToken';
const refreshTokenKey = 'ML_refreshToken';

export const getIsLogin = (): boolean | null => {
    return Boolean(localStorage.getItem(isLoginKey)) || null;
};

export const getUserId = (): string | null => {
    return localStorage.getItem(userIdKey) || null;
};

export const getAccessToken = (): string | null => {
    return localStorage.getItem(accessTokenKey) || null;
};

export const getRefreshToken = (): string | null => {
    return localStorage.getItem(refreshTokenKey) || null;
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

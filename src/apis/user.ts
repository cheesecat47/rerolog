import { loginRequest, logoutRequest } from 'types/api/user';
import { authInstance } from './authInstance';
import { defaultInstance } from './defaultInstance';

const login = async ({ userId, userPw }: loginRequest) => {
    const result = await defaultInstance
        .post('/user/login', {
            userId,
            userPw,
        })
        .then((res) => {
            console.log('로그인 성공', res.data.data);
            return res.data.data;
        })
        .catch((err) => {
            console.log('로그인 실패', err);
            alert(err.response.data.message);
            return null;
        });

    return result;
};

const logout = async ({ userId, accessToken }: logoutRequest) => {
    const result = await authInstance
        .post('/user/logout', {
            userId,
            accessToken,
        })
        .then((res) => {
            console.log('로그아웃 성공', res);
            return true;
        })
        .catch((err) => {
            console.log('로그아웃 실패', err);
            return false;
        });

    return result;
};

export { login, logout };

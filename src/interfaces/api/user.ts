import { IUser } from 'interfaces/model';
import { ApiResponse } from './api';

export type getUserInfoReqest = {
    userId: string;
};

export type getUserInfoResponse = ApiResponse<IUser>;

export type loginRequest = {
    userId: string;
    userPw: string;
};

export type loginResponse = {
    userId: string;
    accessToken: string;
    refreshToken: string;
};

export type logoutRequest = {
    userId: string | null;
    accessToken: string | null;
};

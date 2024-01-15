import { IUser } from 'interfaces/model';
import { ApiResponse } from './api';

export type getUserInfoReqest = {
    userId: string;
};

export type getUserInfoResponse = ApiResponse<IUser>;

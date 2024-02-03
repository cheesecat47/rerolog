import { IBlog } from 'types/model';
import { ApiResponse } from './api';

export type getBlogInfoReqest = {
    userId: string;
};

export type getBlogInfoResponse = ApiResponse<IBlog>;

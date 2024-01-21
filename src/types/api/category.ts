import { CategoryListType } from 'types/model';
import { ApiResponse } from './api';

export type getCategoryRequest = {
    userId: string;
};

export type getCategoryResponse = ApiResponse<CategoryListType | []>;

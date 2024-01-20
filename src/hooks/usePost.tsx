import { useQuery } from '@tanstack/react-query';
import { defaultInstance } from 'apis/defaultInstance';
import { QUERY_KEY } from 'constants/queryKeys';
import {
    getPostDetailRequest,
    getPostDetailResponse,
    getPostListRequest,
    getPostListResponse,
} from 'interfaces/api/post';

export const usePost = () => {
    const staleTime = Number(process.env.REACT_APP_STALE_TIME);

    const getUserPostList = ({ userId }: getPostListRequest) => {
        return useQuery({
            queryKey: [QUERY_KEY.POST, { userId }],
            queryFn: async () => {
                const response: getPostListResponse = await defaultInstance
                    .get(`/post?userId=${userId}`)
                    .then((res) => res.data);

                return response.data;
            },
            staleTime,
        });
    };

    const getUserCategoryPostList = ({
        userId,
        categoryId,
    }: getPostListRequest) => {
        return useQuery({
            queryKey: [QUERY_KEY.POST, { userId, categoryId }],
            queryFn: async () => {
                const response: getPostDetailResponse = await defaultInstance
                    .get(`/post?userId=${userId}&categoryId=${categoryId}`)
                    .then((res) => res.data);

                return response.data;
            },
            staleTime,
        });
    };

    const getPostDetail = ({ userId, postId }: getPostDetailRequest) => {
        return useQuery({
            queryKey: [QUERY_KEY.POST, { userId, postId }],
            queryFn: async () => {
                const response: getPostDetailResponse = await defaultInstance
                    .get(`/post/${userId}/${postId}`)
                    .then((res) => res.data);

                return response.data;
            },
            staleTime,
        });
    };

    const getAllPostList = ({ order }: getPostListRequest) => {
        return useQuery({
            queryKey: [QUERY_KEY.POST, { order }],
            queryFn: async () => {
                const response: getPostListResponse = await defaultInstance
                    .get(`/post?order=${order}`)
                    .then((res) => res.data);

                return response.data;
            },
            staleTime,
        });
    };

    return {
        getUserPostList,
        getUserCategoryPostList,
        getPostDetail,
        getAllPostList,
    };
};

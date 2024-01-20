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

    const getPostList = ({ userId, categoryId, order }: getPostListRequest) => {
        const key: getPostListRequest = {};
        let url = '/post';

        if (userId) {
            key.userId = userId;
            url += `?userId=${userId}`;
        }

        if (categoryId) {
            key.categoryId = categoryId;
            url += `?categoryId=${categoryId}`;
        }

        if (order) {
            key.order = order;
            url += `?order=${order}`;
        }

        return useQuery({
            queryKey: [QUERY_KEY.POST, key],
            queryFn: async () => {
                const response: getPostListResponse = await defaultInstance
                    .get(url)
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

    return {
        getPostList,
        getPostDetail,
    };
};

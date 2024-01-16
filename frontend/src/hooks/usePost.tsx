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
            queryKey: [QUERY_KEY.POST, userId],
            queryFn: async () => {
                const response: getPostListResponse = await defaultInstance
                    .get(`/post/${userId}`)
                    .then((res) => res.data);

                return response.data;
            },
            staleTime,
        });
    };

    const getPostDetail = ({ userId, postId }: getPostDetailRequest) => {
        return useQuery({
            queryKey: [QUERY_KEY.POST, userId, postId],
            queryFn: async () => {
                const response: getPostDetailResponse = await defaultInstance
                    .get(`/post/${userId}/${postId}`)
                    .then((res) => res.data);

                return response.data;
            },
            staleTime,
        });
    };

    // @TODO: 전체 게시물 조회하는 API 생성

    return { getUserPostList, getPostDetail };
};

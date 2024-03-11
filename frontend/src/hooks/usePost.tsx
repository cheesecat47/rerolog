import { authInstance } from "@/apis/authInstance";
import { defaultInstance } from '@/apis/defaultInstance';
import { QUERY_KEY } from '@/constants/queryKeys';
import {
    getPostDetailRequest,
    getPostDetailResponse,
    getPostListRequest,
    getPostListResponse,
    writePostRequest,
} from '@/types/api/post';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

export const usePost = () => {
    const staleTime = Number(import.meta.env.VITE_STALE_TIME);
    const queryClient = useQueryClient();

    const getPostList = ({ userId, categoryId, order }: getPostListRequest) => {
        const key: getPostListRequest = {};
        let url = 'post';
        const sortList: string[] = [];

        if (userId || categoryId || order) {
            url += '?';
        }

        if (userId) {
            key.userId = userId;
            sortList.push(`userId=${userId}`);
        }

        if (categoryId) {
            key.categoryId = categoryId;
            sortList.push(`categoryId=${categoryId}`);
        }

        if (order) {
            key.order = order;
            sortList.push(`order=${order}`);
        }

        return useQuery({
            queryKey: [QUERY_KEY.POST, key],
            queryFn: async () => {
                const response: getPostListResponse = await defaultInstance
                    .get(url + sortList.join('&'))
                    .then((res) => res.data);

                return response.data;
            },
            staleTime,
        });
    };

    const getPostDetail = ({ userId, postTitle }: getPostDetailRequest) => {
        return useQuery({
            queryKey: [QUERY_KEY.POST, { userId, postTitle }],
            queryFn: async () => {
                const response: getPostDetailResponse = await defaultInstance
                    .get(`post/${postTitle}`)
                    .then((res) => res.data);

                return response.data;
            },
            staleTime,
        });
    };

    const writePost = () => {
        return useMutation({
            mutationFn: async (postData: writePostRequest) => {
                try { 
                    const { data } = await authInstance.post('post', postData);
                    return data;
                }
                catch (err) {
                    throw err;
                }
            },
            onSuccess: () => {
                // queryClient.invalidateQueries({ queryKey: [QUERY_KEY.POST, postTitle] });
                alert('게시글을 성공적으로 작성했습니다.');
            },
            onError: () => {
                console.log('에러');
                alert('로그인 후 이용가능합니다.');
            }
        })
    }

    return {
        getPostList,
        getPostDetail,
        writePost
    };
};

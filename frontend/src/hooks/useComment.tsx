
import { authInstance } from "@/apis/authInstance";
import { QUERY_KEY } from '@/constants/queryKeys';
import { getPostCommentRequest, getPostCommentResponse, writePostCommentRequest } from "@/types/api/comment";
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

export const useComment = () => {
    const staleTime = Number(import.meta.env.VITE_STALE_TIME);
    const queryClient = useQueryClient();

    const getPostComment = ({ postTitle }: getPostCommentRequest) => {

        return useQuery({
            queryKey: [QUERY_KEY.POST, QUERY_KEY.COMMENT, postTitle],
            queryFn: async () => {
                const response: getPostCommentResponse = await authInstance
                    .get(`/post/${postTitle}/comment`)
                    .then((res) => res.data);

                return response.data;
            },
            staleTime,
        });
    };
    
    const writePostComment = (postTitle: string) => {
        return useMutation({
            mutationFn: async ({ content }: writePostCommentRequest) => {
                try { 
                    const { data } = await authInstance.post(`/post/${postTitle}/comment`, {
                        content
                    });
    
                    return data;
                }
                catch (err) {
                    throw err;
                }
            },
            onSuccess: () => {
                queryClient.invalidateQueries({ queryKey: [QUERY_KEY.POST, QUERY_KEY.COMMENT, postTitle] });
            },
            onError: () => {
                console.log('에러');
                alert('로그인 후 이용가능합니다.');
            }
        })
    }

    return {
        getPostComment,
        writePostComment
    };
};

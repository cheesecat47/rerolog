import { useQuery } from '@tanstack/react-query';
import { defaultInstance } from 'apis/defaultInstance';
import { QUERY_KEY } from 'constants/queryKeys';
import { getBlogInfoReqest, getBlogInfoResponse } from 'types/api/blog';

export const useBlog = () => {
    const staleTime = Number(process.env.REACT_APP_STALE_TIME);

    const getBlogInfo = ({ userId }: getBlogInfoReqest) => {
        return useQuery({
            queryKey: [QUERY_KEY.BLOG, userId],
            queryFn: async () => {
                const response: getBlogInfoResponse = await defaultInstance
                    .get(`/blog/${userId}`)
                    .then((res) => res.data);
                return response;
            },
            staleTime,
        });
    };

    return { getBlogInfo };
};

import { defaultInstance } from '@/apis/defaultInstance';
import { QUERY_KEY } from '@/constants/queryKeys';
import { useQuery } from '@tanstack/react-query';
import { getCategoryRequest, getCategoryResponse } from 'types/api/category';

export const useCategory = () => {
    const staleTime = Number(import.meta.env.VITE_STALE_TIME);

    const getCategoryList = ({ userId }: getCategoryRequest) => {
        return useQuery({
            queryKey: [QUERY_KEY.CATEGORY, userId],
            queryFn: async () => {
                const response: getCategoryResponse = await defaultInstance
                    .get(`blog/${userId}/category`)
                    .then((res) => res.data);
                return response.data;
            },
            staleTime,
        });
    };

    return { getCategoryList };
};

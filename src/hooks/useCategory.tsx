import { useQuery } from '@tanstack/react-query';
import { defaultInstance } from 'apis/defaultInstance';
import { QUERY_KEY } from 'constants/queryKeys';
import { getCategoryRequest, getCategoryResponse } from 'types/api/category';

export const useCategory = () => {
    const staleTime = Number(process.env.REACT_APP_STALE_TIME);

    const getCategoryList = ({ userId }: getCategoryRequest) => {
        return useQuery({
            queryKey: [QUERY_KEY.CATEGORY, userId],
            queryFn: async () => {
                const response: getCategoryResponse = await defaultInstance
                    .get(`/blog/${userId}/category`)
                    .then((res) => res.data);
                return response.data;
            },
            staleTime,
        });
    };

    return { getCategoryList };
};

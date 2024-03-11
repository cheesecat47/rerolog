
import { authInstance } from "@/apis/authInstance";
import { QUERY_KEY } from '@/constants/queryKeys';
import { getUserInfoReqest, getUserInfoResponse } from "@/types/api/user";
import { useQuery } from '@tanstack/react-query';

export const useUser = () => {
    const staleTime = Number(import.meta.env.VITE_STALE_TIME);

    const getUserInfo = ({ userId }: getUserInfoReqest) => {

        return useQuery({
            queryKey: [QUERY_KEY.POST, userId],
            queryFn: async () => {
                const response: getUserInfoResponse = await authInstance
                    .get(`/user/${userId}`)
                    .then((res) => res.data);

                return response.data;
            },
            staleTime,
        });
    };

    return {
        getUserInfo,
    };
};

import { defineStore } from "pinia";
import { ref } from "vue";
import { getUserInfoById } from '../api/user';

export const useUserStore = defineStore(
    'userStore',
    () => {
        const isLogin = ref(false);
        const user_info = ref({});

        // 블로그 주인 유저 정보 조회
        const getBloggerInfo = (id) => {
            getUserInfoById(id,
                ({ data }) => {
                    user_info.value = data.data;
                },
                null
            );
        }

        return {
            isLogin,
            user_info,
            getBloggerInfo,
        }
    }
);
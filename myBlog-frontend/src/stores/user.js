import { defineStore } from "pinia";
import { ref } from "vue";

export const useUserStore = defineStore(
    'userStore',
    () => {
        const isLogin = ref(false);

        return {
            isLogin,
        }
    }
);
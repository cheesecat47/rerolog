import { defineStore } from 'pinia';
import { ref } from 'vue';
import { getCategories } from '../api/blog';

export const useBlogStore = defineStore(
    'blogStore',
    () => {
        const categories = ref([]);

        const getCategoriesWrapper = (id) => {
            getCategories(id,
                ({ data }) => {
                    categories.value = data.data
                },
                null);
        }

        return {
            categories,
            getCategoriesWrapper
        }
    }
)
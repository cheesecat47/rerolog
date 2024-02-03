<script setup>
import { useUserStore } from '../stores/userStore.js';
import { storeToRefs } from 'pinia';

const userStore = useUserStore();
const { user_info } = storeToRefs(userStore);

const contactIcon = (type) => {
  switch (type) {
    case 'Email':
      return ['fas', 'envelope'];
    case 'GitHub':
      return ['fab', 'github'];
    case 'LinkedIn':
      return ['fab', 'linkedin'];
    default:
      return ['fas', 'link'];
  }
};
</script>

<template>
  <hr class="border border-gray-500 my-6" />
  <footer class="footer my-6 text-lg">
    <div class="flex flex-col lg:flex-row justify-center items-center mx-auto">
      <span> Blog Title &copy; 2023 {{ user_info.name }}. </span>
      <div class="flex items-center lg:ml-4 gap-2">
        <template v-for="(item, index) in user_info.contacts" :key="index">
          <a :href="(item.type === 'Email' ? 'mailto:' : '') + item.value">
            <font-awesome-icon :icon="contactIcon(item.type)" />
          </a>
        </template>
      </div>
    </div>
  </footer>
</template>

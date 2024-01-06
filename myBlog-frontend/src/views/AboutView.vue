<script setup>
import { useUserStore } from '../stores/userStore.js';
import { storeToRefs } from 'pinia';

const userStore = useUserStore();
const { user_info } = storeToRefs(userStore);

const contactIcon = (t) => {
  switch (t) {
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
  <main class="mx-6 lg:mx-0 flex flex-col">
    <div class="hero mx-auto max-w-xl min-w-lg">
      <div class="hero-content flex-row">
        <img
          class="rounded-lg shadow-lg w-40"
          src="../assets/6B152AA5-CC62-49B6-913A-7C702AF22F1E_1_102_o.jpeg"
          alt="profile image"
        />
        <div class="flex flex-col items-center pl-6 gap-2">
          <h1 class="text-3xl font-bold">{{ user_info.name }}</h1>
          <h2 class="text-xl">{{ user_info.id }}</h2>
          <ul class="mt-4">
            <li
              class="text-sm"
              v-for="(item, index) in user_info.contacts"
              :key="index"
            >
              <a :href="(item.type === 'Email' ? 'mailto:' : '') + item.value">
                <font-awesome-icon :icon="contactIcon(item.type)" />
                {{ item.value }}
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <section class="mt-6 max-w-2xl mx-auto">{{ user_info.content }}</section>
  </main>
</template>

<script setup>
import { RouterView } from 'vue-router';
import NavbarTop from './components/NavbarTop.vue';
import TheFooter from './components/TheFooter.vue';
import { useUserStore } from './stores/userStore.js';

const userStore = useUserStore();

// 블로그 방문 시 주인 유저 정보 조회
const { getBloggerInfo } = userStore;
getBloggerInfo('cheesecat47');
</script>

<template>
  <div class="container mx-auto max-w-5xl">
    <!-- 상단 Navbar -->
    <NavbarTop />

    <RouterView />

    <!-- Footer -->
    <TheFooter />
  </div>
</template>

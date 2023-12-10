<script setup>
import { RouterView } from 'vue-router';
import NavbarTop from './components/NavbarTop.vue';
import TheFooter from './components/TheFooter.vue';
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

<style scoped></style>

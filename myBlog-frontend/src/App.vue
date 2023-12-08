<script setup>
import { RouterView } from 'vue-router';
import NavbarTop from './components/NavbarTop.vue';
import TheFooter from './components/TheFooter.vue';
</script>

<template>
  <div class="lg:container lg:mx-auto">
    <!-- 상단 Navbar -->
    <NavbarTop />

    <RouterView />

    <!-- Footer -->
    <TheFooter />
  </div>
</template>

<style scoped></style>

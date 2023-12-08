<script setup>
import { RouterLink } from 'vue-router';
</script>

<template>
  <header class="box-border">
    <nav class="flex items-center justify-between flex-wrap p-8">
      <div class="flex items-center flex-shrink-0 mr-6">
        <span class="font-bold text-2xl">Blog Title</span>
      </div>
      <div class="block lg:flex lg:items-center lg:w-auto">
        <RouterLink to="/">로그인</RouterLink>
      </div>
    </nav>
    <hr />
  </header>
</template>

<style scoped></style>

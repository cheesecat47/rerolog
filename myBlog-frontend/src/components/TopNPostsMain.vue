<script setup>
import PostCard from './PostCard.vue';

defineProps({
  title: String,
  posts: Array,
});
</script>

<template>
    <div class="border rounded p-4">
        <h2 class="text-2xl text-center mb-2">{{ title }}</h2>
        <div class="grid grid-cols-1 lg:grid-cols-2 justify-center gap-4">
          <PostCard v-for="(item, index) in posts" :key="index" :post="item"/>
        </div>
    </div>
</template>

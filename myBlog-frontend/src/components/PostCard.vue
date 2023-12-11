<script setup>
defineProps({
  post: Object,
});
</script>

<template>
  <div class="card card-compact card-side">
    <figure class="basis-1/4"><img :src="post.thumbnail" alt="post image" /></figure>
    <div class="card-body basis-3/4">
      <h2 class="card-title">{{ post.title }}</h2>
      <p>{{ post.excerpt }}</p>
      <div class="flex flex-wrap gap-2">
        <span>{{ post.author }}</span>
        <span>{{ post.datetime }}</span>
        <span>조회수: {{ post.hit }}</span>
      </div>
    </div>
  </div>
</template>

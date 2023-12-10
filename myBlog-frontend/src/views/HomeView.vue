<script setup>
import { ref } from 'vue';
import NavbarLeft from '../components/NavbarLeft.vue';
import TopNPostsMain from '../components/TopNPostsMain.vue';

const latestPosts = ref([
  {
    title: '내 아이디가 왜 cheesecat이냐면',
    author: 'cheesecat47',
    datetime: '2023.12.02. 23:00',
    hit: 21,
    excerpt:
      '2018년 10월에 동촌 유원지에 사진 찍으러 나갔다가 만난 아기 고양이. 진짜 예뻤다.',
    thumbnail: '/image-regular.svg',
  },
  {
    title: 'DevFest 2023 Busan',
    author: 'cheesecat47',
    datetime: '2023.12.03. 23:00',
    hit: 21,
    excerpt:
      '12월 2일에 부산에서 한 DevFest에 갔다. 이번에는 스티커 엄청 많이 줘서 노트북에 바로 붙였다 ㅋㅋ',
    thumbnail: '/image-regular.svg',
  },
  {
    title: '내 아이디가 왜 cheesecat이냐면',
    author: 'cheesecat47',
    datetime: '2023.12.02. 23:00',
    hit: 21,
    excerpt:
      '2018년 10월에 동촌 유원지에 사진 찍으러 나갔다가 만난 아기 고양이. 진짜 예뻤다.',
    thumbnail: '/image-regular.svg',
  },
  {
    title: 'DevFest 2023 Busan',
    author: 'cheesecat47',
    datetime: '2023.12.03. 23:00',
    hit: 21,
    excerpt:
      '12월 2일에 부산에서 한 DevFest에 갔다. 이번에는 스티커 엄청 많이 줘서 노트북에 바로 붙였다 ㅋㅋ',
    thumbnail: '/image-regular.svg',
  },
]);

const popularPosts = ref([
  {
    title: '내 아이디가 왜 cheesecat이냐면',
    author: 'cheesecat47',
    datetime: '2023.12.02. 23:00',
    hit: 21,
    excerpt:
      '2018년 10월에 동촌 유원지에 사진 찍으러 나갔다가 만난 아기 고양이. 진짜 예뻤다.',
    thumbnail: '/image-regular.svg',
  },
  {
    title: 'DevFest 2023 Busan',
    author: 'cheesecat47',
    datetime: '2023.12.03. 23:00',
    hit: 21,
    excerpt:
      '12월 2일에 부산에서 한 DevFest에 갔다. 이번에는 스티커 엄청 많이 줘서 노트북에 바로 붙였다 ㅋㅋ',
    thumbnail: '/image-regular.svg',
  },
  {
    title: '내 아이디가 왜 cheesecat이냐면',
    author: 'cheesecat47',
    datetime: '2023.12.02. 23:00',
    hit: 21,
    excerpt:
      '2018년 10월에 동촌 유원지에 사진 찍으러 나갔다가 만난 아기 고양이. 진짜 예뻤다.',
    thumbnail: '/image-regular.svg',
  },
  {
    title: 'DevFest 2023 Busan',
    author: 'cheesecat47',
    datetime: '2023.12.03. 23:00',
    hit: 21,
    excerpt:
      '12월 2일에 부산에서 한 DevFest에 갔다. 이번에는 스티커 엄청 많이 줘서 노트북에 바로 붙였다 ㅋㅋ',
    thumbnail: '/image-regular.svg',
  },
]);
</script>

<template>
  <main class="mx-6 lg:mx-0 flex flex-col lg:flex-row">
    <div>
      <NavbarLeft />
    </div>
    <div class="w-full mt-6 lg:mt-0 lg:ml-6 flex flex-col gap-6">
      <!-- 최근 글 목록 -->
      <TopNPostsMain v-bind="{ title: '최근 글 목록', posts: latestPosts }" />

      <!-- 인기 글 목록 -->
      <TopNPostsMain v-bind="{ title: '인기 글 목록', posts: popularPosts }" />
    </div>
  </main>
</template>

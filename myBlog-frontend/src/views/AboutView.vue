<script setup>
import { ref } from 'vue';

// Todo: 추후 API 응답 데이터로 변경 필요.
const user = ref({
  id_str: 'cheesecat47',
  name: '신주용',
  content:
    '안녕하세요, 신주용입니다. 저는 고양이를 좋아합니다. 사실 이런 곳에는 Lorem Ipsum 문자열을 채워넣으면 가장 편하긴 한데 그러기에는 재미가 없잖아요. 근데 안그러려니 딱히 채울 내용이 없긴 해요. 그래서 한 세 줄만 넣어봐야겠어요. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas dictum, nunc at posuere iaculis, augue lectus efficitur dolor, ac facilisis metus ipsum ac sem. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In hac habitasse platea dictumst. Nulla elementum facilisis velit, quis varius ante venenatis nec.',
  contacts: [
    { type: 'Email', value: 'cheesecat47@gmail.com' },
    { type: 'GitHub', value: 'https://github.com/cheesecat47' },
    { type: 'LinkedIn', value: 'https://www.linkedin.com/in/shinjuyong' },
  ],
});

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
          <h1 class="text-3xl font-bold">{{ user.name }}</h1>
          <h2 class="text-xl">{{ user.id_str }}</h2>
          <ul class="mt-4">
            <li
              class="text-sm"
              v-for="(item, index) in user.contacts"
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
    <section class="mt-6 max-w-2xl mx-auto">{{ user.content }}</section>
  </main>
</template>

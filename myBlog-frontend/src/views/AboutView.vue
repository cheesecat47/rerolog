<script setup>
import axios from "axios";
import {ref} from "vue";

const resp = ref({});
const board_list = ref([]);

axios.get("http://localhost:8080/board").then(({data}) => {
  resp.value = data;
  console.log(resp.value);
  board_list.value = data.data;
  console.log(data.data, board_list.value);
});
</script>

<template>
  <div class="about">
    <h1>This is an about page</h1>
    <div v-if="board_list.length > 0">
      <ul v-for="(item, index) in board_list" :key="index">
        <li>item: {{ item }}</li>
      </ul>
    </div>
    <div v-else>
      <p>표시할 항목이 없습니다.</p>
    </div>
  </div>
</template>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>

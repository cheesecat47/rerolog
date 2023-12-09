<script setup></script>

<template>
  <footer class="footer items-center my-6">
    <hr class="border border-gray-500" />
    <div class="flex items-center justify-evenly mt-2">
      <div>
        <span> Blog Title &copy; 2023 Shin Juyong. </span>
      </div>
      <div class="flex items-center mr-6">
        <span class="mr-6">Contact</span>
        <ul>
          <!-- Todo: 여기 들어갈 내용은 추후 API로 받아온 유저 정보 객체에 따라 동적으로 처리 필요. -->
          <li><a href="mailto:cheesecat47@gmail.com"><font-awesome-icon :icon="['fas', 'envelope']" /> cheesecat47@gmail.com</a></li>
          <li><a href="https://github.com/cheesecat47"><font-awesome-icon :icon="['fab', 'github']" /> GitHub</a></li>
          <li><a href="https://www.linkedin.com/in/shinjuyong"><font-awesome-icon :icon="['fab', 'linkedin']" /> LinkedIn</a></li>
        </ul>
      </div>
    </div>
  </footer>
</template>

<style scoped></style>

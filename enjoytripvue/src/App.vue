<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
</script>

<template>
  <div>
    <TheHeadingNavbar></TheHeadingNavbar>
    <router-view></router-view>
  </div>
</template>

<style scoped>
@font-face {
    font-family: 'Orbit-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2310@1.0/Orbit-Regular.woff2') format('woff2');
    font-weight: normal;
    font-style: normal;
}
* {
  font-family: Orbit-Regular;
}
</style>

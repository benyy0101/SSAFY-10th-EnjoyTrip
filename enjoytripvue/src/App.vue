<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
import LoginModal from "@/components/modal/LoginModal.vue";
import {provide, ref, watch} from "vue";
const stateLogin = ref(false);
provide('stateLogin', stateLogin);

watch(stateLogin, ()=>{
  console.log(stateLogin.value)
});
</script>

<template>
  <a-layout class="layout">
    <TheHeadingNavbar class="navbar"></TheHeadingNavbar>
    <a-layout-content class="layoutContent">
      <router-view></router-view>
    <LoginModal v-show="stateLogin"></LoginModal>
    </a-layout-content>
    <a-layout-footer class="footer"></a-layout-footer>
  </a-layout>
</template>

<style scoped>
@font-face {
    font-family: 'NPSfontBold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2310@1.0/NPSfontBold.woff2') format('woff2');
    font-weight: 700;
    font-style: normal;
}
* {
  font-family: NPSfontBold;
}

#main{
  position: relative;
  height: 100vh;
  width: 100vw;
}

.layout{
  
}
.layoutContent{
  background-color: white;
  min-height: 100vh;
}

.footer{
  background-color: #f2eee5;
}
</style>

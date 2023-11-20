<script setup>
import { RouterView } from 'vue-router';
import TheHeadingNavbar from '@/components/layout/TheHeadingNavbar.vue';
import LoginModal from '@/components/modal/LoginModal.vue';
//import FindPasswordModal from '@/components/modal/FindPasswordModal.vue';
import { provide, ref, watch } from 'vue';

const stateLogin = ref(false);
const stateFindPW = ref(false);
provide('stateLogin', stateLogin);
//provide('stateFindPW', stateFindPW);

watch(stateLogin, () => {
  console.log(stateLogin.value);
});
watch(stateFindPW, () => {
  console.log(stateFindPW.value);
});
</script>

<template>
  <a-config-provider
    :theme="{
      token: {
        colorPrimary: '#ff7f50',
      },
    }"
  >
    <a-layout class="layout">
      <TheHeadingNavbar class="navbar"></TheHeadingNavbar>
      <a-layout-content class="layoutContent">
        <router-view></router-view>
        <LoginModal v-show="stateLogin"></LoginModal>
        <FindPasswordModal v-show="stateFindPW"></FindPasswordModal>
      </a-layout-content>
      <a-layout-footer class="footer"></a-layout-footer>
    </a-layout>
  </a-config-provider>
</template>

<style scoped>
@font-face {
  font-family: 'NPSfontBold';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2310@1.0/NPSfontBold.woff2')
    format('woff2');
  font-weight: 700;
  font-style: normal;
}
* {
  font-family: NPSfontBold;
}

#main {
  position: relative;
  height: 100vh;
  width: 100vw;
}

.layout {
}
.layoutContent {
  background-color: white;
  min-height: 100vh;
}

.footer {
  background-color: #f2eee5;
}
</style>

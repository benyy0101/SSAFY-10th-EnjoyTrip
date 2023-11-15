<script setup>
import {inject,ref } from "vue";
const stateLogin = inject('stateLogin');

function toggleModal(){
  stateLogin.value = !stateLogin.value;
}


</script>

<template>

<a-layout-header class="header">
  <div class="leftMenuWrapper">
    <div class="logo">임시 로고</div>
  <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px'}"
      >
        <a-menu-item key="1" class="menuItem">핫플 찾기!</a-menu-item>
        <a-menu-item key="2" class="menuItem">여행계획</a-menu-item>
  </a-menu>
  </div>
  
  <div class="loginSection">
      <div @click="toggleModal">
        로그인
      </div>
      <div>회원가입</div>
  </div>
</a-layout-header>

</template>

<style scoped>
.header{
  display: flex;
  justify-content: space-between;
}

.logo{
  font-size: large;
  color:crimson;
}

.loginSection{
  color:#FF7F50;
  display: flex;
  justify-content: center;
  gap: 2rem;
}

.leftMenuWrapper{
  display: flex;
  gap:5rem;
}

.site-layout-content {
  min-height: 280px;
  padding: 24px;
  background: #abc9ff;
}


</style>



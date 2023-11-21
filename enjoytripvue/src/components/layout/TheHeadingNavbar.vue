<script setup>
import { inject, watch, ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/member';

const memberStore = useMemberStore();
const { isLogin } = storeToRefs(memberStore);
const { userLogout } = memberStore;
const { getUser } = storeToRefs(memberStore);
const stateLogin = inject('stateLogin');

const selectedKeys = ref();

console.log('로그인 상태', isLogin.value);
function toggleModal() {
  stateLogin.value = !stateLogin.value;
}
watch(selectedKeys, (val) => {
  console.log('selectedKeys', val);
});

// watch(
//   isLogin.value,

//   { immediate: true }
// );

const logoutMember = async () => {
  console.log('logout 진행 중!!!');
  console.log(getUser.value);
  await userLogout(getUser.value.userId);
};
</script>

<template>
  <div
    :style="{
      display: 'flex',
      justifyContent: 'space-between',
      backgroundColor: 'white',
    }"
  >
    <a-menu
      mode="horizontal"
      v-model:selectedKeys="selectedKeys"
      :style="{
        display: 'flex',
        flexDirection: 'row',
      }"
    >
      <a-menu-item key="board">
        <template #icon>
          <img src="@/assets/board.png" :style="{ width: '20px' }" />
        </template>
        <router-link :to="{ name: 'board' }" class="nav-link"
          >여행 후기</router-link
        >
      </a-menu-item>
      <a-menu-item key="map">
        <template #icon>
          <img src="@/assets/map.png" :style="{ width: '20px' }" />
        </template>
        <router-link :to="{ name: 'plan-setup' }" class="nav-link"
          >지도</router-link
        >
      </a-menu-item>
    </a-menu>
    <a-menu
      v-if="!isLogin"
      mode="horizontal"
      v-model:selectedKeys="selectedKeys"
      :style="{ width: '230px' }"
    >
      <a-menu-item key="login" @click="toggleModal">
        <template #icon>
          <img src="@/assets/login.png" :style="{ width: '20px' }" />
        </template>
        로그인
      </a-menu-item>
      <a-menu-item key="signup">
        <template #icon>
          <img src="@/assets/signup.png" :style="{ width: '20px' }" />
        </template>
        <router-link :to="{ name: 'member-signup' }" class="nav-link"
          >회원가입</router-link
        >
      </a-menu-item>
    </a-menu>
    <a-menu
      v-else
      v-model:selectedKeys="selectedKeys"
      mode="horizontal"
      :style="{ width: '260px' }"
    >
      <a-menu-item key="mypage">
        <template #icon>
          <img src="@/assets/mypage.png" :style="{ width: '20px' }" />
        </template>
        <router-link :to="{ name: 'mypage' }" class="nav-link"
          >마이페이지</router-link
        >
      </a-menu-item>
      <a-menu-item key="logout" @click.prevent="logoutMember">
        <template #icon>
          <img src="@/assets/logout.png" :style="{ width: '20px' }" />
        </template>
        로그아웃
      </a-menu-item>
    </a-menu>
  </div>
  <!-- <a-layout-header class="header">
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
  
  <a-menu class="loginSection">
      <div @click="toggleModal">
        로그인
      </div>
      <a-menu-item key="3" class="menuItem">여행계획</a-menu-item>
  </a-menu>
</a-layout-header> -->
</template>

<style scoped>
.logo {
  font-size: large;
  color: crimson;
}

.loginSection {
  color: #ff7f50;
  display: flex;
  gap: 2rem;
}

.leftMenuWrapper {
  display: flex;
  gap: 5rem;
}

.site-layout-content {
  min-height: 280px;
  padding: 24px;
  background: #abc9ff;
}
</style>

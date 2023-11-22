<script setup>
import { inject, watch, ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/member';
import { useRouter } from 'vue-router';
const router = useRouter();
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

const logoutMember = async () => {
  console.log('logout 진행 중!!!');
  console.log(getUser.value);
  await userLogout(getUser.value.userId);
};

function moveHome() {
  router.push({ name: 'main' });
}
</script>

<template>
  <div class="navbar-container">
    <img src="@/assets/mainLogo.png" id="logo" :style="{width:'80px', height:'80px'}"  @click="moveHome"/>
    <a-menu
      mode="horizontal"
      v-model:selectedKeys="selectedKeys"
      :style="{
        display: 'flex',
        flexDirection: 'row',
      }"
      class="menu"
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
      class="menu"
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

.navbar-container {
  position: relative;
  display: flex;
  justify-content: space-between;
  background-color: white;
}

#logo {
  position: absolute;
  left: 50%;
  transform: translate(-50%);
  margin:13px 0 0 0;
  height: 150px;
}

* {
  font-family: NPSfontBold;
}

.menu{
  margin:3rem 0 0
}
</style>

<script setup>
import { ref, watch, inject } from "vue";
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue';
// JWT를 위해 추가
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
const router = useRouter();

const memberStore = useMemberStore();
const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;

const loginModal = inject("stateLogin");

const login = ref({
  userId: " ",
  userPwd: " ",
});

const userIdErrMsg = ref("");
const userPwdErrMsg = ref("");

watch(
  () => login.value.userId,
  (value) => {
    let len = value.length;
    if (len == 0) {
      userIdErrMsg.value = "아이디를 입력해주세요!";
    } else userIdErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => login.value.userPwd,
  (value) => {
    let len = value.length;
    if (len == 0) {
      userPwdErrMsg.value = "비밀번호를 입력해주세요!";
    } else userPwdErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  if (userIdErrMsg.value) {
    alert(userIdErrMsg.value);
  } else if (userPwdErrMsg.value) {
    alert(userPwdErrMsg.value);
  } else {
    loginMember();
  }
}

const loginMember = async () => {
  console.log("login 진행 중!!!");
  login.value = {
    userId: login.value.userId.trim(),
    userPwd: login.value.userPwd.trim()
  }
  await userLogin(login.value);
  let token = sessionStorage.getItem("accessToken");
  
  console.log("isLogin: ", isLogin);
  
  if (isLogin) {
    console.log("로그인 성공아닌가?");
    console.log("111. ", token);
    // 이름 찍기 위해서 token을 가지고 getUserInfo로 가서 가져온다 -> member.js
    getUserInfo(token);
  }
  router.push("/");
};
</script>

<template>
  <a-modal
    v-model:open="loginModal"
    :closable="false"
    :footer="null"
    centered="true"
  >

    <a-space class="loginHeader">
      <h1 class="title">로그인</h1>
      <div>비밀번호를 잊어버리셨나요?</div>
    </a-space>
    <div class="formContainer">
      <form @submit.prevent="onSubmit">
        <a-input v-model="login.userId" size="large" placeholder="아이디" class="idInput">
          <template #prefix>
        <user-outlined />
      </template>
        </a-input>
        <a-input type="password" v-model="login.userPwd" size="large" placeholder="비밀번호" class="pwdInput" @keyup.enter="onSubmit">
          <template #prefix>
        <LockOutlined />
      </template>
        </a-input>
        <a-flex :justify="'flex-end'" class="buttonWrapper">
          <button type="submit" @click.prevent="onSubmit" class="login">로그인</button>
        </a-flex>
        
      </form>
    </div>
  </a-modal>
</template>
<style>
  .loginHeader{
    display: flex;
    justify-content: space-between;
  }
  .idInput{
    border-radius: 8px 8px 0 0;
  }
  .pwdInput{
    border-radius: 0 0 8px 8px;
  }

  .title{
    font-weight: 900;
  }

  .buttonWrapper{
    margin-top: 2rem;
  }

  .login{
    background-color: #FF7F50;
    border: none;
    padding: 0.5rem 2rem;
    font-weight: 800;
    border-radius: 3rem;
    font-size: larger;
    color:aliceblue;
  }
</style>

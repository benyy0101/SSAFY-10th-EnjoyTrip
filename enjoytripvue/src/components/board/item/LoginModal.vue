<script setup>
import {ref, watch, inject} from 'vue';

const loginModal = inject('stateLogin');    
// // watch(loginModal,()=>{
// //     console.log(loginModal);
// // })
const login = ref({
  userId: ' ',
  userPwd: ' ',
});

const userIdErrMsg = ref('');
const userPwdErrMsg = ref('');

watch(
  () => login.value.userId,
  (value) => {
    let len = value.length;
    if (len == 0) {
      userIdErrMsg.value = '아이디를 입력해주세요!';
    } else userIdErrMsg.value = '';
  },
  { immediate: true }
);
watch(
  () => login.value.userPwd,
  (value) => {
    let len = value.length;
    if (len == 0) {
      userPwdErrMsg.value = '비밀번호를 입력해주세요!';
    } else userPwdErrMsg.value = '';
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

function loginMember() {
  console.log('로그인하자', login.value);

  // todo: 나중에 수정해야 함
  // registUser(
  //   member.value,
  //   ({ data }) => {
  //     console.log('regist.....................sucess, data: ', data);
  //   },
  //   (err) => {
  //     console.log(err);
  //   }
  // );
  // API 호출
}

function toggleLogin() {
    console.log("clicked");
    loginModal.value = !loginModal.value;
}
</script>
<template>
    <div id="modal-container" @click="()=>{toggleLogin()}">
        <div class="p-5" id="loginModal">
        <h1 class="text-center mt-3">로그인</h1>
  <div id="loginForm" class="mt-3">
    <form @submit.prevent="onSubmit">
      <div class="mb-3">
        <label for="userId" class="form-label">아이디 : </label>
        <input
          type="text"
          class="form-control"
          v-model="login.userId"          
          placeholder="아이디..."
        />
      </div>
      <div class="mb-3">
        <label for="userPwd" class="form-label">비밀번호 : </label>
        <input
          type="password"
          class="form-control"
          v-model = "login.userPassword"          
          placeholder="비밀번호..."
        />
      </div>
      <div class="mt-3 col-auto text-center">
        <button type="submit" class="btn btn-outline-primary mb-3">
          로그인
        </button>
      </div>
    </form>
  </div>
    </div>
    </div>
</template>
<style>
    #loginModal{
        position: absolute;
        z-index: 99999;
        top: 50%; right: 50%;
        transition: transform 1s;
        transform: translate(50%,-50%);
        background-color:aliceblue;
        border-radius: 10px;
    }
    #modal-container {
        position: absolute;
        z-index: 9999;
        top:0px;
        right:0px;
        height:100vh;
        width: 100vw;
        background: rgba(39, 39, 39, .6)
    }
</style>
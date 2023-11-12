<script setup>
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);

const member = ref({
  userId: '',
  userName: '',
  userPwd: '',
  email: '',
});

const userIdErrMsg = ref('');
const userPwdErrMsg = ref('');
watch(
  () => member.value.userId,
  (value) => {
    let len = value.length;
    if (len == 0) {
      userIdErrMsg.value = '아이디를 입력해주세요!';
    } else userIdErrMsg.value = '';
  },
  { immediate: true }
);
watch(
  () => member.value.userPwd,
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
    signup();
  }
}

function signup() {
  console.log('회원가입하자', member.value);

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

// 나중에 필요하려나?
// function updateArticle() {
//   console.log(article.value.articleNo + "번글 수정하자!!", article.value);
//   modifyArticle(article.value,
//   ({data}) =>{
//     console.log("regist.....................sucess, data: ", data);
//   },
//   err => {
//     console.log(err)
//   }
//   )
//    // API 호출
// }
</script>

<template>
  <h1 class="text-center mt-3">회원가입</h1>
  <div id="signupForm" class="mt-3">
    <form @submit.prevent="onSubmit">
      <div class="mb-3">
        <label for="userId" class="form-label">아이디 : </label>
        <input
          type="text"
          class="form-control"
          v-model="member.userId"
          :disabled="isUseId"
          placeholder="아이디..."
        />
      </div>
      <div class="mb-3">
        <label for="userName" class="form-label">이름 : </label>
        <input
          type="text"
          class="form-control"
          v-model="member.userName"
          placeholder="이름..."
        />
      </div>
      <div class="mb-3">
        <label for="userPwd" class="form-label">비밀번호 : </label>
        <input
          type="password"
          class="form-control"
          v-model="member.userPwd"
          placeholder="비밀번호..."
        />
      </div>
      <div class="mb-3">
        <label for="email" class="form-label">이메일 : </label>
        <input
          type="email"
          class="form-control"
          v-model="member.emailId"
          placeholder="이메일..."
        />
      </div>
      <div class="col-auto text-center">
        <button type="submit" class="btn btn-outline-primary mb-3">
          회원가입
        </button>
      </div>
    </form>
  </div>
</template>

<style>
#signupForm {
  width: 600px;
  margin: auto;
}
</style>

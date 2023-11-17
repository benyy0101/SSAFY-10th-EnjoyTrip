<script setup>
import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import { joinMember } from "@/api/user";

const router = useRouter();

const props = defineProps({ type: String });

const isUseId = ref(false);

const member = ref({
  userId: "",
  userName: "",
  userPwd: "",
  emailId: "",
  emailDomain: "",
  profileImg: "",
});

const userIdErrMsg = ref("");
const userPwdErrMsg = ref("");
watch(
  () => member.value.userId,
  (value) => {
    let len = value.length;
    if (len == 0) {
      userIdErrMsg.value = "아이디를 입력해주세요!";
    } else userIdErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => member.value.userPwd,
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
    signup();
  }
}

function signup() {
  console.log("회원가입하자", member.value);

  joinMember(
    member.value,
    ({ data }) => {
      console.log("signup.....................success, data: ", data);
      router.push({ name: "main" });
    },
    (err) => {
      console.log(err);
    }
  );
}
</script>

<template>
  <a-layout-content
    :style="{
      padding: '80px 140px',
      background: '#fff',
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
    }"
  >
    <div
      :style="{
        background: '#fff',
        padding: '24px',
        minHeight: '380px',
        width: '80%',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
      }"
    >
      <div class="wholeDiv" :style="{ width: '100%' }">
        <div :style="{ display: 'flex', alignItems: 'flex-start' }">
          <img src="@/assets/signup.gif" :style="{ width: '80px' }" />
          <div
            :style="{
              display: 'flex',
              flexDirection: 'column',
              marginTop: '15px',
              paddingLeft: '10px',
            }"
          >
            <h1>회원가입</h1>
            <h3>EnjoyTrip과 함께 해요!</h3>
          </div>
        </div>
        <a-divider />
        <div
          :style="{
            padding: '30px',
            marginTop: '30px',
            display: 'flex',
            justifyContent: 'center',
          }"
        >
          <a-form @submit.prevent="onSubmit">

            <a-form-item label="아이디 " :style="{ width: '100%' }">
              <a-input v-model:value="member.userId" :disabled="isUseId" />
            </a-form-item>
            <a-form-item label="이름 " :style="{ width: '100%' }">
              <a-input v-model:value="member.userName" />
            </a-form-item>
            <a-form-item label="비밀번호 " :style="{ width: '100%' }">
              <a-input v-model:value="member.userPwd" />
            </a-form-item>
            <a-form-item label="이메일 " :style="{ width: '100%' }">
              <a-input v-model:value="member.emailId">
                <template #addonAfter>
                  <a-select
                    v-model:value="member.emailDomain"
                    style="width: 120px"
                  >
                    <a-select-option value="@naver.com"
                      >@naver.com</a-select-option
                    >
                    <a-select-option value="@gmail.com"
                      >@gmail.com</a-select-option
                    >
                    <a-select-option value="@daum.net"
                      >@daum.net</a-select-option
                    >
                    <a-select-option value="@hanmail.net"
                      >@hanmail.net</a-select-option
                    >
                    <a-select-option value="@kakao.com"
                      >@kakao.com</a-select-option
                    >
                  </a-select>
                </template>
              </a-input>
            </a-form-item>
            <div
              :style="{
                display: 'flex',
                flexDirection: 'row',
                alignItems: 'center',
                marginBottom: '20px',
                justifyContent: 'center',
                marginTop: '20px',
              }"
            >
              <a-button
                :style="{
                  color: '#ABC9FF',
                  borderColor: '#ABC9FF',
                  border: '2px solid',
                  fontSize: '15px',
                  fontWeight: 'Bold',
                  margin: '6px',
                }"
                html-type="submit"
              >
                회원가입
              </a-button>
            </div>
          </a-form>
        </div>
      </div>
    </div>
  </a-layout-content>
</template>

<style scoped>
</style>

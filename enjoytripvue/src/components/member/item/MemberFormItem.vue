<script setup>
import { ref, watch } from "vue";
import { useRouter, useRoute } from "vue-router";
import {
  joinMember,
  updateMember,
  findById,
  uploadImage,
  insertImg,
  idCheck,
  getImg,
  updateImg
} from "@/api/user";
import { Modal } from "ant-design-vue";

const { VITE_IMGBB_KEY } = import.meta.env;

const router = useRouter();
const route = useRoute();
const props = defineProps({ type: String });

const isUseId = ref(false);

// 이미지 파일 업로드
const selectedFile = ref([]);
const imgURL = ref();
const handleFileChange = (e) => {
  selectedFile.value = e.target.files;
};

const upload = async () => {
  const formData = new FormData();
  // form에서 선택된 데이터 가져오기
  formData.append("key", VITE_IMGBB_KEY);
  formData.append("image", selectedFile.value[0]);
  // 프로필 이미지를 위한 코드
  uploadImage(
    formData,
    ({ data }) => {
      console.log(
        "uploadImage.....................success, data: ",
        data.data.url
      );
      profile.value.profileImg = data.data.url;
      imgURL.value = data.data.url;
    },
    (err) => {
      console.log(err);
    }
  );
};

const profile = ref({
  userId: "",
  profileImg: "",
});

const member = ref({
  userId: "",
  userName: "",
  userPwd: "",
  emailId: "",
  emailDomain: "",
});

function getProfileImg() {
  console.log("프로필 이미지 가져오자!");
  // API 호출
  getImg(
    member.value.userId,
    (res) => {
      console.log("이거 프로필?", res.data);
      profile.value.profileImg = res.data;
      imgURL.value = profile.value.profileImg;
    },
    (error) => {
      console.log(error);
    }
  );
}

if (props.type === "modify") {
  let { userId } = route.params;
  member.value.userId = userId;
  console.log(userId + "회원 수정할거야");
  // API 호출
  findById(
    userId,
    (res) => {
      member.value = res.data.userInfo;
      console.log("으잉?", member.value);
    },
    (err) => {
      console.log(err);
    }
  );
  isUseId.value = true;
  getProfileImg();
  console.log("무슨 ? ", member.value);
}

const userIdErrMsg = ref("");
const userPwdErrMsg = ref("");
const profileImgErrMsg = ref("");
watch(
  () => member.value.userId,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      userIdErrMsg.value = "아이디 입력해주세요!";
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

watch(
  () => profile.value.profileImg,
  (value) => {
    let len = value.length;
    if (len == 0) {
      profileImgErrMsg.value =
        "프로필 이미지를 추가하고 사진 업로드 버튼을 눌러주세요!";
    } else profileImgErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  if (userIdErrMsg.value) {
    Modal.warning({
      title: "아이디를 입력해주세요.",
    });
  } else if (userPwdErrMsg.value) {
    Modal.warning({
      title: "비밀번호를 입력해주세요.",
    });
  } else if (profileImgErrMsg.value) {
    Modal.warning({
      title: "프로필 이미지를 추가하고 사진 업로드 버튼을 눌러주세요!",
    });
  } else {
    props.type === "regist" ? signup() : update();
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
  profile.value.userId = member.value.userId;
  insertImg(
    profile.value,
    ({ data }) => {
      console.log("insertImg.....................success, data: ", data);
    },
    (err) => {
      console.log(err);
    }
  );
}

function update() {
  console.log(member.value.userId + "번글 수정하자!!", member.value);
  let { userId } = route.params;
  member.value.userId = userId;
  updateMember(
    member.value,
    ({ data }) => {
      console.log("update.....................success, data: ", data);
      router.push({ name: "mypage" });
    },
    (err) => {
      console.log(err);
    }
  );
  profile.value.userId = member.value.userId;
  updateImg(
    profile.value,
    ({ data }) => {
      console.log("updateImg..................success, data: ", data);
    },
    (err) => {
      console.log(err);
    }
  );
}

function check() {
  console.log("아이디 체크", member.value.userId);
  idCheck(
    member.value.userId,
    ({ data }) => {
      console.log("idCheck...................success, data: ", data);
      if (data === 0) {
        Modal.success({
          title: "사용 가능한 아이디입니다.",
        });
      } else {
        Modal.warning({
          title: "사용할 수 없는 아이디입니다.",
        });
      }
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
        <div v-if="props.type === 'regist'" :style="{ display: 'flex', alignItems: 'flex-start' }">
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
        <div v-else :style="{ display: 'flex', alignItems: 'flex-start' }">
          <img src="@/assets/signup.gif" :style="{ width: '80px' }" />
          <div
            :style="{
              display: 'flex',
              flexDirection: 'column',
              marginTop: '15px',
              paddingLeft: '10px',
            }"
          >
            <h1>회원 정보 수정</h1>
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
            <!-- 이미지 파일 업로드 -->
            <form
              name="form"
              method="post"
              enctype="multipart/form-data"
              @submit.prevent="upload"
            >
              <h4>프로필 이미지</h4>

              <img
                :style="{
                  width: '160px',
                  height: '160px',
                  borderRadius: '100%',
                  display: 'flex',
                  alignItems: 'center',
                  marginBottom: '20px',
                }"
                :src="imgURL"
              />
              <div :style="{ display: 'flex', marginBottom: '20px' }">
                <input
                  type="file"
                  name="files"
                  ref="fileInput"
                  @change="handleFileChange"
                  multiple="multiple"
                />
                <a-button
                  :style="{
                    color: '#ABC9FF',
                    borderColor: '#ABC9FF',
                    border: '2px solid',
                    fontSize: '15px',
                    fontWeight: 'Bold',
                    margin: '6px',
                  }"
                  @click="upload"
                >
                  사진 업로드
                </a-button>
              </div>
            </form>

            <a-form-item :style="{ width: '100%', display: 'flex' }">
              <a-input
                :style="{ width: '70%' }"
                v-model:value="member.userId"
                :disabled="isUseId"
                placeholder="아이디"
              />
              <a-button
                :style="{
                  color: '#ABC9FF',
                  borderColor: '#ABC9FF',
                  border: '2px solid',
                  fontWeight: 'Bold',
                  marginLeft: '5px',
                }"
                @click="check"
                >아이디 체크</a-button
              >
            </a-form-item>
            <a-form-item :style="{ width: '100%' }">
              <a-input v-model:value="member.userName" placeholder="이름" />
            </a-form-item>
            <a-form-item :style="{ width: '100%' }">
              <a-input-password
                v-model:value="member.userPwd"
                placeholder="비밀번호"
              />
            </a-form-item>
            <a-form-item :style="{ width: '100%' }">
              <a-input v-model:value="member.emailId" placeholder="이메일">
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
            <div v-if="props.type === 'regist'"
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
            <div v-else
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
                회원 정보 수정
              </a-button>
            </div>
          </a-form>
        </div>
      </div>
    </div>
  </a-layout-content>
</template>

<style scoped></style>

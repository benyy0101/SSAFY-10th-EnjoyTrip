<script setup>
import { registArticle, modifyArticle, detailArticle } from '@/api/board';
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import ToastEditor from '@/components/board/item/ToastUIEditor.vue';
import { useMemberStore } from '@/stores/member';
import { storeToRefs } from 'pinia';
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const { getUserInfo } = memberStore;
let token = sessionStorage.getItem('accessToken');
getUserInfo(token);

// 이미지
// let imageUploaded = '';

// function upload(event) {
//   const files = event.target?.files;
//   if(files.length > 0) {
//     const file = files[0];

//     const reader = new FileReader();

//     reader.onload = (e) => {
//       imageUploaded = e.target.result;
//     };
//     reader.readAsDataURL(file);
//   }
// }

// Data URL을 Blob으로 변환하는 함수
// function dataURLtoBlob(dataURL) {
//     const parts = dataURL.split(';base64,');
//     const contentType = parts[0].split(':')[1];
//     const raw = window.atob(parts[1]);
//     const blob = new Blob([raw], { type: contentType });
//     return blob;
// }

// 여행 시작 날짜, 끝나는 날짜
const dates = ref();
const value = ref();
const hackValue = ref();

const onOpenChange = (open) => {
  if (open) {
    dates.value = [];
    hackValue.value = [];
  } else {
    hackValue.value = undefined;
  }
};

const onChange = (val) => {
  value.value = val;
};

const onCalendarChange = (val) => {
  dates.value = val;
  article.value.startDate = dates.value[0];
  article.value.endDate = dates.value[1];
};

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);

const article = ref({
  articleNo: 0,
  userId: '',
  subject: '',
  content: '',
  location: '',
  startDate: '',
  endDate: '',
  hit: 0,
  registerTime: '',
});

const setContent = (e) => {
  article.value.content = e;
};

if (props.type === 'modify') {
  let { articleno } = route.params;
  console.log(articleno + '번글 얻어와서 수정할거야');
  // API 호출
  detailArticle(
    articleno,
    (res) => {
      article.value = res.data;
      console.log(article.value);
    },
    (err) => {
      console.log(err);
    }
  );
  isUseId.value = true;
}

const subjectErrMsg = ref('');
const contentErrMsg = ref('');
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = '제목을 확인해 주세요!!!';
    } else subjectErrMsg.value = '';
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = '내용을 확인해 주세요!!!';
    } else contentErrMsg.value = '';
  },
  { immediate: true }
);

function onSubmit() {
  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === 'regist' ? writeArticle() : updateArticle();
    moveList();
  }
}

function writeArticle() {
  article.value.userId = userInfo.value.userId;
  console.log('글등록하자!!', article.value);
  // 이미지 파일 처리
  // const blob = dataURLtoBlob(imageUploaded);
  // const formData = new FormData();
  // const boardDto = {
  //   articleNo: article.value.articleNo,
  //   userId: article.value.userId,
  //   subject: article.value.subject,
  //   content: article.value.content,
  //   location: article.value.location,
  //   startDate: article.value.startDate,
  //   endDate: article.value.endDate,
  //   hit: 0,
  //   registerTime: '',
  //   fileInfos : new Blob([blob], {type: 'image/*'})
  // }

  // // DTO를 FormData에 추가
  // for (const key in boardDto) {
  //     formData.append(key, boardDto[key]);
  // }

  registArticle(
    article.value,
    ({ data }) => {
      console.log('regist.....................success, data: ', data);
      // console.log('formdata..{}', formData)
      moveList();
    },
    (err) => {
      console.log(err);
    }
  );
  
  // registArticle(
  //   article.value,
  //   ({ data }) => {
  //     console.log('regist.....................success, data: ', data);
  //     moveList();
  //   },
  //   (err) => {
  //     console.log(err);
  //   }
  // );
}

function updateArticle() {
  console.log(article.value.articleNo + '번글 수정하자!!', article.value);
  article.value.userId = userInfo.value.userId;
  modifyArticle(
    article.value,
    ({ data }) => {
      console.log('update.....................success, data: ', data);
    },
    (err) => {
      console.log(err);
    }
  );
}

function moveList() {
  router.push({ name: 'article-list' });
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
          <img src="@/assets/write.gif" />
          <div
            :style="{
              display: 'flex',
              flexDirection: 'column',
              marginTop: '5px',
              paddingLeft: '10px',
            }"
          >
            <h1>여행 후기 작성</h1>
            <h3>여행 이야기를 들려주세요!</h3>
          </div>
        </div>
        <a-divider />
        <div
          :style="{
            padding: '10px',
          }"
        >
          <a-form @submit.prevent="onSubmit">
            <a-form-item label="제목 " :style="{ width: '30%' }">
              <a-input v-model:value="article.subject" :disabled="isUseId" />
            </a-form-item>
            <a-form-item label="장소 " :style="{ width: '30%' }">
              <a-input v-model:value="article.location" />
            </a-form-item>

            <a-space>
              <span>여행 날짜: </span>
              <a-range-picker
                :value="hackValue || value"
                @change="onChange"
                @openChange="onOpenChange"
                @calendarChange="onCalendarChange"
              />
            </a-space>

            <!-- 이미지 파일 업로드 -->
            <!-- <div :style="{ marginBottom: '24px' }">
              <label class="form-label">여행 후기 메인 사진</label>
              <img :src="imageUploaded" />
              <input type="file" name="upfile" @change="upload" />
            </div> -->

            <div :style="{ marginTop: '60px' }">
              <!-- 내용 입력 필드 -->
              <ToastEditor :data="content" @setContent="setContent" />
            </div>
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
                v-if="type === 'regist'"
              >
                글작성
              </a-button>
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
                v-else
              >
                글수정
              </a-button>
              <a-button
                :style="{
                  color: '#ABC9FF',
                  borderColor: '#ABC9FF',
                  border: '2px solid',
                  fontSize: '15px',
                  fontWeight: 'Bold',
                  margin: '6px',
                }"
                @click="moveList"
              >
                글목록
              </a-button>
            </div>
          </a-form>
        </div>
      </div>
    </div>
  </a-layout-content>
</template>

<style scoped></style>

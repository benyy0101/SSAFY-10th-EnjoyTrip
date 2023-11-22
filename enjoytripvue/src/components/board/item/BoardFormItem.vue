<script setup>
import { registArticle, modifyArticle, detailArticle } from '@/api/board';
import { uploadImage } from '@/api/user';
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { Modal } from 'ant-design-vue';
import ToastEditor from '@/components/board/item/ToastUIEditor.vue';
import { useMemberStore } from '@/stores/member';
import { storeToRefs } from 'pinia';
const memberStore = useMemberStore();
const { getUser: userInfo } = storeToRefs(memberStore);


const { VITE_IMGBB_KEY } = import.meta.env;

// 이미지 파일 업로드
const selectedFile = ref([]);
// 초기 이미지 넣기
const imgURL = ref('https://i.ibb.co/c6GdLvZ/noImg.png');

const handleFileChange = (e) => {
  selectedFile.value = e.target.files;
};

const upload = async () => {
  const formData = new FormData();
  // form에서 선택된 데이터 가져오기
  formData.append('key', VITE_IMGBB_KEY);
  formData.append('image', selectedFile.value[0]);
  // 프로필 이미지를 위한 코드
  uploadImage(
    formData,
    ({ data }) => {
      console.log(
        'uploadImage.....................success, data: ',
        data.data.url
      );
      article.value.mainImg = data.data.url;
      imgURL.value = data.data.url;
    },
    (err) => {
      console.log(err);
    }
  );
};

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
  mainImg: '',
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
      imgURL.value = article.value.mainImg
    },
    (err) => {
      console.log(err);
    }
  );
  isUseId.value = true;
}

const subjectErrMsg = ref('');
const contentErrMsg = ref('');
const mainImgErrMsg = ref('');
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = '제목을 확인해 주세요!';
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
watch(
  () => article.value.mainImg,
  (value) => {
    let len = value.length;
    if (len == 0) {
      mainImgErrMsg.value =
        '프로필 이미지를 추가하고 사진 업로드 버튼을 눌러주세요!';
    } else mainImgErrMsg.value = '';
  },
  { immediate: true }
);

function onSubmit() {
  if (subjectErrMsg.value) {
    Modal.warning({
      title: '제목을 입력해주세요.',
    });
  } else if (contentErrMsg.value) {
    Modal.warning({
      title: '내용을 입력해주세요.',
    });
  } else if (mainImgErrMsg.value) {
    Modal.warning({
      title: '프로필 이미지를 추가하고 사진 업로드 버튼을 눌러주세요!',
    });
  } else {
    props.type === 'regist' ? writeArticle() : updateArticle();
    moveList();
  }
}

function writeArticle() {
  article.value.userId = userInfo.value.userId;
  console.log('글등록하자!', article.value);

  registArticle(
    article.value,
    ({ data }) => {
      console.log('regist.....................success, data: ', data);
      moveList();
    },
    (err) => {
      console.log(err);
    }
  );
}

function updateArticle() {
  console.log(article.value.articleNo + '번글 수정하자!', article.value);
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
        <div v-if="props.type === 'regist'" :style="{ display: 'flex', alignItems: 'flex-start' }">
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
        <div v-else :style="{ display: 'flex', alignItems: 'flex-start' }">
          <img src="@/assets/write.gif" />
          <div
            :style="{
              display: 'flex',
              flexDirection: 'column',
              marginTop: '5px',
              paddingLeft: '10px',
            }"
          >
            <h1>여행 후기 수정</h1>
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
            <div
              :style="{
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'space-evenly',
              }"
            >
              <div :style="{ display: 'flex', flexDirection: 'column' }">
                <a-form-item label="제목" :style="{ width: '100%' }">
                  <a-input
                    v-model:value="article.subject"
                    :disabled="isUseId"
                  />
                </a-form-item>
                <a-form-item label="장소" :style="{ width: '100%'}">
                  <a-input v-model:value="article.location" />
                </a-form-item>
                <a-space>
                  <span>여행 날짜: </span>
                  <a-range-picker
                    :style="{ width: '100%' }"
                    :value="hackValue || value"
                    @change="onChange"
                    @openChange="onOpenChange"
                    @calendarChange="onCalendarChange"
                  />
                </a-space>

                <!-- 이미지 파일 업로드 -->
                <form
                  name="form"
                  method="post"
                  enctype="multipart/form-data"
                  @submit.prevent="upload"
                >
                  <h4 :style="{ marginTop: '30px' }">여행 후기 메인 이미지</h4>
                  <div
                    :style="{
                      display: 'flex',
                      marginTop: '30px',
                      marginBottom: '20px',
                    }"
                  >
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
                        fontSize: '14px',
                        fontWeight: 'Bold',
                      }"
                      @click="upload"
                    >
                      사진 업로드
                    </a-button>
                  </div>
                </form>
              </div>
              <img
                :src="imgURL"
                :style="{
                  display: 'flex',
                  width: '180px',
                  height: '180px',
                  marginTop: '40px'
                }"
              />
            </div>

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

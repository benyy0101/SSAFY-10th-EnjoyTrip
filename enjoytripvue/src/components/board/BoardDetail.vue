<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { Modal } from 'ant-design-vue';
import { detailArticle, deleteArticle } from '@/api/board';
import { listComment, writeComment } from '@/api/comment';
import CommentListItem from '@/components/board/item/CommentListItem.vue';
import { useMemberStore } from '@/stores/member';
const memberStore = useMemberStore();
const { userInfo } = memberStore;

const route = useRoute();
const router = useRouter();

const { articleno } = route.params;

const article = ref({});
const comments = ref({});
const num = ref(0);
const sendComment = ref({
  articleNo: articleno,
  userId: '',
  commentContent: '',
});

onMounted(() => {
  getArticle();
  getComment();
});

const getArticle = () => {
  console.log(articleno + '번글 얻으러 가자!!!');
  // API 호출
  detailArticle(
    articleno,
    (res) => {
      console.log(res);
      article.value = res.data;
      const timeInfo = article.value.registerTime.split(' ');
      //console.log(timeInfo);
      article.value.registerTime =
        timeInfo[0].replaceAll('-', '.') +
        ' ' +
        timeInfo[1].split(':').slice(0, 2).join(':');
      article.value.startDate = article.value.startDate.replaceAll('-', '.');
      article.value.endDate = article.value.endDate.replaceAll('-', '.');
    },
    (err) => {
      console.log(err);
    }
  );
  // const cookie = this.$cookies.get('boardView');
  // console.log("쿠키당", cookie);
};

const getComment = () => {
  console.log(articleno + '번 댓글 얻으러 가자!!!');
  // API 호출
  listComment(
    articleno,
    (res) => {
      console.log(res);
      comments.value = res.data;
      num.value = comments.value.length;
    },
    (err) => {
      console.log(err);
    }
  );
};

// 댓글 추가 시 변경 감지
watch(comments.value, () => {
  console.log('댓글 추가', comments.value);
});

function moveList() {
  router.push({ name: 'article-list' });
}

function moveModify() {
  router.push({ name: 'article-modify', params: { articleno } });
}

function onDeleteArticle() {
  console.log(articleno + '번글 삭제하러 가자!!!');
  // API 호출
  deleteArticle(articleno);
  moveList();
}

const contentErrMsg = ref('');
watch(
  () => sendComment.value.commentContent,
  (value) => {
    let len = value.length;
    if (len == 0) {
      contentErrMsg.value = '댓글을 작성해주세요';
    } else contentErrMsg.value = '';
  },
  { immediate: true }
);

function onSubmit() {
  if (contentErrMsg.value) {
    Modal.warning({
      title: '댓글을 작성해주세요.',
    });
  } else {
    onSendComment();
  }
}

function onSendComment() {
  console.log(userInfo);
  writeComment(
    sendComment.value,
    ({ data }) => {
      console.log('writeComment................success, data: ', data);
      getComment();
      document.getElementById('commentArea').value = null;
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
      fontSize: '18px',
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
        <div :style="{ display: 'flex', alignItems: 'center' }">
          <img src="@/assets/camera.gif" />
          <div
            :style="{
              display: 'flex',
              flexDirection: 'column',
              marginTop: '5px',
              paddingLeft: '10px',
            }"
          >
            <h1>여행 후기</h1>
            <h3>여행 이야기를 들어주세요!</h3>
          </div>
        </div>
        <a-divider />
        <div
          :style="{
            padding: '10px',
          }"
        >
          <div
            :style="{
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              marginTop: '30px',
              marginBottom: '20px',
            }"
          >
            <h1 id="title">{{ article.subject }}</h1>
          </div>
          <div>
            <div class="meta-container">
              <div class="sub-container">
                <div>
                  <img
                    src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                  />
                  <span>{{ article.userId }}</span>
                </div>
                <div>{{ article.registerTime }}</div>
                <div>조회 : {{ article.hit }}</div>
              </div>
              <div>
                여행 정보:
                <span>{{ article.startDate }}</span>
                ~
                <span>{{ article.endDate }}</span>
                <span>{{ '    ' + article.location }}</span>
              </div>
            </div>

            <div
              :style="{
                border: '1px solid',
                borderColor: '#ABC9FF',
                opacity: '80%',
                padding: '36px',
                marginTop: '20px',
                marginBottom: '20px',
                minHeight: '40rem',
                borderRadius: '17px',
              }"
            >
              {{ article.content }}
            </div>
            <div
              :style="{
                display: 'flex',
                flexDirection: 'row',
                alignItems: 'center',
                marginBottom: '20px',
                justifyContent: 'center',
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
                type="button"
                @click="moveList"
              >
                글목록
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
                type="button"
                @click="moveModify"
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
                type="button"
                @click="onDeleteArticle"
              >
                글삭제
              </a-button>
            </div>
            <a-divider />
            <div :style="{ paddingTop: '20px', paddingBottom: '20px' }">
              댓글 {{ num }}
            </div>
            <a-form @submit.prevent="onSubmit">
              <div class="comment mb-3 mt-3">
                <a-textarea
                  :rows="3"
                  id="commentArea"
                  v-model:value="sendComment.commentContent"
                  placeholder="댓글을 입력해주세요"
                ></a-textarea>
              </div>
              <div
                :style="{
                  paddingTop: '16px',
                  display: 'flex',
                  justifyContent: 'flex-end',
                }"
              >
                <a-button
                  :style="{
                    color: '#ABC9FF',
                    borderColor: '#ABC9FF',
                    border: '2px solid',
                    fontSize: '15px',
                    fontWeight: 'Bold',
                  }"
                  html-type="submit"
                >
                  댓글 작성
                </a-button>
              </div>
            </a-form>
            <div class="mb-3 mt-3">
              <CommentListItem
                v-for="comment in comments"
                :key="comment.commentNo"
                :comment="comment"
              ></CommentListItem>
            </div>
          </div>
        </div>
      </div>
    </div>
  </a-layout-content>
</template>

<style scoped>
.comment {
  width: 100%;
}
.wholeDiv {
  justify-content: center;
}

.meta-container {
  margin: 3rem 1rem 0 1rem;
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  color: rgb(71 85 105);
}
.sub-container {
  display: flex;
  gap: 1rem;
}
#title {
  font-weight: 900;
  font-size: 50px;
}
</style>

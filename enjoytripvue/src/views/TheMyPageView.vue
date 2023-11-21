<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { getImg } from '@/api/user';
import { listMyArticle } from '@/api/board';
import { useMemberStore } from '@/stores/member';
import { storeToRefs } from 'pinia';
import MyPageItem from '@/components/member/item/MyPageItem.vue';
const memberStore = useMemberStore();
const { getUser: userInfo } = storeToRefs(memberStore);
const router = useRouter();
const url = ref();
const articles = ref({});

const profile = ref({
  userId: userInfo.value.userId,
  profileImg: '',
});

// 필요한 데이터를 위해 API 미리 호출
getProfileImg();
getMyArticle();

function getProfileImg() {
  console.log('프로필 이미지 가져오자!');
  // API 호출
  getImg(
    profile.value.userId,
    (res) => {
      console.log('이거 프로필?', res.data);
      profile.value.profileImg = res.data;
      url.value = profile.value.profileImg;
    },
    (error) => {
      console.log(error);
    }
  );
}

function getMyArticle() {
  console.log('나의 여행 후기 글 리스트 가져오자!');
  // API 호출
  listMyArticle(
    profile.value.userId,
    (res) => {
      console.log('나의 여행 후기 글 리스트?', res.data);
      articles.value = res.data;
    },
    (error) => {
      console.log(error);
    }
  );
}

function moveUserModify() {
  const userId = userInfo.value.userId;
  router.push({ name: 'member-modify', params: { userId } });
}

function moveBoardWrite() {
  router.push({ name: 'article-write' });
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
          <img src="@/assets/mypage.gif" />
          <div
            :style="{
              display: 'flex',
              flexDirection: 'column',
              marginTop: '5px',
              paddingLeft: '10px',
            }"
          >
            <h1>마이페이지</h1>
            <h3>나의 여행 이야기</h3>
          </div>
        </div>
        <a-divider />
        <div
          :style="{
            padding: '10px',
          }"
        >
          <div :style="{ display: 'flex', alignItems: 'flex-start' }">
            <img
              :style="{ width: '200px', height: '200px', borderRadius: '100%' }"
              :src="url"
            />
            <div
              :style="{
                display: 'flex',
                flexDirection: 'column',
                marginTop: '30px',
                paddingLeft: '50px',
              }"
            >
              <h1>{{ userInfo.userId }}님 안녕하세요!</h1>
              <h3>오늘은 어떤 여행을 떠나볼까요?</h3>
              <div
                :style="{
                  display: 'flex',
                  flexDirection: 'row',
                  marginTop: '30px',
                }"
              >
                <a-button
                  :style="{
                    color: '#ABC9FF',
                    borderColor: '#ABC9FF',
                    border: '2px solid',
                    fontSize: '15px',
                    fontWeight: 'Bold',
                    marginRight: '8px',
                  }"
                  type="button"
                  @click="moveUserModify"
                >
                  회원 정보 수정
                </a-button>
                <a-button
                  :style="{
                    color: '#ABC9FF',
                    borderColor: '#ABC9FF',
                    border: '2px solid',
                    fontSize: '15px',
                    fontWeight: 'Bold',
                    marginLeft: '8px',
                  }"
                  type="button"
                  @click="moveBoardWrite"
                >
                  글쓰기
                </a-button>
              </div>
            </div>
          </div>
        </div>
        <a-divider />
        <div :style="{ display: 'flex', flexDirection: 'row' }">
          <MyPageItem
            v-for="article in articles"
            :key="article.articleNo"
            :article="article"
          ></MyPageItem>
          <a-card hoverable :style="{ width: '250px', margin: '20px' }">
            <template #cover>
              <img src="@/assets/dog.jpg" />
            </template>
            <a-card-meta title="Europe Street beat">
              <template #description>안녕</template>
            </a-card-meta>
          </a-card>
        </div>
      </div>
    </div>
  </a-layout-content>
</template>

<style scoped></style>

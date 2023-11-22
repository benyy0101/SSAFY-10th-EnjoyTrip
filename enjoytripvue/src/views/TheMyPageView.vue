<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getImg } from "@/api/user";
import { listMyArticle } from "@/api/board";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import MyPageItem from "@/components/member/item/MyPageItem.vue";
const memberStore = useMemberStore();
const { getUser: userInfo } = storeToRefs(memberStore);
const router = useRouter();
const url = ref();
const articles = ref({});

const profile = ref({
  userId: userInfo.value.userId,
  profileImg: "",
});

// 필요한 데이터를 위해 API 미리 호출
onMounted(() => {
  getProfileImg();
  getMyArticle();
});

function getProfileImg() {
  console.log("프로필 이미지 가져오자!");
  // API 호출
  getImg(
    profile.value.userId,
    (res) => {
      console.log("이거 프로필?", res.data);
      profile.value.profileImg = res.data;
      url.value = profile.value.profileImg;
    },
    (error) => {
      console.log(error);
    }
  );
}

function getMyArticle() {
  console.log("나의 여행 후기 글 리스트 가져오자!");
  // API 호출
  listMyArticle(
    profile.value.userId,
    (res) => {
      console.log("나의 여행 후기 글 리스트?", res.data);
      articles.value = res.data;
      console.log("articles 출력", articles.value[0]);
    },
    (error) => {
      console.log(error);
    }
  );
}

function moveUserModify() {
  const userId = userInfo.value.userId;
  router.push({ name: "member-modify", params: { userId } });
}

function moveBoardWrite() {
  router.push({ name: "article-write" });
}
console.log("articles 출력", articles);
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
                  backgroundColor: '#ff7f50',
                  color: 'aliceblue',
                  borderRadius: '3rem',
                  fontSize: '17px',
                  fontWeight: 'Bold',
                }"
                  type="button"
                  @click="moveUserModify"
                >
                  회원 정보 수정
                </a-button>
                <a-button
                :style="{
                  backgroundColor: '#ff7f50',
                  color: 'aliceblue',
                  borderRadius: '3rem',
                  fontSize: '17px',
                  fontWeight: 'Bold',
                  marginLeft: '20px'
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
        <h2 :style="{marginLeft:'30px', marginTop:'50px'}">나의 여행 후기</h2>
        <a-row :gutter="[16, 16]">     
          <MyPageItem
            v-for="article in articles"
            :key="article.articleNo"
            :article="article"
          ></MyPageItem>
        </a-row>
      </div>
    </div>
  </a-layout-content>
</template>

<style scoped></style>

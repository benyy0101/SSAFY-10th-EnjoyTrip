<script setup>
// import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useMemberStore } from '@/stores/member';
import { storeToRefs } from 'pinia';
const memberStore = useMemberStore();
//const { getUser:userInfo } = storeToRefs(useMemberStore);

const { getUser: userInfo } = storeToRefs(memberStore);

const router = useRouter();

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
              src="@/assets/dog.jpg"
            />
            <div
              :style="{
                display: 'flex',
                flexDirection: 'column',
                marginTop: '30px',
                paddingLeft: '50px',
              }"
            >
              <!-- <h1>{{ userInfo.userId }}님 안녕하세요!</h1> -->
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
          <a-card hoverable :style="{ width: '250px', margin: '20px' }">
            <template #cover>
              <img src="@/assets/dog.jpg" />
            </template>
            <a-card-meta title="Europe Street beat">
              <template #description>안녕</template>
            </a-card-meta>
          </a-card>
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

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { listArticle } from '@/api/board';

import VSelect from '@/components/common/VSelect.vue';

const router = useRouter();

const selectOption = ref([
  { text: '검색조건', value: '' },
  { text: '글번호', value: 'article_no' },
  { text: '제목', value: 'subject' },
  { text: '작성자아이디', value: 'user_id' },
]);

const articles = ref(null);
const currentPage = ref(1);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: '',
  word: '',
});
getArticleList();

const changeKey = (val) => {
  console.log('BoarList에서 선택한 조건 : ' + val);
  param.value.key = val;
};

function getArticleList() {
  console.log('서버에서 글목록 얻어오자!!!', param.value);
  // API 호출
  listArticle(
    param.value,
    (res) => {
      console.log('이거야?', res.data);
      articles.value = res.data;
      //console.log(articles.value);
      //currentPage.value = data.currentPage;
      //totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.log(error);
    }
  );
}
const moveWrite = () => {
  router.push({ name: 'article-write' });
};

const columns = [
  {
    name: 'articleNo',
    dataIndex: 'articleNo',
    key: 'articleNo',
  },
  {
    title: '제목',
    dataIndex: 'subject',
    key: 'subject',
  },
  {
    title: '작성자',
    dataIndex: 'userId',
    key: 'userId',
  },
  {
    title: '장소',
    dataIndex: 'location',
    key: 'location',
  },
  {
    title: '여행 시작 날짜',
    dataIndex: 'startDate',
    key: 'startDate',
  },
  {
    title: '여행 종료 날짜',
    dataIndex: 'endDate',
    key: 'endDate',
  },
  {
    title: '조회수',
    dataIndex: 'hit',
    key: 'hit',
  },
  {
    title: '작성일',
    dataIndex: 'registerTime',
    key: 'registerTime',
  },
];
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
          <img src="@/assets/board_list.gif" />
          <div
            :style="{
              display: 'flex',
              flexDirection: 'column',
              marginTop: '5px',
              paddingLeft: '10px',
            }"
          >
            <h1>여행 후기 목록</h1>
            <h3>여행 이야기를 공유해요!</h3>
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
              flexDirection: 'row',
              justifyContent: 'space-between',
            }"
          >
            <div>
              <a-button
                :style="{
                  color: '#ABC9FF',
                  borderColor: '#ABC9FF',
                  border: '2px solid',
                  fontSize: '15px',
                  fontWeight: 'Bold',
                  margin: '6px',
                }"
                @click="moveWrite"
              >
                글쓰기
              </a-button>
            </div>
            <form
              :style="{
                display: 'flex',
                flexDirection: 'row',
              }"
            >
            <div
                :style="{
                  display: 'flex',
                  flexDirection: 'row',
                  justifyContent:'space-evenly'
                }"
              >
              <VSelect
                :selectOption="selectOption"
                @onKeySelect="changeKey"
              />
              
                <a-input
                  type="text"
                  class="form-control"
                  v-model:value="param.word"
                  placeholder="검색어"
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
                  @click="getArticleList"
                >
                  검색하기
                </a-button>
              </div>
            </form>
          </div>
        </div>
      </div>
      <a-divider />
      <a-table :columns="columns" :data-source="articles">
        <template #headerCell="{ column }">
          <template v-if="column.key === 'articleNo'">
            <span>글번호</span>
          </template>
        </template>
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'subject'">
            <router-link
              :to="{
                name: 'article-view',
                params: { articleno: record.articleNo },
              }"
              class="article-title link-dark"
            >
              {{ record.subject }}
            </router-link>
          </template>
        </template>
      </a-table>
    </div>
  </a-layout-content>
</template>

<style scoped></style>

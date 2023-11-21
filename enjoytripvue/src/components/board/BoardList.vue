<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { listArticle } from '@/api/board';

const router = useRouter();

const selectOption = ref([
  { label: '검색조건', value: '' },
  { label: '글번호', value: 'article_no' },
  { label: '제목', value: 'subject' },
  { label: '작성자아이디', value: 'user_id' },
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
            padding: '20px',
          }"
        >
          <div
            :style="{
              display: 'flex',
              justifyContent: 'space-between',
            }"
          >
            <div class="menu-bar">
              <a-button
                :style="{
                  color: '#ABC9FF',
                  borderColor: '#ABC9FF',
                  border: '2px solid',
                  fontSize: '15px',
                  fontWeight: 'Bold',
                }"
                @click="moveWrite"
              >
                글쓰기
              </a-button>
            </div>
            <form>
              <div class="search-bar">
                <a-select
                  :options="selectOption"
                  @change="changeKey"
                  class="select-bar"
                />
                <a-input
                  type="text"
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
              <div>
                {{ record.subject }}
              </div>
            </router-link>
          </template>
        </template>
      </a-table>
    </div>
  </a-layout-content>
</template>

<style scoped>
.search-bar {
  display: flex;
  gap: 3px;
}
.select-bar {
  min-width: 130px;
}

th {
  text-align: center;
}

* {
  font-family: NPSfontBold;
}
</style>

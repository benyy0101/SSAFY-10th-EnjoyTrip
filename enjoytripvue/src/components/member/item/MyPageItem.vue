<script setup>
import { useRouter } from "vue-router";
const router = useRouter();
const props = defineProps({ article: Object });
const URL = props.article.mainImg;
const title = props.article.subject;
const num = props.article.articleNo;
function moveArticle() {
  router.push({ name: "article-view", params: { articleno: num } });
}
</script>

<template>
  <div style="padding: 12px">
        <a-card
          hoverable
          :style="{ width: '250px', margin: '20px', height: '300px' }" @click="moveArticle"
        >
          <template #cover>
            <img :src="URL" :style="{ display: 'flex', height: '180px' }" />
          </template>
          <a-card-meta :title="title" :style="{ textAlign: 'center' }">
            <template #description>{{ article.content }}</template>
          </a-card-meta>
        </a-card>
  </div>
</template>

<style scoped></style>

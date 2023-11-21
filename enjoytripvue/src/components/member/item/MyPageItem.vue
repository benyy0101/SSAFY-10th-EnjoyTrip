<script setup>
import { ref } from 'vue';

defineProps({ board : Object });
</script>

<template>
  <a-comment :style="{borderColor:'black'}">
    <template #actions>
      <span key="comment-basic-like">
        <a-tooltip title="Like">
          <template v-if="action === 'liked'">
            <LikeFilled @click="like" />
          </template>
          <template v-else>
            <LikeOutlined @click="like" />
          </template>
        </a-tooltip>
        <span style="padding-left: 8px; cursor: auto">
          {{ likes }}
        </span>
      </span>
      <span key="comment-basic-dislike">
        <a-tooltip title="Dislike">
          <template v-if="action === 'disliked'">
            <DislikeFilled @click="dislike" />
          </template>
          <template v-else>
            <DislikeOutlined @click="dislike" />
          </template>
        </a-tooltip>
        <span style="padding-left: 8px; cursor: auto">
          {{ dislikes }}
        </span>
      </span>
    </template>
    <template #author>
      <span :style="{color:'black', fontWeight:'Bold'}">{{ comment.userId }}</span></template>
    <template #avatar>
      <a-avatar src="https://joeschmoe.io/api/v1/random" alt="Han Solo" />
    </template>
    <template #content>
      <p :style="{marginTop:'20px'}">
        {{ comment.commentContent }}
      </p>
    </template>
    <template #datetime>
      {{ comment.commentRegisterTime }}
    </template>
  </a-comment>
</template>

<style scoped>
</style>

<script lang="ts" setup>
import dayjs from 'dayjs';
import { LikeFilled, LikeOutlined, DislikeFilled, DislikeOutlined } from '@ant-design/icons-vue';
import { ref } from 'vue';
import relativeTime from 'dayjs/plugin/relativeTime';
dayjs.extend(relativeTime);

const likes = ref(0);
const dislikes = ref(0);
const action = ref();

const like = () => {
  likes.value = 1;
  dislikes.value = 0;
  action.value = 'liked';
};

const dislike = () => {
  likes.value = 0;
  dislikes.value = 1;
  action.value = 'disliked';
};

defineProps({ comment: Object });
</script>

<template>
  <a-comment>
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
    <template #author>{{ comment.userId }}</template>
    <template #avatar>
      <a-avatar src="https://joeschmoe.io/api/v1/random" alt="Han Solo" />
    </template>
    <template #content>
      <p>
        {{ comment.commentContent }}
      </p>
    </template>
    <template #datetime>
      <span>{{ comment.commentRegisterTime }}</span>
    </template>
  </a-comment>
</template>

<style scoped>
</style>

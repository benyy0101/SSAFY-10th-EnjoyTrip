<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/board";
import { listComment } from "@/api/comment";
import CommentListItem from "@/components/board/item/CommentListItem.vue";

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { articleno } = route.params;

const article = ref({});
const comments = ref(null);

onMounted(() => {
  getArticle();
  getComment();
});

const getArticle = () => {
  // const { articleno } = route.params;
  console.log(articleno + "번글 얻으러 가자!!!");
  // API 호출
  detailArticle(articleno,(res)=>{
    console.log(res);
    article.value = res.data;
  },
  (err)=>{
    console.log(err);
  }
  );
};

const getComment = () => {
  console.log(articleno + "번글 얻으러 가자!!!");
  // API 호출
  listComment(articleno,(res)=>{
    console.log(res);
    comments.value = res.data;
  },
  (err)=>{
    console.log(err);
  }
  );
};

function moveList() {
  router.push({ name: "article-list" });
}

function moveModify() {
  router.push({ name: "article-modify", params: { articleno } });
}

function onDeleteArticle() {
  // const { articleno } = route.params;
  console.log(articleno + "번글 삭제하러 가자!!!");
  // API 호출
  deleteArticle(articleno);
  moveList();
}

</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글보기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-center px-5 mt-3">{{ article.subject }}</h2>
        </div>
        <div class="row">
          <div class="d-flex justify-content-end">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="fw-bold">{{article.userId}}</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.registerTime }} 조회 : {{ article.hit }}
                </span>
              </p>
            </div>
          </div>
          <div class="divider mb-3"></div>
          <div class="text-secondary">
            {{ article.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">
              글목록
            </button>
            <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify">
              글수정
            </button>
            <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteArticle">
              글삭제
            </button>
          </div>
          <div class="col-md-4 align-self-center">댓글</div>
          <div class="mb-3 mt-3">
            <textarea class="comment" v-model="article.commentContent" rows="2"></textarea>
          </div>
          <div class="text-end">
            <button type="submit" class="btn btn-outline-primary">
              댓글 작성
            </button>
          </div>
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
</template>

<style scoped>
.comment{
  width:60%;
}
</style>

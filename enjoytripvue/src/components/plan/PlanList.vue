<script setup>
import { onMounted } from "vue";
import { getPlanList } from "@/api/plan";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

onMounted(() => {
  console.log(userInfo.value.userId)
  getPlanList(
    userInfo.value.userId,
    ({ data }) => {
      console.log("fetchPlanList............", data);
    },
    (err) => {
      console.log(err);
    }
  );
})

</script>

<template>
  <div id="plan-list-container">뭐요</div>
</template>

<style>
#plan-list-container {
  height: 100vh;
  width: 100vw;
}
</style>

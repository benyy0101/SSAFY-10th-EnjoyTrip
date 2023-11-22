<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();
const props = defineProps({ plan: Object });
// const URL = props.plan.mainImg;
// const title = props.plan.subject;
const num = props.plan.planNo;
function movePlan() {
  router.push({ name: 'plan-view', params: { planno: num } });
}
</script>

<template>
  <div style="padding: 12px">
    <a-card
      hoverable
      :style="{
        display: 'flex',
        flexDirection: 'row',
        width: '300px',
      }"
      @click="movePlan"
    >
      <template #cover>
        <img
          src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png"
          :style="{
            borderBottomLeftRadius: '8px',
            borderBottomRightRadius: '8px',
            marginTop: '1px',
            height: '100%',
          }"
        />
        <!-- 나중에 데이터 값 받고나서 :src="URL"로 변경-->
      </template>
      <a-card-meta title="title" :style="{ textAlign: 'center' }">
        <template #description>
          <p>목적지?넣어도 되구 안넣어도 되구</p>
          <p>여행 시작 날짜</p>
          <p>여행 종료 날짜</p>
        </template>
      </a-card-meta>
      <!--
        나중에 데이터 값 받고 이런식으로 넣어주시면 됩니다
      <a-card-meta :title="title" :style="{ textAlign: 'center' }">
        <template #description>{{ plan.location }}</template>
        <template #description>{{ plan.startDate }}</template>
        <template #description>{{ plan.endDate }}</template>
      </a-card-meta>-->
    </a-card>
  </div>
</template>

<style scoped></style>

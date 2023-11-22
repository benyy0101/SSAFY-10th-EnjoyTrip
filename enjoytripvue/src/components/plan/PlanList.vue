<script setup>
import { onMounted, ref } from 'vue';
import { getPlanList } from '@/api/plan';
import { useRouter } from 'vue-router';
import { useMemberStore } from '@/stores/member';
import { storeToRefs } from 'pinia';
import PlanFormItem from '@/components/plan/item/PlanFormItem.vue';
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const router = useRouter();
const plans = ref({});

onMounted(() => {
  console.log(userInfo.value.userId);
  getPlanList(
    userInfo.value.userId,
    ({ data }) => {
      console.log('fetchPlanList............', data);
    },
    (err) => {
      console.log(err);
    }
  );
});
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
            <h1>여행 계획 목록</h1>
            <h3>여행 계획을 세워요!</h3>
          </div>
        </div>
        <a-divider />
        <h2 :style="{ marginLeft: '30px', marginTop: '50px' }">
          나의 여행 계획
        </h2>
        <a-row :gutter="[16, 16]">
          <!-- 예시 아이템 화면! 데이터 값 넣고 나서는 꼭 삭제해야 함!!! -->
          <a-card
            hoverable
            :style="{
              display: 'flex',
              flexDirection: 'row',
              width: '300px',
            }"
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
            </template>
            <a-card-meta title="title" :style="{ textAlign: 'center' }">
              <template #description>
                <p>목적지?넣어도 되구 안넣어도 되구</p>
                <p>여행 시작 날짜</p>
                <p>여행 종료 날짜</p>
              </template>
            </a-card-meta>
          </a-card>
          <PlanFormItem
            v-for="plan in plans"
            :key="plan.planNo"
            :plan="plan"
          ></PlanFormItem>
        </a-row>
      </div>
    </div>
  </a-layout-content>
</template>

<style>
#plan-list-container {
  height: 100vh;
  width: 100vw;
}
</style>

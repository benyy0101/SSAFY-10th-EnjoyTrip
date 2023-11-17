<script setup>
import { ref, watch} from "vue";
import dayjs from "dayjs";
import duration from "dayjs/plugin/duration";
import locale from "ant-design-vue/es/date-picker/locale/ko_KR";
import { usePlanStore } from "@/stores/plan";
import { storeToRefs } from "pinia";

dayjs.locale("ko");
dayjs.extend(duration);

const totalDate = ref([dayjs(), dayjs()]);
const planStore = usePlanStore();
const {planTitle, totalDays} = storeToRefs(planStore);
planTitle.value = '';
totalDays.value = -1;
// watch(
//   planTitle,
//   () => {

//     console.log(planTitle);
//   },
//   { deep: true }
// );

// watch(
//   dates,
//   () => {
//     console.log(dates);
//     provide("totalDays", dates);
//   },
//   { deep: true }
// );

watch(
  totalDate,
  () => {
    //console.log(totalDate.value[0])
    //console.log(totalDate.value[0].diff(totalDate.value[1]));
    totalDays.value =
      parseInt(
        dayjs.duration(totalDate.value[1].diff(totalDate.value[0])).$d.days
      ) + 1;
  },
  { deep: true }
);
</script>
<template>
  <div class="content-wrapper">
    <a-space>
      <img src="@/assets/paperplane.gif" alt="" />
      <h1 class="font-size">여행 계획 짜기</h1>
    </a-space>
    <a-card style="width: 400px">
      <div class="calendar-wrapper">
        <div>
          <h2 class="font-size">제목</h2>
          <a-input
            placeholder="제목을 입력해주세요. (Ex. 제주도 여행)"
            :bordered="false"
            v-model:value="planTitle"
            class="inputWrapper"
          ></a-input>
          <a-divider />
        </div>
        <a-space :size="20" direction="vertical">
          <h2 class="font-size">여행 날짜</h2>
          <a-range-picker
            v-model:value="totalDate"
            :locale="locale"
          ></a-range-picker>
          <div v-if="totalDays > 0">
            총 <span class="daysCon">{{ totalDays }}</span> 일 여행할래요
          </div>
        </a-space>

        <a-divider />
        <div>
          <router-link
            :to="{ name: 'plan-detail', params: { days: 1 } }"
            class="start-button"
          >
            시작하기</router-link
          >
          <!-- <a-button type="primary">시작하기</a-button> -->
        </div>
      </div>
    </a-card>
  </div>
</template>
<style scoped>
.inputWrapper {
  font-size: 20px;
}
.daysCon {
  font-weight: 900;
  color: coral;
  font-size: 20px;
}
.start-button {
  color: coral;
}
.font-size {
  font-weight: 900;
}
.calendar-wrapper {
  display: flex;
  flex-direction: column;
}

.content-wrapper {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  height: 90vh;
  width: 100vw;
  gap: 1rem;
}

* {
  font-family: NPSfontBold;
}
</style>

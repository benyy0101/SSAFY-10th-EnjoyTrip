<script setup>
import OptionItem from "@/components/map/item/OptionItem.vue";
import MapItem from "./item/MapItem.vue";
import SearchList from "./SearchList.vue";
import { ref, watch } from "vue";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import { useMemberStore } from "@/stores/member";
import {insertPlan} from "@/api/plan";
import { useRouter} from 'vue-router'
import dayjs from "dayjs";

const router = useRouter();
const option = ref([]); //지역 검색을 위한 옵션
const attInfo = ref([]); //검색한 지역 옵션
const curPage = ref(1); //현재 페이지 
const percentage = ref(); // 일단 보류
const totalInfo = ref({});  //모든 정보 취합
const currentLoc = ref([]);
const planStore = usePlanStore(); 
const {totalDays, startDate, endDate, planTitle} = storeToRefs(planStore);
const memberStore = useMemberStore();
const {userInfo} = storeToRefs(memberStore);

function confirmOption(data) {
  option.value = data;
}

function confirmList(data) {
  attInfo.value = data;
}
watch(
  option,
  () => {
    
  },
  { deep: true }
);

watch(
  attInfo,
  () => {
    console.log("MapSelect......................attInfo", attInfo.value);
    console.log("MAPselect........................userId", userInfo.value);
  },
  { deep: true }
);

watch(curPage, () => {
  percentage.value = (curPage.value / totalDays.value) * 100;
  console.log(percentage.value);
},
{deep: true}
);

watch(totalInfo, () => {
  console.log("totalInfo................", totalInfo.value);
}
, { deep: true });

const addDayInfo = (data) => {
  //console.log("startDate..........",startDate.value);
  //console.log("curPage.value...............", curPage.value)
  const curDate = startDate.value.add(curPage.value - 1, 'day').format('YYYY-MM-DD').toString();
  totalInfo.value[curDate] = data;
};

const nextPage = (data) => {
  addDayInfo(data);
  curPage.value = parseInt(curPage.value) + 1;
  attInfo.value = [];
};

const prevPage = (data) => {
  addDayInfo(data);
  curPage.value = parseInt(curPage.value) - 1;
  attInfo.value = [];
};

const savePlan = (data)=>{
  addDayInfo(data);
  console.log(totalInfo);
  insertPlan(
    {
      totalDays: totalDays.value,
      userId:userInfo.value.userId,
      startDate: startDate.value,
      endDate: endDate.value,
      title: planTitle.value,
      totalInfo: totalInfo.value
    },
    (response)=>{
      console.log(response);
      router.push({name:'plan-result'});
    },
    (err)=>{
      console.error(err);
    }
  );
}

const saveCurLoc = (data)=>{
  currentLoc.value = data;
  console.log("currentLoc.value.concat...............",currentLoc.value);
}

</script>
<template>
  <div class="mapWrapper">
    <MapItem :options="option" @getAttList="confirmList" :currentLoc="currentLoc"></MapItem>
    <OptionItem @changeOption="confirmOption"></OptionItem>
    <SearchList
      :attInfo="attInfo"
      :curPage="curPage"
      :savedInfo="totalInfo[curPage]"
      @saveCurLoc="saveCurLoc"
      @nextPage="nextPage"
      @prevPage="prevPage"
      @savePlan = "savePlan"
    ></SearchList>
    <a-progress :percent="percentage" :show-info="false" class="progress-bar" />
  </div>
</template>
<style>
.mapWrapper {
  position: relative;
  display: flex;
  overflow: hidden;
}
.progress-bar {
  position: fixed;
}


</style>

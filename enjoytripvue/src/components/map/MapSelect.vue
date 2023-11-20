<script setup>
import OptionItem from "@/components/map/item/OptionItem.vue";
import MapItem from "./item/MapItem.vue";
import SearchList from "./SearchList.vue";
import { ref, watch } from "vue";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import { useMemberStore } from "@/stores/member";
import {insertPlan} from "@/api/plan";

const option = ref([]); //지역 검색을 위한 옵션
const attInfo = ref([]); //검색한 지역 옵션
const curPage = ref(1);
const percentage = ref();
const totalInfo = ref({});
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
    console.log("MapSelect......................option", option.value);
    
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
  percentage.value = (curPage.value / totalDays) * 100;
  console.log(percentage.value);
},
{deep: true}
);

watch(totalInfo, () => {
  console.log("totalInfo................", totalInfo.value);
}
, { deep: true });

const addDayInfo = (data) => {
  //console.log(data);
  totalInfo.value[curPage.value] = data;
  //console.log(totalInfo.value);
};

const nextPage = (data) => {
  addDayInfo(data);
  curPage.value = parseInt(curPage.value) + 1;
  attInfo.value = [];
  //console.log(data);
};

const prevPage = (data) => {
  addDayInfo(data);
  curPage.value = parseInt(curPage.value) - 1;
  attInfo.value = [];
};

const savePlan = ()=>{
  const result = [];
  for(let key in totalInfo){
    result.push(totalInfo[key].contentId);
  }
  insertPlan(
    {
      totalDays: totalDays.value,
      userId:userInfo.value.userId,
      startDate: startDate.value,
      endDate: endDate.value,
      title: planTitle.value,
      totalInfo: result
    },
    ()=>{
      console.log("success");
    },
    (err)=>{
      console.log("errrrrrrrrr");
    }
  );
}
</script>
<template>
  <div class="mapWrapper">
    <MapItem :options="option" @getAttList="confirmList"></MapItem>
    <OptionItem @changeOption="confirmOption"></OptionItem>
    <SearchList
      :attInfo="attInfo"
      :curPage="curPage"
      :savedInfo="totalInfo[curPage]"
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
}
.progress-bar {
  position: fixed;
}
</style>

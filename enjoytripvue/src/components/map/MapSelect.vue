<script setup>
import OptionItem from "@/components/map/item/OptionItem.vue";
import MapItem from "./item/MapItem.vue";
import SearchList from "./SearchList.vue";
import { ref, watch } from "vue";


const option = ref([]);
const attInfo = ref([]);
const setInfo = ref(false);
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
    console.log("MapSelect......................attInfo", option.value);
  },
  { deep: true }
);
</script>
<template>
  <div class="mapWrapper">
    <div v-if="setInfo"></div>
    <MapItem :options="option" @getAttList="confirmList"></MapItem>
    <OptionItem @changeOption="confirmOption"></OptionItem>
    <SearchList :attInfo="attInfo"></SearchList>
    <a-progress :percent="50" :show-info="false" class="progress-bar" />
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

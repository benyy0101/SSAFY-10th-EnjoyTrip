<script setup>
import { watch, ref } from "vue";
import { usePlanStore } from "@/stores/plan";
import { storeToRefs } from "pinia";
import { integer } from "vue-types";

import {
  DoubleRightOutlined,
  HeartOutlined,
  DoubleLeftOutlined,
  CheckOutlined,
} from "@ant-design/icons-vue";

const planStore = usePlanStore();
const { planTitle, totalDays, startDate } = storeToRefs(planStore);

const props = defineProps({
  attInfo: Array,
  curPage: integer,
  savedInfo: Array,
});
//attInfo: 검색한 정보 어레이, curPage:현재 페이지, savedInfo: 저장된 찜한 여행지
const emit = defineEmits(["addDayInfo", "nextPage", "prevPage", "savePlan", 'saveCurLoc']);
const chosenOption = ref([]);
const activeKey = ref("1");
const curDate = ref();

watch(
  () => props.attInfo.value,
  () => {
    if(props.curPage.value - 1 == 0) curDate.value = startDate.value.format('YYYY.MM.DD');
    else{
      curDate.value = startDate.value.add(props.curPage - 1, 'day').format('YYYY.MM.DD');
    }
  },
  { deep: true }
);
watch(
  () => props.curPage.value,
  () => {
    
    
    initSavedOption();
  },
  { deep: true }
);

watch(
  chosenOption,
  ()=>{
    emit('saveCurLoc', chosenOption.value)
  },
  {deep: true}
)

const initSavedOption = () => {
  console.log(props.savedInfo);
  if (props.savedInfo == undefined) {
    chosenOption.value = [];
    return;
  }
  chosenOption.value = props.savedInfo;
};

const toggleButton = (att) => {
  if (att.isSelected == null) {
    att.isSelect = true;
  } else {
    att.isSelected = !att.isSelected;
  }
};

const selectAtt = (att) => {
  att["isSelected"] = true;
  if (chosenOption.value.length == 0 || !chosenOption.value.includes(att))
    chosenOption.value.push(att);
};

const removeAtt = (att) => {
  chosenOption.value = chosenOption.value.filter((item) => {
    return att.title != item.title;
  });
  toggleButton(att);
};

</script>
<template>
  <a-flex vertical>
    <div class="main-info-container">
      <h1 class="plan-title-section">{{ planTitle }}</h1>
      <h3 class="date-section">{{ curPage }}일차 ({{ curDate}})</h3>
    </div>
    <a-tabs v-model:activeKey="activeKey" class="tabContainer">
      <a-tab-pane key="1" tab="검색한 장소">
        <div class="card-section">
          <div v-if="attInfo.length > 0">
            <a-card v-for="option in attInfo" :key="option.title" class="card">
            <a-image
              :src="option.firstImage"
              fallback="data:image/png;base64,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"
              height="200px"
              width="300px"
            ></a-image>
            <h2 class="card-title">{{ option.title }}</h2>
            <a-flex :justify="'space-between'">
              <a-button v-show="!option.isSelected" @click="selectAtt(option)"
                >목록에 추가</a-button
              >
              <a-button
                v-show="option.isSelected"
                @click="selectAtt(option)"
                disabled
                type="primary"
                >추가됨!</a-button
              >
              <a><HeartOutlined /></a>
            </a-flex>
          </a-card>
          </div>
          <div v-else>
            검색해 보세요!
          </div>
        </div>
      </a-tab-pane>

      <a-tab-pane key="2" tab="저장한 장소">
        <div v-if="chosenOption.length > 0">
          <a-card v-for="option in chosenOption" :key="option.title">
          <a-image :src="option.firstImage"></a-image>
          <h2 class="card-title">{{ option.title }}</h2>
          <a-button @click="removeAtt(option)">지우기</a-button>
        </a-card>
        </div>
        <div v-else>
          아직 검색 내용이 없습니다.
        </div>
      </a-tab-pane>
    </a-tabs>
  </a-flex>
  <a-float-button
    v-show="curPage > 1"
    type="primary"
    @click="$emit('prevPage', chosenOption)"
    class="prev-button"
    :style="{
      right: '94px',
    }"
  >
    <template #icon>
      <DoubleLeftOutlined />
    </template>
  </a-float-button>

  <div v-if="curPage < totalDays">
    <a-float-button
      type="primary"
      @click="$emit('nextPage', chosenOption)"
      class="next-button"
      :style="{
        right: '24px',
      }"
    >
      <template #icon>
        <DoubleRightOutlined />
      </template>
    </a-float-button>
  </div>
  <div v-else>
    <a-popover placement="topRight">
      <template #content>
        <div class="popover-text">
          <p>여행계획이</p>
          <p>저장 됩니다!</p>
        </div>
    </template>
      <a-float-button class="next-button" @click="$emit('savePlan', chosenOption)">
        <template #icon>
          <CheckOutlined />
        </template>
      </a-float-button>
    </a-popover>
  </div>
</template>
<style scoped>
.popover-text{
  padding: 10px 10px 0 10px;
  font-weight:900;
  font-size:15px;
}
.main-info-container {
  margin: 2rem 0 0 1rem;
}
.plan-title-section {
  font-size: 30px;
}
.date-section {
  margin: 0 0 0 0.5rem;
  font-weight: 900;
}
.card-title {
  font-weight: 900;
}

.card {
  margin: 0 0 3px 0;
}
.tabContainer {
  height: 100vh;
  width: 20vw;
  margin: 1rem 1rem 0 1rem;
}
.card-section {
  overflow-y: scroll;
  height: 100vh;
}

.card-section::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}

.next-button {
  height: 50px;
  width: 50px;
  font-size: larger;
}

.prev-button {
  height: 50px;
  width: 50px;
  font-size: larger;
  right: 20;
}

* {
  font-family: NPSfontBold;
}
</style>

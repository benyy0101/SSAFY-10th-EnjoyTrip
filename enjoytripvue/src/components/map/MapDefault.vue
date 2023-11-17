<script setup>
import {ref,watch} from 'vue';
import dayjs from 'dayjs';
import duration from 'dayjs/plugin/duration';
import locale from 'ant-design-vue/es/date-picker/locale/ko_KR';
dayjs.locale('ko');
dayjs.extend(duration);
const dates = ref();
const totalDate =ref([dayjs(), dayjs()]);

watch(totalDate,()=>{
    //console.log(totalDate.value[0])
    //console.log(totalDate.value[0].diff(totalDate.value[1]));
    dates.value = parseInt(dayjs.duration(totalDate.value[1].diff(totalDate.value[0])).$d.days) + 1;
}, {deep:true});


</script>
<template>
  <div class="content-wrapper">
    <a-space>
        <img src="@/assets/paperplane.gif" alt="">
        <h1 class="font-size">여행 계획 짜기</h1>
    </a-space>
    <a-card style="width: 400px">
      <div class="calendar-wrapper">
        <a-space :size="20" direction="vertical">
            <h2 class="font-size">여행 날짜</h2>
            <a-range-picker v-model:value="totalDate" :locale="locale" style="{fontSize: 15px;}"></a-range-picker>
            <div v-if="dates>0">총 <span class="daysCon">{{dates}}</span> 일 여행할래요</div>
        </a-space>
        <a-divider />
        <div>
            <h2 class="font-size">제목</h2>
        <a-input placeholder="제목을 입력해주세요. (Ex. 제주도 여행)" :bordered="false" class="inputWrapper"></a-input>
        </div>
        <a-divider />
        <div>
            <a-button type="primary" >시작하기</a-button>
        </div>
        
      </div>
    </a-card>
  </div>
</template>
<style scoped>
.inputWrapper{
 font-size:20px;   
}
.daysCon{
    font-weight:900;
    color: coral;
    font-size: 20px;
}
.font-size{
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

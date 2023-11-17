<script setup>
import {watch } from "vue";
const props = defineProps({ attInfo: Array });

watch(()=> props.attInfo.value,()=>{
    console.log(props.attInfo);
},{deep:true})
</script>
<template>
    <div class="cardSection">
      <a-card v-for="option in attInfo" :key="option.title">
        <a-image :src="option.firstImage"></a-image>
        <p>{{ option.title }}</p>
      </a-card>
    </div>
</template>
<style scoped>
.cardSection {
  height: 100vh;
  width: 20vw;
  background-color: brown;
  overflow-y: scroll;
  display: flex;
  flex-direction: column;
  gap:10px;
  padding:1rem;
}

.cardSection::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Opera*/
}
</style>

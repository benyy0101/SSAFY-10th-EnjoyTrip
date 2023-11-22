<script setup></script>

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
          <img src="@/assets/home.gif" />
          <div
            :style="{
              display: 'flex',
              flexDirection: 'column',
              marginTop: '5px',
              paddingLeft: '10px',
            }"
          >
            <h1>홈</h1>
            <h3>즐거운 여행을 떠나볼까요?</h3>
          </div>
        </div>
        <a-divider />
        <h2>계절별 여행지 추천</h2>
        <a-tabs
          v-model:activeKey="activeKey"
          type="card"
          :style="{ marginTop: '40px' }"
        >
          <a-tab-pane key="spring">
            <template #tab>
              <span>
                <img src="@/assets/spring.png" />
                봄
              </span> </template
            >Content of Tab Pane 1</a-tab-pane
          >
          <a-tab-pane key="summer"
            ><template #tab>
              <span>
                <img src="@/assets/summer.png" />
                여름
              </span> </template
            >Content of Tab Pane 2</a-tab-pane
          >
          <a-tab-pane key="autumn">
            <template #tab>
              <span>
                <img src="@/assets/autumn.png" />
                가을
              </span> </template
            >Content of Tab Pane 1</a-tab-pane
          >
          <a-tab-pane key="winter">
            <template #tab>
              <span>
                <img src="@/assets/winter.png" />
                가을
              </span> </template
            >Content of Tab Pane 1</a-tab-pane
          >
        </a-tabs>
      </div>
    </div>
  </a-layout-content>
  <!-- <a-carousel :after-change="onChange" class="main-page">
    <video src="@/assets/carousel5.mp4" autoplay controls muted></video>
    <video src="@/assets/carousel2.mp4" autoplay controls muted></video>
    <video src="@/assets/carousel3.mp4" autoplay controls muted></video>
    <video src="@/assets/carousel4.mp4" autoplay controls muted></video>
  </a-carousel> -->
</template>

<style scoped>
.carousel-item {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
:deep(.slick-slide) {
  height: 100vh;
  width: 100vw;
  text-align: center;
  background: #364d79;
  overflow: hidden;
}

:deep(.slick-arrow.custom-slick-arrow) {
  width: 25px;
  height: 25px;
  font-size: 25px;
  color: #fff;
  background-color: rgba(31, 45, 61, 0.11);
  transition: ease all 0.3s;
  opacity: 0.3;
  z-index: 1;
}

:deep(.slick-arrow.custom-slick-arrow:before) {
  display: none;
}
:deep(.slick-arrow.custom-slick-arrow:hover) {
  color: #fff;
  opacity: 0.5;
}
</style>

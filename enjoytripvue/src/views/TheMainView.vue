<script setup></script>

<template>
  <a-carousel :after-change="onChange" class="main-page">
    <video src="@/assets/carousel5.mp4" autoplay controls muted></video>
    <video src="@/assets/carousel2.mp4" autoplay controls muted></video>
    <video src="@/assets/carousel3.mp4" autoplay controls muted></video>
    <video src="@/assets/carousel4.mp4" autoplay controls muted></video>
  </a-carousel>
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

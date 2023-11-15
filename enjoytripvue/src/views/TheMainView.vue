<script setup></script>

<template>
  <div class="hero-bg-container">
      <!-- <img src="@/assets/hero.jpeg" alt="" class="hero-bg">
    </div>
  <div class="hero-container">    
    <div class="searchbar-container">
      <input type="text" class="input-box" size="20">
      <div class="search-btn">
         검색하기
      </div>
    </div> -->
  </div>
  
</template>

<style scoped>
.hero-container{
  width: 100%;
  height: 100%;
  position: relative;
}
.searchbar-container{
  position: absolute;
  display: flex;
  flex-direction: row;
  gap:10px;
  top: 300px;
  left: 50%;
  transform:translateX(-50%);
  /* left: 0; 
  right: 0; 
  margin-left: auto; 
  margin-right: auto;  */
  font-size: 30px;
}
.hero-bg{
  width: 100%;
  height: 100%;
  position: absolute;
}


.input-box{
  background: transparent;
  border: none;
  border-bottom: 1px solid;
  outline: none;
  
}

.search-btn{

}

</style>

<script setup>
import {inject } from "vue";
const stateLogin = inject('stateLogin');

function toggleModal(){
  stateLogin.value = !stateLogin.value;
}

</script>

<template>
<nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top">
    <div class="container-fluid">
      <router-link :to="{ name: 'main' }" class="navbar-brand">
        <img src="@/assets/ssafy_logo.png" class="rounded mx-auto d-block" alt="..." />
      </router-link>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarScroll"
        aria-controls="navbarScroll"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarScroll">
        <ul
          class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll"
          style="--bs-scroll-height: 100px"
        >
          <li class="nav-item">
            <a class="nav-link" href="#">마이캠퍼스</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">강의실</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">커뮤니티</a>
          </li>
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              HELP DESK
            </a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="#">공지사항</a></li>
              <li><a class="dropdown-item" href="#">FAQ</a></li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item" href="#">학사규정</a></li>
            </ul>
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'mypage' }" class="nav-link">게시판</router-link>
          </li>
          <li class="nav-item">
            <div @click="toggleModal">
        로그인
      </div>
          </li>
        </ul>
        <!-- <form class="d-flex" role="search">
          <input
            class="form-control me-2"
            type="search"
            placeholder="검색..."
            aria-label="Search"
          />
          <button class="btn btn-outline-success" type="button">search</button>
        </form> -->
        <!-- <ul
          class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll"
          style="--bs-scroll-height: 100px"
        >
          <template v-for="menu in menuList" :key="menu.routeName">
            <template v-if="menu.show">
              <template v-if="menu.routeName === 'user-logout'">
                <li class="nav-item">
                  <router-link to="/" @click.prevent="logout" class="nav-link">{{
                    menu.name
                  }}</router-link>
                </li>
              </template>
              <template v-else>
                <li class="nav-item">
                  <router-link :to="{ name: menu.routeName }" class="nav-link">{{
                    menu.name
                  }}</router-link>
                </li>
              </template>
            </template>
          </template>
        </ul> -->
      </div>
    </div>
  </nav>
<!-- <a-layout-header class="header">
  <div class="leftMenuWrapper">
    <div class="logo">임시 로고</div>
  <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px'}"
      >
        <a-menu-item key="1" class="menuItem">핫플 찾기!</a-menu-item>
        <a-menu-item key="2" class="menuItem">여행계획</a-menu-item>
  </a-menu>
  </div>
  
  <a-menu class="loginSection">
      <div @click="toggleModal">
        로그인
      </div>
      <a-menu-item key="3" class="menuItem">여행계획</a-menu-item>
  </a-menu>
</a-layout-header> -->

</template>

<style scoped>
.header{
  display: flex;
  justify-content: space-between;
}

.logo{
  font-size: large;
  color:crimson;
}

.loginSection{
  color:#FF7F50;
  display: flex;
  justify-content: center;
  gap: 2rem;
}

.leftMenuWrapper{
  display: flex;
  gap:5rem;
}

.site-layout-content {
  min-height: 280px;
  padding: 24px;
  background: #abc9ff;
}


</style>



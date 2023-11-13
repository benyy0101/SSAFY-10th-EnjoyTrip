<script setup>
import {inject } from "vue";
const stateLogin = inject('stateLogin');

function toggleModal(){
  stateLogin.value = !stateLogin.value;
}
</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top">
    <div class="container-fluid flex">
      <router-link :to="{ name: 'main' }" class="navbar-brand">
        <!-- <img src="@/assets/main_logo.png" class="mx-auto d-block" alt="..." height="100"/> -->
        <div>임시 LOGO</div>
      </router-link>
      <!-- <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarScroll"
        aria-controls="navbarScroll"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button> -->
      <div class="collapse navbar-collapse" id="navbarScroll">
        <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll">
          <li class="nav-item">
            <a class="nav-link" href="#">길찾기</a>
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'board' }" class="nav-link"
              >자유 게시판</router-link
            >
          </li>
          <!-- <li class="nav-item dropdown">
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
          </li> -->
        </ul>

        <ul class="navbar-nav me-2 my-lg-0">
          <li class="nav-item">
            <button @click="toggleModal" class="nav-link"
              >로그인</button
            >
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'signup' }" class="nav-link"
              >회원가입</router-link
            >
          </li>
        </ul>
        <form class="d-flex" role="search">
          <input
            class="form-control me-2"
            type="search"
            placeholder="검색..."
            aria-label="Search"
          />
          <button class="btn btn-outline-success" type="button">search</button>
        </form>
      </div>
    </div>
  </nav>
</template>

<style scoped></style>

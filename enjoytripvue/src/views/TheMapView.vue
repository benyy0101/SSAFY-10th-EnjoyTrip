<script setup>

// const sidoOption = ref([
//   { text: "시,도 검색", value: "" },
//   { text: "서울시", value: "article_no" },
//   { text: "", value: "subject" },
//   { text: "작성자아이디", value: "user_id" },
// ]);

// const gugunOption = ref([
//   { text: "구,군 검색", value: "" },
//   { text: "글번호", value: "article_no" },
//   { text: "제목", value: "subject" },
//   { text: "작성자아이디", value: "user_id" },
// ]);

const sidoOption = ref([]);


const {VITE_KAKAO_API_KEY} = import.meta.env;

if (!window.kakao || !window.kakao.maps) {
    const script = document.createElement('script')
    script.type = 'text/javascript'
    script.src =
      `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${VITE_KAKAO_API_KEY}`
    /* eslint를 사용한다면 kakao 변수가 선언되지 않았다고
     * 오류를 내기 때문에 아래 줄과 같이 전역변수임을
     * 알려주어야 한다. */
    /* global kakao */
    script.addEventListener('load', () => {
      kakao.maps.load(() => {
        // 카카오맵 API가 로딩이 완료된 후 지도의 기본적인 세팅을 시작해야 한다.
        initMap()
      })
    })
    document.head.appendChild(script);
}
function initMap() {
    const container = document.getElementById('map')

    const options = {
      // 처음 지도의 위치를 lat, lng(위도, 경도) 값으로 설정한다.
      center: new kakao.maps.LatLng(33.450701, 126.570667),
      level: 3
    }
    const map = new kakao.maps.Map(container, options);
}
</script>

<template>
    <div class="d-flex justify-content-center align-items-center">
        <div></div>
        <div id="map" class="m-2"></div>
    </div>
</template>

<style>
    #map{
        width:80vw;
        height: 80vh;
    }
</style>
<script setup>
import {ref,onMounted, watch} from 'vue';
import {gugunList, sidoList,attList} from '@/api/map'
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
const gugunOption = ref([]);
const attOption = ref([
  {text: "관광 타입", value:""},
  {text: "관광지", value:"12"},
  {text: "문화시설", value:"14"},
  {text: "축제, 공연, 행사", value:"15"},
  {text: "레포츠", value:"28"},
  {text: "숙박", value:"32"},
  {text: "쇼핑", value:"38"},
  {text: "음식점", value:"39"}
]);
const sidoKey = ref();
const gugunKey = ref();
const attKey = ref();
const markers = ref([]);
const positions = ref([]);
let map = '';
//const gugunOption = ref([{ text: "구군선택", value: "" }]);
// watch(sidoOption,()=>{
//   console.log(sidoOption.value);
// })
onMounted(()=>{
  getSidoList();
})

function getSidoList (){
  sidoList(
    ({data}) => {
      console.log(data);
      let options = [];
      options.push({ text: "시도선택", value: "" });
      data.forEach((item) => {
        //console.log(item.sidoCode, item.sidoName);
        options.push({ text: item.sidoName, value: item.sidoCode});
      });
      sidoOption.value = options;
      //console.log(res)
    },
    (err) => {
      console.log(err);
    }
  );
}

watch(sidoKey,()=>{
  gugunList(sidoKey.value,
    ({data}) => {
      //console.log(data);
      let options = [];
      options.push({ text: "구군선택", value: "" });
      data.forEach((item) => {
        //console.log(item.sidoCode, item.sidoName);
        options.push({ text: item.gugunName, value: item.gugunCode});
      });
      gugunOption.value = options;
      //console.log(res)
    },
    (err) => {
      console.log(err);
    }
  )
})

watch(attKey,()=>{
  //console.log(attKey.value);
  //console.log(sidoKey.value);
  //console.log(gugunKey.value);
  const param= {
    content_type_id: attKey.value,
    sido_code: sidoKey.value,
    gugun_code: gugunKey.value
  }

  attList(param, 
  ({data})=>{
    positions.value = [];
    data.forEach((item)=>{
      const obj ={};
      obj.latlng = new kakao.maps.LatLng(item.latitude.toFixed(2), item.longitude.toFixed(2));
      obj.title = item.title;
      positions.value.push(obj);
    })
    console.log(positions.value);
    loadMarkers();
  },
  (err) =>{
    console.log(err);
  }
  );

})

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
    map = new kakao.maps.Map(container, options);
}


const loadMarkers = () => {
  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  deleteMarkers();

  // 마커 이미지를 생성합니다
  //   const imgSrc = require("@/assets/map/markerStar.png");
  // 마커 이미지의 이미지 크기 입니다
  //   const imgSize = new kakao.maps.Size(24, 35);
  //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });
    markers.value.push(marker);
  });

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<template>
  <div class="m-4 d-flex options gap-3 justify-content-center align-items-center w-50">
    <select v-model="sidoKey" class="form-select form-select-sm w-50" @change="onSelect">
    <option
      v-for="option in sidoOption"
      :key="option.value"
      :value="option.value"
      :disabled="option.value === '' ? true : false"
    >
      {{ option.text }}
    </option>
  </select>
  <select v-model="gugunKey" class="form-select form-select-sm w-50" @change="onSelect">
    <option
      v-for="option in gugunOption"
      :key="option.value"
      :value="option.value"
      :disabled="option.value === '' ? true : false"
    >
      {{ option.text }}
    </option>
  </select>
  <select v-model="attKey" class="form-select form-select-sm w-50" @change="onSelect">
    <option
      v-for="option in attOption"
      :key="option.value"
      :value="option.value"
      :disabled="option.value === '' ? true : false"
    >
      {{ option.text }}
    </option>
  </select>
  </div>

  <div class="d-flex justify-content-center align-items-center">
    <div>
        <div id="map" class="m-2"></div>
    </div>    
    
  </div>
</template>

<style>
    #map{
        width:80vw;
        height: 80vh;
    }
    .options{
      position: relative;
      
    }
</style>
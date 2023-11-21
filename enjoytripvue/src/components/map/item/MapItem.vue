<script setup>
import { ref, watch} from "vue";
import {attList} from '@/api/map';
const { VITE_KAKAO_API_KEY } = import.meta.env;
let map;
const markers = ref([]);
const positions = ref([]);
const props = defineProps({ options: Array, currentLoc:Array });
const attInfo = ref([]);
const emit = defineEmits(['getAttList']);

watch(()=> props.currentLoc.value,
()=>{
  console.log("props...............currentLoc", props.currentLoc)
  addDistanceMarker();
},
{deep: true}
)
watch(
  () => props.options.value,
  () => {
    console.log("MapItem....................props: ", props.options);
    const param = {
      sido_code: props.options[0].toString(),
      gugun_code: props.options[1].toString(),
      content_type_id: props.options[2].toString(),
    };
    getAttList(param);
  },
  { deep: true }
);

watch(attInfo,()=>{
    console.log(attInfo.value);
    emit('getAttList', attInfo.value);
}, {deep:true})

const addDistanceMarker = () =>{
  displayCircleDot(clickPosition,);
}

function displayCircleDot(position, distance) {

// 클릭 지점을 표시할 빨간 동그라미 커스텀오버레이를 생성합니다
var circleOverlay = new kakao.maps.CustomOverlay({
    content: '<span class="dot"></span>',
    position: position,
    zIndex: 1
});

// 지도에 표시합니다
circleOverlay.setMap(map);

if (distance > 0) {
    // 클릭한 지점까지의 그려진 선의 총 거리를 표시할 커스텀 오버레이를 생성합니다
    var distanceOverlay = new kakao.maps.CustomOverlay({
        content: '<div class="dotOverlay">거리 <span class="number">' + distance + '</span>m</div>',
        position: position,
        yAnchor: 1,
        zIndex: 2
    });

    // 지도에 표시합니다
    distanceOverlay.setMap(map);
}

// 배열에 추가합니다
dots.push({circle:circleOverlay, distance: distanceOverlay});
}

function getAttList(param){
    console.log("param...................", param);
    attList(
    param,
    ({ data }) => {
      positions.value = [];
      console.log(data);
      attInfo.value = data;
      console.log(attInfo);
      data.forEach((item) => {
        const obj = {};
        obj.latlng = new kakao.maps.LatLng(
          item.latitude.toFixed(2),
          item.longitude.toFixed(2)
        );
        obj.title = item.title;
        positions.value.push(obj);
      });
      console.log(positions.value);
      loadMarkers();
    },
    (err) => {
      console.log(err);
    }
  );
}

const script = document.createElement("script");
  script.type = "text/javascript";
  script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${VITE_KAKAO_API_KEY}`;
  /* eslint를 사용한다면 kakao 변수가 선언되지 않았다고
   * 오류를 내기 때문에 아래 줄과 같이 전역변수임을
   * 알려주어야 한다. */
  /* global kakao */
  script.addEventListener("load", () => {
    kakao.maps.load(() => {
      // 카카오맵 API가 로딩이 완료된 후 지도의 기본적인 세팅을 시작해야 한다.
      initMap();
    });
  });
  document.head.appendChild(script);

function initMap() {
  const container = document.getElementById("map");

  const options = {
    // 처음 지도의 위치를 lat, lng(위도, 경도) 값으로 설정한다.
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
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
    <input class="forAtt" v-model="attInfo" @onChange="$emit('getAttList', attInfo)"/>
    <div id="map" />
</template>
<style>
#map {
  height: 100vh;
  width: 80vw;
  z-index: 0;
}

.forAtt{
    display: none;
}

</style>

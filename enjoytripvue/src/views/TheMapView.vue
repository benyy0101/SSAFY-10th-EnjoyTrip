<script setup>
import { ref, onMounted, watch } from "vue";
import { gugunList, sidoList, attList } from "@/api/map";
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
const attOption = ref([
  { label: "관광 타입", value: "" },
  { label: "관광지", value: "12" },
  { label: "문화시설", value: "14" },
  { label: "축제, 공연, 행사", value: "15" },
  { label: "레포츠", value: "28" },
  { label: "숙박", value: "32" },
  { label: "쇼핑", value: "38" },
  { label: "음식점", value: "39" },
]);

const attKey = ref();
const markers = ref([]);
const positions = ref([]);
const attInfo = ref([]);
const value = ref();
let map = "";
watch(value, () => {
  console.log(value);
});
//const gugunOption = ref([{ text: "구군선택", value: "" }]);
// watch(sidoOption,()=>{
//   console.log(sidoOption.value);
// })
onMounted(() => {
  getSidoList();
});

function getSidoList() {
  sidoList(
    ({ data }) => {
      console.log(data);
      let options = [];
      options.push({ text: "시도선택", value: "" });
      data.forEach((item) => {
        //console.log(item.sidoCode, item.sidoName);
        options.push({
          label: item.sidoName,
          value: item.sidoCode,
          isLeaf: false,
        });
      });
      sidoOption.value = options;
      //console.log(res)
    },
    (err) => {
      console.log(err);
    }
  );
}

watch(attKey, () => {
  //console.log(attKey.value);
  //console.log(sidoKey.value);
  //console.log(gugunKey.value);
  console.log(value.value);
  const param = {
    content_type_id: attKey.value,
    sido_code: value.value[0],
    gugun_code: value.value[1],
  };

  // if (
  //   value.value == null ||
  //   value.value.legth != 2
  // ) {
  //   alert("지역을 먼저 검색해 주세요!");
  // }

  attList(
    param,
    ({ data }) => {
      positions.value = [];
      attInfo.value = data;
      console.log(attInfo.value);
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
});

const { VITE_KAKAO_API_KEY } = import.meta.env;

if (!window.kakao || !window.kakao.maps) {
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
}
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

const loadData = (selectedOptions) => {
  const targetOption = selectedOptions[selectedOptions.length - 1];
  targetOption.loading = true;
  //console.log(targetOption.value);
  // load options lazily
  gugunList(
    targetOption.value,
    ({ data }) => {
      //console.log(data);
      targetOption.loading = false;
      let options = [];
      //options.push({ text: "구군선택", value: "" });
      data.forEach((item) => {
        //console.log(item.sidoCode, item.sidoName);
        options.push({
          label: `${targetOption.label}` + item.gugunName,
          value: item.gugunCode,
        });
      });
      targetOption.children = options;
      //gugunOption.value = options;

      //console.log(res)
      //sidoOption.value = [...sidoOption.value];
    },
    (err) => {
      console.log(err);
    }
  );

  // setTimeout(() => {
  //   targetOption.loading = false;
  //   targetOption.children = [
  //     {
  //       label: `${targetOption.label} Dynamic 1`,
  //       value: 'dynamic1',
  //     },
  //     {
  //       label: `${targetOption.label} Dynamic 2`,
  //       value: 'dynamic2',
  //     },
  //   ];
  //   sidoOption.value = [...sidoOption.value];
  // }, 1000);
};
</script>

<template>
  <div class="contentWrapper">
    <a-space class="searchContainer">
    <div>지역</div>
    <a-cascader
      v-model:value="value"
      :options="sidoOption"
      :load-data="loadData"
      placeholder="검색 시작하기"
      change-on-select
    />
    <div>관광타입</div>
    <a-select
      v-model:value="attKey"
      @change="onSelect"
      :options="attOption"
      class="attOptions"
    />
  </a-space>

  <a-space direction="horizontal">
    <div id="map" />
    <div class="cardSection">
      <a-card title="어쩔건데">
        <p>너가 뭘할수 있는데</p>
      </a-card>
    </div>
  </a-space>
  </div>
</template>

<style>
#map {
  height: 80vh;
  width: 80vw;
  margin: 0 0 0 3rem;
  border-radius: 0.8rem;
}

.cardSection {
  height: 80vh;
  width: 15vw;
  background-color: brown;
  padding: 1rem;
  border-radius: 0.8rem;
}

.attOptions {
  width: 10rem;
}

.searchContainer {
  margin: 1rem 2rem;
  font-weight: 1000;
  font-size: large;
}


</style>

<template>
  <div class="container">
    <div class="col card card-body">
      <div id="deliveryList"></div>
    </div>
  </div>

</template>

<script setup>
import 'tui-grid/dist/tui-grid.css';
import Grid from 'tui-grid';
import { ref, onBeforeUnmount, onMounted } from 'vue';
//import axios from 'axios';
import { ajaxUrl } from '@/utils/commons.js';

let gridInstance = ref();


//let rowData = ref([]);


/*
const deliveryList = async () => {

  let result = await axios.get(`${ajaxUrl}/driver/list`)
                          .catch(err=> console.log(err));

  rowData.value = result.data.contents; 
  console.log(result);
  //gridInstance.value.resetData(rowData.value);
}
  */



// Toast UI Grid 초기화
onMounted(() => {
  if (gridInstance.value) {
    gridInstance.value.destroy(); // 기존 Grid 제거
  }

  gridInstance.value = new Grid({
    el: document.getElementById('deliveryList'),
    scrollX: false,
    scrollY: false,
    bodyHeight: 400,
    pageOptions: {
      useClient: false, // 서버 페이징 사용
      perPage: 10
    },
    columns: [
      { header: '상호명', name: 'companyName' },
      { header: '납기주소', name: 'dedtAddress' },
      { header: '상태', name: 'deliveryStatus', minWidth: 30, width: 'auto' },
    ],
    data: {
      api: {
        readData: { url: `${ajaxUrl}/driver/list`, method: 'GET' },
      },
      contentType: 'application/json',
    },
    showDummyRows: true
  });

  Grid.applyTheme('striped');
})

/*
  // Grid 초기 데이터 로드
  loadData();
});

// 데이터를 다시 불러오는 함수
const loadData = () => {
  if (gridInstance.value) {
    gridInstance.value.readData(1, {}, true);
  }
};

// 필요할 때 버튼 클릭으로 데이터 갱신
const refreshData = () => {
  loadData();
};
*/
// 페이지 이동 시 Grid 제거하여 중복 방지
onBeforeUnmount(() => {
  if (gridInstance.value) {
    gridInstance.value.destroy();
    gridInstance.value = null;
  }
});
</script>

<style>
.container {
  width: 350px;
}
.tui-grid-container {
    width: 100%;
    position: relative;
    border-width: 0;
    clear: both;
    font-size: 11px !important;
    font-family: Arial, '\B3CB\C6C0', Dotum, sans-serif;
}
.card {
  margin: 0 auto;
  width: 350px;
  height: 650px !important;
}
.card-body {
    margin-top: 15%;
}
/*
.tui-grid-row-odd {
  height: 15px !important;
}
.tui-grid-row-even {
  height: 15px !important;
}
.tui-grid-header-area {
  height: 20px !important;
}
#deliveryList tr {
  height: 20px !important;
}*/
</style>
<template>
  <div class="col card card-body">
    <tui-grid 
      ref="GridTable"
      :data="data"
      :columns="columns"
      :rowHeight="10"
    >
    </tui-grid>
  </div>

</template>

<script setup>
//import { TuiGridElement } from "vue3-tui-grid"
//import { OptColumn, OptRow } from 'tui-grid/types/options';
import { onMounted, ref } from 'vue';
const data = ref([
  {
    id: '10012',
    city: 'Seoul',
    country: 'South Korea'
  },
  {
    id: '10013',
    city: 'Tokyo',
    country: 'Japan'    
  },
  {
    id: '10014',
    city: 'London',
    country: 'England'
  },
  {
    id: '10015',
    city: 'Ljubljana',
    country: 'Slovenia'
  },
  {
    id: '10016',
    city: 'Reykjavik',
    country: 'Iceland'
  }
]);
const columns = ref([
  {
    header: 'ID',
    name: 'id'
  },
  {
    header: 'CITY',
    name: 'city',
    editor: 'text'
  },
  {
    header: 'COUNTRY',
    name: 'country'
  }
]);
const GridTable = ref();

onMounted(()=>{
  const grid = GridTable.value;
  grid.applyTheme("striped");
  grid.setLanguage("ko");
  const instance = grid.gridInstance;
  instance.setWidth(250);
  grid.invoke("setWidth", 250);
});
</script>



<style>
.tui-grid-container {
    width: 100%;
    position: relative;
    border-width: 0;
    clear: both;
    font-size: 9px !important;
    font-family: Arial, '\B3CB\C6C0', Dotum, sans-serif;
}
.card {
  margin: 0 auto;
  width: 280px;
  height: 650px !important;
}
.card-body {
    margin-top: 30%;
}
.tui-grid-row {
  height: 10px !important; /* 모든 행 높이를 60px로 설정 */
}

.tui-grid-cell {
  line-height: 10px !important; /* 셀 내부 텍스트 수직 정렬 */
}
</style>
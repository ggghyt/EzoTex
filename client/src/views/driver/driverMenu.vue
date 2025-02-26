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
  instance.setWidth(500);
  grid.invoke("setWidth", 500);
});
</script>

<template>
  <tui-grid 
    ref="GridTable"
    :data="data"
    :columns="columns"
  >
  </tui-grid>
</template>
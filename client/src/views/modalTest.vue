<template>
  <button @click="modalOpen">Modal</button>
  <div>
    
    <div class="modal-wrap" v-show="modalCheck">
      <div class="modal-container">
        
        ...  <!--  모달창 content  -->
        
        <div class="modal-btn">
          <button @click="modalOpen">닫기</button>
          <button @click="modalOpen">확인</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

let modalCheck = ref(false);

const modalOpen = () => {
  console.log('작동');
  modalCheck.value = !modalCheck.value;
  
}

</script>

<style>

  /* dimmed */
  .modal-wrap {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.4);
  }
  /* modal or popup */
  .modal-container {
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 550px;
    background: #fff;
    border-radius: 10px;
    padding: 20px;
    box-sizing: border-box;
  }

</style>
  
  
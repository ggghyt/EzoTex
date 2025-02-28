<template>
  <div class="container">
    <div class="col card card-body">
      <p class="fs-6 fw-bold">| 배송지 목록</p>
      <div>
        
        <div>
          <span>배송주소</span>
          <input v-model="address" type="text" v-on:keyup.enter="searchData" />
        </div>
        <div>
          <span>상호명</span>
          <input v-model="companyName" type="text" v-on:keyup.enter="searchData" />
        </div>
        <span>
						<span>상태</span>
						<select class="search-status" v-model="status">
							<option value="">전체</option>
							<option value="DS01">미완료</option>
							<option value="DS05">완료</option>
						</select>
					</span>
          <span class="btn-gruop">
            <button class="btn btn-success" @click="searchData">검색</button>
            <button class="btn btn-secondary" @click="refreshData">초기화</button>
          </span>
      </div>
      <!--배송지 리스트-->
      <div id="deliveryList"></div>
    </div>
  </div>
    <div class="modal fade" id="registModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header" style="height: 20px;">
            <h5 class="modal-title" id="exampleModalLabel" style="font-size: 15px;">배송지 정보</h5>
          </div>
          <div class="modal-body" style="text-align: center;">
            <div>
              <form>
                <table class="table" action="http://localhost:80/driver/deliveryComplete" method="post" enctype="multipart/form-data">
                    <tbody>
                      <tr>
                        <td scope="row">출고코드</td>
                        <td><input type="text" class="form-control" v-model="readDeliveryCode" name="deliveryCode" disabled></td>
                      </tr>
                      <tr>
                        <td scope="row">납품주소</td>
                        <td><textarea class="form-control" disabled v-model="readAddress" name="dedtAddress" /></td>
                      </tr>
                      <tr>
                        <td scope="row">상호명</td>
                        <td><input type="text" class="form-control" v-model="readCompanyName" name="companyName" disabled></td>
                      </tr>
                      <tr>
                        <td scope="row">납기일</td>
                        <td><input type="text" class="form-control" v-model="readDedt" disabled></td>
                      </tr>
                      <tr>
                        <td scope="row">배송사진</td>
                        <td><input type="file" multiple class="form-control" name="imgUrl"/></td>
                      </tr>
                    </tbody>
                </table>
                <div class="modal-footer regist" style="display: flex; justify-content: center; border-top: none;">
                  <button type="submit" class="btn btn-primary">등록</button>
                  <button type="button" class="btn btn-outline-secondary denyBtn" @click="closeModal" data-bs-dismiss="modal">이전</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
  </div>
  

</template>

<script setup>
import 'tui-grid/dist/tui-grid.css';
import Grid from 'tui-grid';
import { ref, onBeforeUnmount, onMounted } from 'vue';
//import axios from 'axios';
import { ajaxUrl } from '@/utils/commons.js';
import { Modal } from 'bootstrap'; //모달


//
let modalCheck = ref(false);

//그리드
let gridInstance = ref();

//선택된 행 키 번호
let selectedDelivery = ref(null);

//검색 조건
let address = ref(''); //주소
let companyName = ref(''); //회사명
let status = ref(''); //상태

//배송지 단건조회 정보
let readDeliveryCode = ref('');
let readAddress = ref('');
let readCompanyName = ref('');
let readDedt = ref('');

/*
const deliveryList = async () => {

  let result = await axios.get(`${ajaxUrl}/driver/list`)
                          .catch(err=> console.log(err));

  rowData.value = result.data.contents; 
  console.log(result);
  //gridInstance.value.resetData(rowData.value);
}
  */
  
// 모달 열기
const openModal = () => {
  modalCheck.value = true;
  let modalElement = document.getElementById('registModal');
  
  let modal = new Modal(modalElement);
  modal.show();
};


// 모달 닫기
const closeModal = () => {
  let modalElement = document.getElementById('registModal');
  if (modalElement) {
    let modal = Modal.getInstance(modalElement);
    if (modal) {
      modal.hide();
    }
  }
};

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
      { header: '출고 코드', name: 'deliveryCode', hidden: true},
      { header: '상호명', name: 'companyName', minWidth: 60, width: 'auto'  },
      { header: '납기주소', name: 'dedtAddress' },
      { header: '상태', name: 'deliveryStatus', minWidth: 30, width: 'auto' },
      { header: '납기일', name: 'dedt', hidden: true},
    ],
    data: {
      api: {
        readData: { url: `${ajaxUrl}/driver/list`, method: 'GET' },
      },
      contentType: 'application/json',
    },
    columnOptions: {
        resizable: true
      },
    showDummyRows: true
  });

  Grid.applyTheme('striped');

  // 그리드 클릭 이벤트 (focusChange)
  gridInstance.value.on('focusChange', ev => {

    // 이전 선택된 행이 있으면 스타일 제거
    if (selectedDelivery.value !== null) {
      gridInstance.value.removeRowClassName(selectedDelivery.value, 'bg-blue');
    }

    // 새로운 선택 행 스타일 추가
    gridInstance.value.addRowClassName(ev.rowKey, 'bg-blue');

    //선택된 행 키 번호
    selectedDelivery.value = ev.rowKey;

    // 현재 행 데이터 가져오기
    let data = gridInstance.value.getRow(ev.rowKey);


    readDeliveryCode.value = data.deliveryCode;
    readAddress.value = data.dedtAddress;
    readCompanyName.value = data.companyName;
    readDedt.value = data.dedt;
    openModal();
  });

})


// 데이터를 다시 불러오는 함수
const searchData = () => {
  if (gridInstance.value) {
    gridInstance.value.readData(1, {
      dedtAddress : address.value,
      companyName : companyName.value,
      deliveryStatus : status.value
    }, true);
  }
};


// 필요할 때 버튼 클릭으로 데이터 갱신
const refreshData = () => {
  address.value = ''; //주소
  companyName.value = ''; //회사명
  status = ''; //상태  
  searchData();
};

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
.modal-dialog {
  margin-top: 28% !important;
}
.modal-body td {
  font-size: 11px;
  vertical-align: middle;
}
.modal-body {
  padding-bottom: 0 !important;
}
.form-control {
  font-size: 0.7rem !important;
}
</style>
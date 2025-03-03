<template>  
    <!-- 토스트 메시지 -->
    <transition name="fade">
      <div v-show="showToast" class="toast show" :class="toastClass">
        {{ toastMessage }}
      </div>
    </transition>
  <div id="containerApp">
    <div class="col card card-body">
      <p class="fs-6 fw-bold deliveryTitle">| 배송지 목록</p>
      <div class="search-section">
        <div>
          <span>배송주소</span>
          <input class="form-control" v-model="address" type="text" v-on:keyup.enter="searchData" />
        </div>
        <div>
          <span>상호명</span>
          <input class="form-control" v-model="companyName" type="text" v-on:keyup.enter="searchData" />

        </div>
        <div>
          <span>상태</span>
          <select class="search-status form-control" v-model="status">
            <option value="">전체</option>
            <option value="DS01">미완료</option>
            <option value="DS05">완료</option>
          </select>
          <span class="btn-gruop">
            <button class="btn btn-primary" @click="searchData">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="15" height="15">
                <path fill="#ffffff" d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
              </svg>
            </button>
            <button class="btn btn-secondary" @click="refreshData">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="15" height="15">
                <path fill="#ffffff" d="M463.5 224l8.5 0c13.3 0 24-10.7 24-24l0-128c0-9.7-5.8-18.5-14.8-22.2s-19.3-1.7-26.2 5.2L413.4 96.6c-87.6-86.5-228.7-86.2-315.8 1c-87.5 87.5-87.5 229.3 0 316.8s229.3 87.5 316.8 0c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0c-62.5 62.5-163.8 62.5-226.3 0s-62.5-163.8 0-226.3c62.2-62.2 162.7-62.5 225.3-1L327 183c-6.9 6.9-8.9 17.2-5.2 26.2s12.5 14.8 22.2 14.8l119.5 0z"/>
              </svg>
            </button>
          </span>
        </div>
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
                        <td><input type="file" class="form-control" id="profile-upload" accept="image/*" @change="onChangeImg"/></td>
                      </tr>
                    </tbody>
                </table>
                <div class="modal-footer regist" style="display: flex; justify-content: center; border-top: none;">
                  <button type="button" class="btn btn-primary" @click="submitFormBtn">등록</button>
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
import { ref, onBeforeUnmount, onMounted, computed } from 'vue';
import axios from 'axios';
import { ajaxUrl } from '@/utils/commons.js';
import { Modal } from 'bootstrap'; //모달


const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute("content") || "";
const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute("content") || "X-XSRF-TOKEN"; // 기본값

const showToast = ref(false);
const toastMessage = ref("");
const toastType = ref("");

// `computed`를 사용하여 클래스 자동 바인딩
const toastClass = computed(() => {
  return showToast.value ? toastType.value : "";
});

// 토스트 표시 함수
const showToastMessage = (message, type = "success") => {
  
  //console.log("토스트 표시:", message, type);
  toastMessage.value = message;
  toastType.value = type;
  showToast.value = true;

  // 3초 후 자동 사라지도록 설정
  setTimeout(() => {
    //console.log("토스트 숨김 실행됨");
    showToast.value = false;
    type == "success"? closeModal() : '';
  }, 2000);
};


// 성공 & 실패 토스트 호출 예제
//const showSuccessToast = () => showToastMessage("성공적으로 처리되었습니다!", "success");
//const showErrorToast = () => showToastMessage("오류가 발생했습니다!", "error");

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
let fileInput = ref('');    //이미지 파일

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
    console.log(data);

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

//csrf토큰
/*
function getCsrfToken() {
        const csrfCookie = document.cookie
            .split('; ')
            .find(row => row.startsWith('XSRF-TOKEN='));
            
        if (!csrfCookie) {
            console.error(" CSRF 토큰이 없습니다. 쿠키를 확인하세요.");
            return null;
        }
        //console.log('csrf쿠키:', csrfCookie.split('=')[1]);
        return csrfCookie ? csrfCookie.split('=')[1] : null;
    }
*/

//이미지 업로드 했을 때
const onChangeImg = (e) => {
  e.preventDefault();
  if(e.target.files){
    fileInput.value = e.target.files[0];
    console.log('업로드한 이미지:', fileInput.value);
  }
}

//폼 제출 메소드
const submitFormBtn = async() => {

  if(fileInput.value == '') {
    console.log('안됨');
    showToastMessage("사진을 업로드 해 주세요", "error");
    return;
  }

  console.log('작동');
  console.log({deliveryCode: readDeliveryCode.value, dedtAddress: readAddress.value, companyName: readCompanyName.value, dedt: readDedt.value, image: fileInput.value})
  
  //const csrfTokkenVal = getCsrfToken();
  const formData = new FormData();

  formData.append("deliveryCode", readDeliveryCode.value);
  formData.append("dedtAddress", readAddress.value);
  formData.append("companyName", readCompanyName.value);
  formData.append("dedt", readDedt.value);
  formData.append(`image`, fileInput.value);

  console.log('formData:', formData);


  //let result = await axios.post(`${ajaxUrl}/driver/completeDelivery`, formData)
  //                        .catch(err=> console.log(err));

  axios({method: 'post',
         url: `${ajaxUrl}/driver/completeDelivery`,
         data: formData,
         headers: {
              [csrfHeader]: csrfToken // 동적으로 헤더 키 설정
          }
        })
        .then(result => {
          console.log(result);
          if(result == 'success') {
            showToastMessage("배송이 완료되었습니다", "success");
            gridInstance.value.readData();
            //gridInstance.value.removeRow(selectedDelivery.value);
          }
          
        })
        .catch(error => {
            console.error(error);
        });
        


}

</script>

<style>
.modal-dialog {
    height: auto !important;
    position: relative !important;
}
.modal-content {
    overflow: hidden !important;
}
.modal {
    z-index: 1050 !important;
}

.modal-backdrop {
    z-index: 1040 !important; /* 배경 어둡게 하는 부분 */
}

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
  height: 100% !important;
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
    display: inline-block !important;
    width: 84% !important;
    height: 24px;
}
.search-section :nth-of-type(2) input{
  width: 84% !important;
}
.search-section :nth-of-type(3) select{
  font-size: 0.5rem !important;
  width: 30% !important;
  text-align: center;
}
.search-section div:nth-of-type(3) {
  margin-bottom: 10px;
}
.search-section div span {
  font-size: 11px; 
  display: inline-block;
  width: 50px;
}
.search-section div{
  margin-bottom: 5px;
}
.btn-secondary {
    --bs-btn-color: #fff;
    --bs-btn-bg: #b1b1b1 !important;
    --bs-btn-border-color: #b1b1b1 !important;
    --bs-btn-hover-color: #fff;
    --bs-btn-hover-bg: #5c636a;
    --bs-btn-hover-border-color: #565e64;
    --bs-btn-focus-shadow-rgb: 130, 138, 145;
    --bs-btn-active-color: #fff;
    --bs-btn-active-bg: #565e64;
    --bs-btn-active-border-color: #51585e;
    --bs-btn-active-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);
    --bs-btn-disabled-color: #fff;
    --bs-btn-disabled-bg: #6c757d;
    --bs-btn-disabled-border-color: #6c757d;
}
.btn {
    --bs-btn-padding-y: 0.3rem;
    --bs-btn-font-size: 0.7rem;
}
.btn-gruop {
  width: 54% !important;
  text-align: right !important;
}
.btn-gruop button{
  line-height: 0;
  margin-left: 10px;
  width: 40px;
}
textarea {
  height: 50px !important;
}
.toast {
  position: fixed;
  top: 7%;
  left: 50%;
  transform: translateX(-50%);
  padding: 10px 20px;
  border-radius: 5px;
  color: white;
  font-size: 14px;
  z-index: 9999;
}

.success {
  --bs-toast-color: white !important;
  --bs-toast-bg: rgba(52, 177, 170, 0.9) !important;
}

.error {
  --bs-toast-color: white !important;
  --bs-toast-bg: rgba(249, 95, 83, 0.9) !important;
}

/* Vue transition 효과 */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease-in-out;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
.deliveryTitle{
  padding: 5px;
  background-color: #eeeeee;
}
input[type=file] {
  line-height: 1.1;
}
</style>
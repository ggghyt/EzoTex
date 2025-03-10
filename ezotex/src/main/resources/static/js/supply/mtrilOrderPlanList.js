let lclasBox = document.getElementById('lclas');
let sclasBox = document.getElementById('sclas');

let compCodeBox = document.getElementById('companyCode');
let compNameBox = document.getElementById('companyName');
let mtrCodeBox = document.getElementById('mtrilCode');
let mtrNameBox = document.getElementById('mtrilName');

let dueDateBox = document.getElementById('dueDate');
let remarkBox = document.getElementById('remark');

document.addEventListener('DOMContentLoaded', () => { 
  changeClas(lclasBox, sclasBox);
  updateRange('qyMin', 'qyMax');
  if(th != null) updateRange('amountMin', 'amountMax');
  updateRange('dueMin', 'dueMax');
  updateRange('rgsdeMin', 'rgsdeMax');
});

// 엑셀 내보내기 버튼 이벤트
document.getElementById('xlsx').addEventListener('click', () => {
  if(planGrid.getRowCount() == 0) return; // 값이 없으면 미동작
  let fileNm = th != null ? '발주내역전체_' : '발주계획전체_';
  planGrid.export('xlsx', {
    useFormattedValue: true,
    fileName: fileNm + dateFormatter().replaceAll('-','')
  });
});

// 엑셀 내보내기(디테일)
document.getElementById('xlsxDetail').addEventListener('click', () => {
  let fileNm = th != null ? '발주내역_' : '발주계획_';
  planDetailGrid.export('xlsx', {
    useFormattedValue: true,
    fileName: fileNm + document.getElementById('mtrilOrderPlanCode').value
  });
});

// 대분류 onChange 이벤트 등록 함수
const changeClas = function(lclasEle, sclasEle){
    lclasEle.addEventListener('change', () => {
        createOptions(sclasEle, `/product/category/${lclasEle.value}`);     
    });
}

// 업체/자재 선택
compCodeBox.onclick = () => { // 업체코드 input 클릭 시 현재 값으로 모달 호출
    let valueObj = {
        productCode: mtrCodeBox.value
    };
    loadModalGrid('company', valueObj);
};

mtrCodeBox.onclick = () => { // 자재코드 input 클릭 시 현재 값으로 모달 호출
    loadModalGrid('material', { companyCode: compCodeBox.value });
};

/******************** Tui Grid Custom Renderer ********************/
let selected;
// 모달 선택버튼 커스텀 렌더링
class CustomBtnRender {
  constructor(props) {
    let type = props.columnInfo.className; // className으로 자재/업체선택 구분
    const el = document.createElement('button');
    el.type = 'button';
    el.classList = 'btn btn-outline-light btn-sm';
    el.innerText = type == 'plan' ? '상세조회' : '선택';
    
    el.addEventListener('click', () => {
        if(type == 'mtr'){ 
            let selectedMtr = prdGrid.getRow(props.rowKey); // 자재 기본정보 저장
            mtrCodeBox.value = selectedMtr.PRODUCT_CODE;
            mtrNameBox.value = selectedMtr.PRODUCT_NAME;
        } else if(type == 'comp') {
            let selectedComp = companyGrid.getRow(props.rowKey);
            compCodeBox.value = selectedComp.companyCode;
            compNameBox.value = selectedComp.companyName;
        } else if(type == 'plan'){
            selected = planGrid.getRow(props.rowKey);
          
            // 모달에 선택한 정보 표시
            document.getElementById('mtrilOrderPlanCode').value = selected.mtrilOrderPlanCode ? selected.mtrilOrderPlanCode : selected.mtrilOrderCode;
            document.getElementById('chargerName').value = selected.chargerName;
            document.getElementById('rgsde').value = dateFormatter(selected.rgsde);
            dueDateBox.value = dateFormatter(selected.dueDate);
            remarkBox.value = selected.remark;
            
             // 발주서 상태가 대기인 경우에만 발주취소/수정 표시
            if(selected.status == '대기'){
                cancelBtn.style.display = '';
                modifyBtn.style.display = '';
            } 
            loadPlanDetail(selected.mtrilOrderPlanCode ? selected.mtrilOrderPlanCode : selected.mtrilOrderCode);
        }
        if(type == 'mtr' || type == 'comp'){            
            closeAll();
            $('#myModal').modal('hide');
        }
    });
    this.el = el;
    this.render(props);
  }
  getElement() { return this.el; }
  render(props) {   
    this.el.value = props.value;
  }
}

/******************** Tui Grid ********************/ 
let planGrid = th != null ? null : new Grid({
    el: document.getElementById('planGrid'), // 해당 위치에 그리드 출력
    data: { api: { readData: { url: '/mtr/orderPlanList', method: 'GET' }, initialRequest: false } },
    columns: [
        { header: '발주계획코드', name: 'mtrilOrderPlanCode', width: 130, align: 'center' },
        { header: '계획납기일', name: 'dueDate', width: 100, className: 'fw-bold',formatter: (row) => dateFormatter(row.value), align: 'center' },
        { header: '요약', name: 'summary' },
        { header: '발주계획량', name: 'orderQy', align: 'right', width: 120, 
            formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
        { header: '비고', name: 'remark', whiteSpace: 'pre-line' },
        { header: '담당자', name: 'chargerName', width: 100, align: 'center' },
        { header: '등록일', name: 'rgsde', width: 100, formatter: (row) => dateFormatter(row.value), align: 'center' },
        { header: '최종변경일', name: 'updateDate', width: 100, formatter: (row) => dateFormatterNull(row.value), align: 'center' },
        { header: '상태', name: 'status', align: 'center', width: 80,
          renderer: {
            styles: {
              fontWeight: 'bold',
              color: props => props.value == '미발주' ? '#aaa' : '#4b96e6'
            }
          }
         },
        { header: '', name: '', className: 'plan', renderer: { type: CustomBtnRender, options: {}}, width: 100, align: 'center' }
    ],
    rowHeaders: ['rowNum'],
    pageOptions: {
        useClient: true, // 페이징을 위해 필요
        perPage: 10
    },
    showDummyRows: true,
    bodyHeight: 400,
    scrollX: false, // 가로 스크롤
    scrollY: false, // 세로 스크롤
    summary: {
       height: 30,
       position: 'bottom', // or 'top'
       columnContent: {
           mtrilOrderPlanCode: { // 컬럼명
               template: (valueMap) => {
                   return `총 ${numberFormatter(valueMap.cnt)}건`
               }
           }
       }
   }
});

// 자재 선택 그리드
const prdGrid = new Grid({
    el: document.getElementById('prdGrid'), // 해당 위치에 그리드 출력
    data: [],
    columns: [
        { header: '자재코드', name: 'PRODUCT_CODE', width: 150, sortable: true, align: 'center' },
        { header: '자재명', name: 'PRODUCT_NAME', minWidth: 250, whiteSpace: 'pre-line', sortable: true },
        { header: '단위', name: 'UNIT_NAME', width: 150, sortable: true },
        { header: '기본단가', name: 'UNIT_PRICE', width: 150, sortable: true, align: 'right',
                formatter: (row) => numberFormatter(row.value) },
        { header: '', name: '', className: 'mtr', renderer: { type: CustomBtnRender, options: {}}, width: 150, align: 'center' }
    ],
    rowHeaders: ['rowNum'],
    showDummyRows: true,
    bodyHeight: 200,
    pageOptions: {
        useClient: true, // 페이징을 위해 필요
        perPage: 5
    },
    scrollX: false, // 가로 스크롤
    scrollY: false, // 세로 스크롤
    summary: {
         height: 30,
             position: 'bottom', // or 'top'
             columnContent: {
                    productCode: { // 컬럼명
                     template: (valueMap) => {
                         return `총 ${numberFormatter(valueMap.cnt)}건`
                    }
                 }    
             }
     }
});

// 업체 선택 그리드
const companyGrid = new Grid({
    el: document.getElementById('companyGrid'), // 해당 위치에 그리드 출력
    data: [],
    columns: [
        { header: '업체코드', name: 'companyCode', width: 100, sortable: true, align: 'center' },
        { header: '업체명', name: 'companyName', width: 150, ellipsis: true, sortable: true },
        { header: '소재지', name: 'addressInfo', minWidth: 250, ellipsis: true, sortable: true },
        { header: '담당자', name: 'companyCharger', width: 100, sortable: true, align: 'center' },
        { header: '연락처', name: 'companyPhone', width: 100, sortable: true, align: 'center' },
        { header: '', name: '', className: 'comp', renderer: { type: CustomBtnRender, options: {}}, width: 100, align: 'center' }
    ],
    columnOptions: { resizable: true },
    rowHeaders: ['rowNum'],
    showDummyRows: true,
    bodyHeight: 200,
    pageOptions: {
        useClient: true, // 페이징을 위해 필요
        perPage: 5
    },
    scrollX: false, // 가로 스크롤
    scrollY: false, // 세로 스크롤
    summary: {
         height: 30,
         position: 'bottom', // or 'top'
         columnContent: {
                companyCode: { // 컬럼명
                 template: (valueMap) => {
                     return `총 ${numberFormatter(valueMap.cnt)}건`
                 }
             }
         }
     }
});

// 발주계획 상세조회 그리드
let planDetailGrid = th != null ? null : new Grid({
    el: document.getElementById('planDetailGrid'), // 해당 위치에 그리드 출력
    data: [],
    columns: [
        { header: '자재코드', name: 'productCode', sortable: true, align: 'center' },
        { header: '자재명', name: 'productName', sortable: true },
        { header: '색상', name: 'productColor', sortable: true, formatter: (row) => row.value == 'null' ? '' : row.value },
        { header: '발주계획량', name: 'orderQy', sortable: true, align: 'right', editor: 'text', disabled: true,
          formatter: (row) => numberFormatter(row.value) },
        { header: '단위', name: 'unitName', sortable: true },
        { header: '업체코드', name: 'companyCode', sortable: true, align: 'center' },
        { header: '업체명', name: 'companyName', sortable: true },
    ],
    rowHeaders: ['rowNum'],
    scrollX: false, // 가로 스크롤
    scrollY: true, // 세로 스크롤
    showDummyRows: true,
    bodyHeight: 200,
    summary: {
         height: 30,
         position: 'bottom', // or 'top'
         columnContent: {
              productCode: { // 컬럼명
                   template: (valueMap) => {
                       return `총 ${numberFormatter(valueMap.cnt)}건`
                   }
              },
              orderQy: { // 컬럼명
                   template: (valueMap) => {
                       return `합계: ${numberFormatter(valueMap.sum)}`
                   }
              }
         }
    }
});

/******************** Tui Grid 출력 ********************/
// 모달 El
let modalTitle = document.getElementById('modalTitle');
let writeBtn = document.getElementById('writeBtn');
let cancelBtn = document.getElementById('orderCancelBtn');
let mtrListDiv = document.getElementById('materialList');
let compListDiv = document.getElementById('companyList');
let planDetailDiv = document.getElementById('planDetailList');

// 전체 조회
function loadPlanList(obj, uri){
    let {status, ...others} = obj;
    let query = new URLSearchParams(others);
      
    // 배열값이 있는 경우 쿼리스트링을 직접 만들어야 정상 출력
    if(status) status.forEach(code => query += `&status=${code}` );
    
    fetch(`${uri}?${query}`)
    .then(response => response.json())
    .then(result => {
        let data = result.data.contents;
        console.log(data);
        
        // 데이터 입력 
        planGrid.resetData(data);
    });
}

// 검색 적용
document.getElementById('orderSearchBtn').addEventListener('click', orderSearch);

function orderSearch(){
    let checkedStatus = document.querySelectorAll('input[name="status"]:checked');
    checkedStatus = Array.from(checkedStatus).map(ele => ele.value); // 가상의 노드들을 복사하여 배열처리
    
    let dto = {
        companyCode: compCodeBox.value,
        companyName: compNameBox.value,
        productCode: mtrCodeBox.value,
        productName: mtrNameBox.value,
        mtrilOrderPlanCode: document.getElementById('orderPlanCode').value,
        remark: document.getElementById('scRemark').value,
        status: checkedStatus,
        qyMin: document.getElementById('qyMin').value,
        qyMax: document.getElementById('qyMax').value,
        dueMin: document.getElementById('dueMin').value,
        dueMax: document.getElementById('dueMax').value,
        rgsdeMin: document.getElementById('rgsdeMin').value,
        rgsdeMax: document.getElementById('rgsdeMax').value,
        chargerName: document.getElementById('scChargerName').value,        
    };
    if(th == null) loadPlanList(dto, '/mtr/orderPlanList');
}

// 선택한 발주계획서의 상세내역 조회
function loadPlanDetail(mtrilOrderPlanCode){
  fetch(`/mtr/orderPlan/${mtrilOrderPlanCode}`)
  .then(response => response.json())
  .then(result => {
    let data = result;
    console.log('상세내역', data);
    planDetailGrid.resetData(data);
    
    planDetailDiv.style.display = '';
    modifyBtn.style.display = '';
    writeBtn.style.display = '';
    $('#myModal').modal('show');
    planDetailGrid.refreshLayout(); 
  });
}

// 모달의 선택 목록 출력 (업체/자재 선택)
function loadModalGrid(type, obj){ // type: 'material' or 'company'
  let query = new URLSearchParams(obj);
    
    fetch(`/mtr/${type}List?${query}`)
    .then(response => response.json())
    .then(result => {
    let data = result.data.contents;
    console.log(data);
    
    let gridArr = [
      {grid: prdGrid, div: mtrListDiv},
      {grid: companyGrid, div: compListDiv},
      {grid: planDetailGrid, div: planDetailDiv}
    ];
    let gridArrIdx;
    if(type == 'material'){
        modalTitle.innerText = '자재 선택';
        gridArrIdx = 0;
    } else if(type == 'company'){
        modalTitle.innerText = '업체 선택';
        gridArrIdx = 1;
    } else {
        modalTitle.innerText = th == null ? '발주계획서 상세조회' : '발주서 상세조회';
        gridArrIdx = 2;
    }
    // 데이터 입력 
    gridArr[gridArrIdx].grid.resetData(data);
    gridArr[gridArrIdx].div.style.display = '';
    // 모달 표시
    $('#myModal').modal('show');
    if(gridArrIdx == 0) prdGrid.refreshLayout();
    else if(gridArrIdx == 1) companyGrid.refreshLayout();
    else planDetailGrid.refreshLayout();
  });
}

// 자재 목록 검색 적용
document.getElementById('prdSearchBtn').addEventListener('click', () => {
    let dto = {
        productCode: document.getElementById('productCode').value,
        productName: document.getElementById('productName').value,
        lclas: document.getElementById('lclas').value,
        sclas: document.getElementById('sclas').value,
        companyCode: compCodeBox.value
    };
    loadModalGrid('material', dto);
});

// 업체 목록 검색 적용
document.getElementById('comSearchBtn').addEventListener('click', () => {
    let dto = {
        companyCode: document.getElementById('scCompanyCode').value,
        companyName: document.getElementById('scCompanyName').value,
        address: document.getElementById('address').value,
        productCode: mtrCodeBox.value
    };
    loadModalGrid('company', dto);
});

// 엔터키 즉시 검색
document.getElementById('materialList').addEventListener('keyup', e => {
    if(e.key == 'Enter') document.getElementById('prdSearchBtn').dispatchEvent(new Event('click'));
});
document.getElementById('companyList').addEventListener('keyup', e => {
    if(e.key == 'Enter') document.getElementById('comSearchBtn').dispatchEvent(new Event('click'));
});
document.getElementById('planForm').addEventListener('keyup', e => {
    if(e.key == 'Enter') document.getElementById('orderSearchBtn').dispatchEvent(new Event('click'));
});

// 발주계획서 => 발주서 작성 이동
writeBtn.addEventListener('click', () => {
    location.href = '/mtr/mtrOrder?mtrilOrderPlanCode=' + selected.mtrilOrderPlanCode 
                  + '&dueDate=' + dateFormatter(selected.dueDate);
});

// 발주계획서 수정
let isModify;
let modifyBtn = document.getElementById('modifyBtn');
let modifyConfirmBtn = document.getElementById('modifyConfirmBtn');
let closeBtn = document.getElementById('closeBtn');

// 수정되었는지 값 확인용
let prevDueDate;
let prevRemark;

modifyBtn.addEventListener('click', () => {
    closeBtn.innerText = '취소';
    prevDueDate = dueDateBox.value;
    prevRemark = remarkBox.value;
    modifyMode(true);
})

modifyConfirmBtn.addEventListener('click', modifyRows);

function modifyRows(){
    let updated = planDetailGrid.getModifiedRows().updatedRows;
    if(updated.length == 0 && dueDateBox.value == prevDueDate && remarkBox.value == prevRemark){
      failToast('변경된 값이 없습니다.');
      modifyMode(false);
      return;
    }
    if(dueDateBox.value == ''){
        failToast('납기일이 입력되지 않았습니다.');
        dueDateBox.value = prevDueDate;
        return;
    }
    
    let detailArr = updated.map(data => {
        return {
            productCode: data.productCode,
            productColor: data.productColor,
            orderQy: data.orderQy
        };
    });
    let headerObj = {
        dueDate: dueDateBox.value,
        remark: remarkBox.value
    };
    if(th == null){
      headerObj.mtrilOrderPlanCode = selected.mtrilOrderPlanCode;
    } else headerObj.mtrilOrderCode = selected.mtrilOrderCode;
    
    let uri = th == null ? '/mtr/mtrOrderPlan' : '/mtr/mtrOrder'; 
    
    fetch(uri, {
        method: 'PUT',
        headers: {...headers, 'Content-Type': 'application/json'},
        body: JSON.stringify({headerObj, detailArr})
    })
    .then(response => response.json())
    .then(result => {
        if(result == true){ 
          successToast('작업이 완료되었습니다.');
          // 변경사항 반영
          let updatedData = planDetailGrid.getData();
          planDetailGrid.resetData(updatedData);
          // 헤더 변경사항 반영
          selected.updateDate = dateFormatter();
          selected.remark = remarkBox.value;
          selected.dueDate = dueDateBox.value;
          selected.orderQy = planDetailGrid.getSummaryValues('orderQy').sum; // 수량합계 재계산
          if(selected.amount) selected.amount = planDetailGrid.getSummaryValues('amount').sum; // 금액합계 재계산
          planGrid.setRow(selected.rowKey, selected);
        }
        else failToast('작업을 실패했습니다.');
    });
    
    closeBtn.innerText = '닫기';
    modifyMode(false);
}

// 입력값 유효성검사
const afterChangeListener = ev => {
  let changed = ev.changes[0];
  let rowKey = changed.rowKey;
  
  let row = planDetailGrid.getRow(rowKey);
  let val = changed.value;
  if(isNaN(val) || val < 0){ // 입력값이 유효하지 않은 경우
    if(isNaN(val)) failToast('입력값은 문자가 들어갈 수 없습니다.');
    else failToast('입력값은 음수가 될 수 없습니다.');
    val = changed.prevValue;
  }
  
  row.orderQy = val;
  if(row.amount) row.amount = row.unitPrice * row.orderQy; // 금액 있으면 재계산
  planDetailGrid.setRow(rowKey, row);
}
if(th == null) planDetailGrid.on('afterChange', afterChangeListener)

// 수정모드 전환
function modifyMode(boolean){
    isModify = boolean;
    modifyBtn.style.display = isModify ? 'none' : '';
    modifyConfirmBtn.style.display = isModify ? '' : 'none';
    if(th == null) writeBtn.style.display = isModify ? 'none' : '';
    else cancelBtn.style.display = isModify ? 'none' : '';
    dueDateBox.readOnly = !isModify;
    remarkBox.readOnly = !isModify;
    isModify ? planDetailGrid.enableColumn('orderQy') : planDetailGrid.disableColumn('orderQy');
    if(isModify) dueDateBox.min = dateFormatter(); // 오늘 날짜로 제한
}

// 모달 내부 닫기버튼 동작 (모두 숨김)
closeBtn.addEventListener('click', () => closeAll());
document.querySelector('.btn-close').addEventListener('click', () => closeAll());

function closeAll(){
    if(isModify){
      modifyMode(false);
      closeBtn.innerText = '닫기';
      return;
    }
    mtrListDiv.style.display = 'none';
    compListDiv.style.display = 'none';
    planDetailDiv.style.display = 'none';
    writeBtn.style.display = 'none';
    modifyBtn.style.display = 'none';
    cancelBtn.style.display = 'none';
    $('#myModal').modal('hide');
}

// 모달 숨김 시 닫기 버튼과 동일한 효과
document.addEventListener('hide.bs.modal', () => closeAll());
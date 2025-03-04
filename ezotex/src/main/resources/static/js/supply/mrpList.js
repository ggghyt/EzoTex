document.addEventListener('DOMContentLoaded', () => {
  mrpGrid.setRequestParams({supplyYear: new Date().getFullYear()});
  mrpGrid.reloadData();
  makeQuantityTag();
});

// 엑셀 내보내기 버튼 이벤트
document.getElementById('xlsx').addEventListener('click', () => {
  if(mrpGrid.getRowCount() == 0) return; // 값이 없으면 미동작
  let selIdx = document.getElementById('season').selectedIndex;
  
  let monthVal = ('0' + document.getElementById('scSupplyMonth').value).slice(-2);
  
  let fileNm = [
    document.getElementById('scSupplyYear').value,
    monthVal,
    document.getElementById('season')[selIdx].innerText ];
  let filtered = fileNm.filter(nm => nm != '' && nm != '0'); // 공백은 제외
  
  mrpGrid.export('xlsx', {
    useFormattedValue: true,
    fileName: '자재소요계획_' + filtered.join('_')
  });
});

/******************** Tui Grid ********************/
// 자재소요계획 그리드
const mrpGrid = new Grid({
    el: document.getElementById('mrpGrid'), // 해당 위치에 그리드 출력
    data: {
      api: { readData: { url: '/supply/listMrp', method: 'GET' }, initialRequest: false }
    },
    columns: [
        { header: '자재코드', name: 'productCode', width: 100, rowSpan: true, align: 'center', className: 'pointer bg-light' },
        { header: '자재명', name: 'productName', rowSpan: true, align: 'center', className: 'pointer bg-light' },
        { header: '색상', name: 'productColor', align: 'center', rowSpan: true },
        { header: '수량', name: 'supplyQy', width: 150, align: 'right',
           formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
        { header: '단위', name: 'unitName', width: 80 },
        { header: '합계', name: 'totalQy', width: 150, align: 'right', rowSpan: true,
           formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
        { header: '날짜', name: 'supplyDate', formatter: (row) => dateFormatterNull(row.value), align: 'center' }
    ],
    scrollX: false, // 가로 스크롤
    scrollY: false, // 세로 스크롤
    summary: {
         height: 40,
         position: 'top',
         columnContent: {
            productCode: { // 컬럼명
                   template: (valueMap) => {
                       return `총 ${valueMap.cnt}건`
                   }
            },
            supplyQy: { // 컬럼명
                   template: (valueMap) => {
                       console.log(valueMap);
                       return `총계: ${numberFormatter(valueMap.sum)}`
                   }
            }
         }
     }
});

// 공급계획된 제품 목록 그리드
const planDetailGrid = new Grid({
    el: document.getElementById('planDetailGrid'), // 해당 위치에 그리드 출력
    data: [],
    columns: [
        { header: '제품코드', name: 'productCode', sortable: true },
        { header: '제품명', name: 'productName', whiteSpace: 'pre-line', sortable: true },
        { header: '공급계획코드', name: 'supplyPlanCode', sortable: true },
        { header: '공급계획일자', name: 'supplyDate', sortable: true, formatter: (row) => dateFormatterNull(row.value) },
        { header: '공급계획량', name: 'supplyQy', sortable: true, align: 'right', formatter: (row) => numberFormatter(row.value) }
    ],
    rowHeaders: ['rowNum'],
    scrollX: false, // 가로 스크롤
    scrollY: true, // 세로 스크롤
    bodyHeight: 100,
    summary: {
         height: 30,
         position: 'bottom', // or 'top'
         columnContent: {
              productCode: { // 컬럼명
                   template: (valueMap) => {
                       return `총 ${valueMap.cnt}건`
                   }
              },
              supplyQy: {
                   template: (valueMap) => {
                       return `합계: ${numberFormatter(valueMap.sum)}`
                   }
              }
         }
    }
});

// 선택한 제품의 옵션 피벗 그리드
const optionGrid = new Grid({
    el: document.getElementById('optionGrid'), // 해당 위치에 그리드 출력
    data: [],
    columns: [],
    scrollX: true, // 가로 스크롤
    scrollY: true, // 세로 스크롤
    bodyHeight: 120
});

/******************** Tui Grid 출력 ********************/ 
// 검색조건으로 조회
document.getElementById('planSearchBtn').addEventListener('click', () => {  
  let dto = {
    supplyYear: document.getElementById('scSupplyYear').value,
    supplyMonth: document.getElementById('scSupplyMonth').value,
    season: document.getElementById('season').value
  };
  mrpGrid.setRequestParams(dto); // 조회 조건 전달
  mrpGrid.reloadData(); // 그리드 재출력 (readData)
});

// 엔터키로 검색
document.getElementById('mrpForm').addEventListener('keyup', e => {
    if(e.key == 'Enter') document.getElementById('planSearchBtn').dispatchEvent(new Event('click'));
});

// 제품코드별 합계 출력
let loadSuccess = false; // reloadData 이후 합계 입력하기 위함.
mrpGrid.on('successResponse', () => loadSuccess = true);

mrpGrid.on('onGridUpdated', () => { 
    if(loadSuccess){
        getTotalQy();
        loadSuccess = false; // successResponse, onGridUpdated 갭 차이로 무한루프 방지
    }
});

function getTotalQy(){
    // 제품코드별 합계 계산하여 데이터 입력
    let data = mrpGrid.getData();
    let sumObj = {};
    data.forEach(obj => {
        let value = sumObj[obj.productCode] ? sumObj[obj.productCode] : 0;
        sumObj[obj.productCode] = value + obj.supplyQy;
    });
    data.forEach(obj => obj.totalQy = sumObj[obj.productCode]);
    mrpGrid.resetData(data);
}



/******************** 모달 동작 ********************/ 
// 제품코드 or 제품명 클릭 시 공급계획서 조회
mrpGrid.on('focusChange', async ev => {
  if(ev.columnName == 'productCode' || ev.columnName == 'productName'){
    let prdCode = mrpGrid.getRow(ev.rowKey).productCode;
    loadPlanDetail(prdCode);
  }
})
    
    
// 선택한 공급계획서의 상세내역 조회
function loadPlanDetail(productCode){
  fetch(`/supply/supplyPlan?productCode=${productCode}`)
  .then(response => response.json())
  .then(result => {
    let data = result.data.contents;
    console.log('상세내역', data);
    planDetailGrid.resetData(data);
    
    $('#myModal').modal('show');
    planDetailGrid.refreshLayout(); 
  });
}

let selectedPrdBox = document.getElementById('selectedPrd'); // 제품별 정보 표시 div
// 선택된 행 강조 & 정보 가져오기
planDetailGrid.on('focusChange', async ev => {
  // 배경색 클래스 적용
  planDetailGrid.removeRowClassName(ev.prevRowKey, 'bg-blue'); // 이전 선택 행 배경색 삭제
  planDetailGrid.addRowClassName(ev.rowKey, 'bg-blue'); // 선택된 행 배경색 추가
  
  // 선택한 정보 가져오기
  let selectedPrd = planDetailGrid.getRow(ev.rowKey);  
  document.getElementById('selectedPrdCode').value = selectedPrd.productCode;
  document.getElementById('selectedPrdName').value = selectedPrd.productName;
  
  loadPrdPivot(selectedPrd);
});

// 선택한 공급계획서-제품의 옵션별 집계 조회
function loadPrdPivot(selectedPrd){
  let query = new URLSearchParams({
    productCode: selectedPrd.productCode, 
    supplyPlanCode: selectedPrd.supplyPlanCode
  });
  
  fetch(`/supply/supplyPlanPivot?${query}`)
  .then(response => response.json())
  .then(result => {
    let data = result.data.contents;
    let columns = [];
    let qyColumn = { header: "수량", name: "SUPPLY_QY", align: "right", 
                     formatter: (row) => row.value == 0 || row.value == null ? null : numberFormatter(row.value) };
    
    if(data == null){ 
      // 옵션이 없는 단일제품일 경우 수량 그대로 표시
      optionGrid.resetData([{ SUPPLY_QY: selectedPrd.supplyQy }]);
      optionGrid.setColumns([qyColumn]);
    } else if(data[0].SUPPLY_QY){  
      // 옵션이 사이즈/색상 중 하나만 있는 경우 목록형태로 반환
      if(data[0].PRODUCT_COLOR) columns.push({ header: "색상", name: "PRODUCT_COLOR", align: "center" });
      else columns.push({ header: "사이즈", name: "SIZE_NAME", align: "center" });
      columns.push(qyColumn);
    } else {
      // 사이즈/색상 모두 있으면 피벗 형태로 반환
      // data 형식: [{"PRODUCT_COLOR": "BLACK", "110": 0, "S": 0, "L": 0, "M": 0, ...}, ...]
      columns.push({ header: "색상/사이즈", name: "PRODUCT_COLOR", width: 80, align: "center" });
      let sizeNmArr = [];
      
      // pivot으로 가져온 사이즈명을 분리하여 컬럼 지정
      data.forEach((row, idx) => {
        let { PRODUCT_COLOR, rowKey, sortKey, uniqueKey, rowSpanMap, _attributes, _disabledPriority, _relationListItemMap, 
              ...sizeNms } = row;
        if(idx == 0) sizeNmArr = Object.keys(sizeNms);
        sizeNmArr.forEach((nm) => {
          let commonCode = getSizeCommonCode(nm);
          row[commonCode] = row[nm]; // 사이즈명으로 가져온 수량데이터를 사이즈코드에 입력
        });
      });
      
      sizeNmArr.sort((current, next) => { // 공통코드 기준 오름차순 정렬
        return getSizeCommonCode(current).substr(2,3) - getSizeCommonCode(next).substr(2,3);
      }).forEach((nm) => { // 컬럼 생성
        let newCol = { header: nm, name: getSizeCommonCode(nm), align: 'right', 
                       formatter: (row) => row.value == 0 || row.value == null ? null : numberFormatter(row.value) };
        columns.push(newCol);     
      });
    }
    
    optionGrid.resetData(data); // 데이터 입력
    optionGrid.setColumns(columns); // 컬럼 입력
  });
}

// 모달 내부 닫기버튼 동작 (모두 숨김)
document.getElementById('closeBtn').addEventListener('click', () => closeAll());
document.querySelector('.btn-close').addEventListener('click', () => closeAll());

function closeAll(){
    optionGrid.resetData([]);
    optionGrid.setColumns([]);
    document.getElementById('selectedPrdCode').value = '';
    document.getElementById('selectedPrdName').value = '';
}
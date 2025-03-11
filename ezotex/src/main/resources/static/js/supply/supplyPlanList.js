let lclasBox = document.getElementById('lclas');
let sclasBox = document.getElementById('sclas');
let prdCodeBox = document.getElementById('scProductCode');
let prdNameBox = document.getElementById('scProductName');
let productList = document.getElementById('productList');
let detailList = document.getElementById('detailList');

// 선택한 공급계획서의 상세조회 내용
let supplyPlanCode = document.getElementById('supplyPlanCode');
let supplyYear = document.getElementById('supplyYear');
let season = document.getElementById('season');
let chargerName = document.getElementById('chargerName');
let selectedPrdCode = document.getElementById('selectedPrdCode');
let selectedPrdName = document.getElementById('selectedPrdName');
let rgsde = document.getElementById('rgsde');
let remark = document.getElementById('remark');

document.addEventListener('DOMContentLoaded', () => {
  changeClas(lclasBox, sclasBox);
  makeQuantityTag();
  updateRange('supplyQyMin', 'supplyQyMax');
  updateRange('supplyDateMin', 'supplyDateMax');
  updateRange('rgsdeMin', 'rgsdeMax');
});

// 엑셀 내보내기 버튼 이벤트
document.getElementById('xlsx').addEventListener('click', () => {
  if(supplyGrid.getRowCount() == 0) return; // 값이 없으면 미동작
  supplyGrid.export('xlsx', {
    useFormattedValue: true,
    fileName: '공급계획전체_' + dateFormatter().replaceAll('-','')
  });
});

// 엑셀 내보내기(디테일)
document.getElementById('xlsxDetail').addEventListener('click', () => {
  planDetailGrid.export('xlsx', {
    useFormattedValue: true,
    fileName: '공급계획_' + document.getElementById('supplyPlanCode').value
  });
});

// 대분류 onChange 이벤트 등록 함수
const changeClas = function(lclasEle, sclasEle){
    lclasEle.addEventListener('change', () => {
        createOptions(sclasEle, `/product/category/${lclasEle.value}`);     
    });
}

let modalTitle = document.getElementById('modalTitle');
prdCodeBox.onclick = () => { // 제품코드 input 클릭 시 모달 호출
  productList.style.display = '';
  modalTitle.innerText = '제품 선택';
  $('#myModal').modal('show');
  prdGrid.refreshLayout(); 
};

/******************** Tui Grid Custom Renderer ********************/  
let selected;
// 선택버튼 커스텀 렌더링
class CustomBtnRender {
  constructor(props) {
    let classNm = props.columnInfo.className;
    const el = document.createElement('button');
    el.type = 'button';
    el.classList = 'btn btn-outline-light btn-sm';
    el.innerText = classNm == 'plan' ? '상세조회' : '선택';
    
    el.addEventListener('click', () => {
      if(classNm == 'prd'){
        let prdData = prdGrid.getRow(props.rowKey);
        prdCodeBox.value = prdData.productCode;
        prdNameBox.value = prdData.productName;
        $('#myModal').modal('hide');
        productList.style.display = 'none';
        return;
      }
      
      selected = supplyGrid.getRow(props.rowKey);
      console.log(selected);
      
      // 모달에 선택한 정보 표시
      supplyPlanCode.value = selected.supplyPlanCode;
      supplyYear.value = selected.supplyYear;
      season.value = selected.season;
      chargerName.value = selected.chargerName;
      rgsde.value = dateFormatter(selected.rgsde);
      remark.value = selected.remark;
      
      loadPlanDetail(selected.supplyPlanCode);
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
// 제품 그리드 (검색)
const prdData = {
  api: { readData: { url: '/supply/productList', method: 'GET' } }
};
  
const prdGrid = new Grid({
    el: document.getElementById('prdGrid'), // 해당 위치에 그리드 출력
    data: prdData,
    columns: [
        { header: '제품코드', name: 'productCode', width: 100, sortable: true, align: 'center' },
        { header: '제품명', name: 'productName', whiteSpace: 'pre-line', sortable: true },
        { header: '', name: '', className: 'prd', renderer: { type: CustomBtnRender, options: {}}, width: 150, align: 'center' }
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
                   return `총 ${valueMap.cnt}건`
               }
           }
       }
     }
});

// 공급계획 목록 그리드
const supplyGrid = new Grid({
    el: document.getElementById('supplyGrid'), // 해당 위치에 그리드 출력
    data: {
      api: { readData: { url: '/supply/supplyPlanList', method: 'GET', initParams : {supplyYear: new Date().getFullYear()} } }
    },
    columns: [
        { header: '공급계획코드', name: 'supplyPlanCode', width: 120, sortable: true, align: 'center' },
        { header: '공급년도', name: 'supplyYear', width: 90, sortable: true, align: 'center', className: 'fw-bold' },
        { header: '시즌', name: 'season', width: 50, sortable: true, align: 'center', className: 'fw-bold' },
        { header: '요약', name: 'summary', whiteSpace: 'pre-line', sortable: true },
        { header: '공급량합계', name: 'supplyQy', width: 150, sortable: true, align: 'right',
           formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
        { header: '비고', name: 'remark', whiteSpace: 'pre-line', sortable: true, elipsis: true },
        { header: '담당자', name: 'chargerName', width: 100, sortable: true, align: 'center' },
        { header: '등록일', name: 'rgsde', width: 100, formatter: (row) => dateFormatter(row.value), align: 'center' },
        { header: '최종변경일', name: 'updateDate', width: 100, formatter: (row) => dateFormatterNull(row.value), align: 'center' },
        { header: '상태', name: 'discontinued', align: 'center', width: 50,
          renderer: {
            styles: {
              fontWeight: 'bold',
              color: props => props.value == 'Y' ? 'red' : null
            }
          },
          formatter: row => row.value == 'Y' ? '중단' : null // 중단인 것만 표시
         },
        { header: '', name: '', className: 'plan', renderer: { type: CustomBtnRender, options: {}}, width: 100, align: 'center' }
    ],
    columnOptions: { resizable: true },
    rowHeaders: ['rowNum'],
    showDummyRows: true,
    bodyHeight: 400,
    pageOptions: {
        useClient: true, // 페이징을 위해 필요
        perPage: 10
    },
    scrollX: false, // 가로 스크롤
    scrollY: false, // 세로 스크롤
    summary: {
         height: 40,
         position: 'bottom', // or 'top'
         columnContent: {
            supplyPlanCode: { // 컬럼명
                   template: (valueMap) => {
                       return `총 ${valueMap.cnt}건`
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
        { header: '제품코드', name: 'productCode', sortable: true, align: 'center' },
        { header: '제품명', name: 'productName', whiteSpace: 'pre-line', sortable: true },
        { header: '공급계획일자', name: 'supplyDate', sortable: true, formatter: (row) => dateFormatterNull(row.value), align: 'center' },
        { header: '공급계획량', name: 'supplyQy', sortable: true, align: 'right', formatter: (row) => numberFormatter(row.value) }
    ],
    rowHeaders: ['rowNum'],
    scrollX: false, // 가로 스크롤
    scrollY: true, // 세로 스크롤
    bodyHeight: 200,
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
// 제품 목록 검색 적용
document.getElementById('prdSearchBtn').addEventListener('click', () => {
  let dto = {
    productCode: document.getElementById('productCode').value,
    productName: document.getElementById('productName').value,
    lclas: document.getElementById('lclas').value,
    sclas: document.getElementById('sclas').value
  };
  prdGrid.setRequestParams(dto); // 조회 조건 전달
  prdGrid.reloadData(); // 그리드 재출력 (readData)
});

// 공급계획 검색 적용 
document.getElementById('planSearchBtn').addEventListener('click', () => {
  let checkedSeasons = document.querySelectorAll('input[name="season"]:checked');
  checkedSeasons = Array.from(checkedSeasons).map(ele => ele.value); // 가상의 노드들을 복사하여 배열처리
  
  let dto = {
    productCode: document.getElementById('scProductCode').value,
    productName: document.getElementById('scProductName').value,
    supplyDateMin: document.getElementById('supplyDateMin').value,
    supplyDateMax: document.getElementById('supplyDateMax').value,
    supplyPlanCode: document.getElementById('scSupplyPlanCode').value,
    supplyYear: document.getElementById('scSupplyYear').value,
    supplyQyMin: document.getElementById('supplyQyMin').value,
    supplyQyMax: document.getElementById('supplyQyMax').value,
    chargerName: document.getElementById('scChargerName').value,
    remark: document.getElementById('scRemark').value,
    rgsdeMin: document.getElementById('rgsdeMin').value,
    rgsdeMax: document.getElementById('rgsdeMax').value,
    season: checkedSeasons
  };
  loadPlan(dto);
});

// 엔터키 즉시 검색
document.getElementById('productList').addEventListener('keyup', e => {
    if(e.key == 'Enter') document.getElementById('prdSearchBtn').dispatchEvent(new Event('click'));
});
document.getElementById('planForm').addEventListener('keyup', e => {
    if(e.key == 'Enter') document.getElementById('planSearchBtn').dispatchEvent(new Event('click'));
});

// 공급계획서 목록 조회
function loadPlan(obj){
  let {season, ...others} = obj;
  let query = new URLSearchParams(others);
  
  // 배열값이 있는 경우 쿼리스트링을 직접 만들어야 정상 출력
  if(season) season.forEach(code => query += `&season=${code}` );
  
  fetch(`/supply/supplyPlanList?${query}`)
  .then(response => response.json())
  .then(result => {
    let data = result.data.contents;
    console.log(data);
    supplyGrid.resetData(data);
  });
}

// 선택한 공급계획서의 상세내역 조회
function loadPlanDetail(supplyPlanCode){
  fetch(`/supply/supplyPlan?supplyPlanCode=${supplyPlanCode}`)
  .then(response => response.json())
  .then(result => {
    let data = result.data.contents;
    console.log('상세내역', data);
    planDetailGrid.resetData(data);
    
    detailList.style.display = '';
    if(validSeason(selected)){
        disconBtn.style.display = ''; // 중단버튼 표시
        disconMsg.style.display = '';
    } 
    modalTitle.innerText = '공급계획서 상세조회';
    $('#myModal').modal('show');
    planDetailGrid.refreshLayout(); 
    optionGrid.refreshLayout(); 
  });
}

let selectedPrdBox = document.getElementById('selectedPrd'); // 제품별 정보 표시 div
// 선택된 행 강조 & 정보 가져오기
planDetailGrid.on('focusChange', async ev => {
  // 배경색 클래스 적용
  planDetailGrid.removeRowClassName(ev.prevRowKey, 'bg-blue'); // 이전 선택 행 배경색 삭제
  planDetailGrid.addRowClassName(ev.rowKey, 'bg-blue'); // 선택된 행 배경색 추가
  
  // 선택한 정보 가져오기
  selectedPrd = planDetailGrid.getRow(ev.rowKey);  
  document.getElementById('selectedPrdCode').value = selectedPrd.productCode;
  document.getElementById('selectedPrdName').value = selectedPrd.productName;
  
  loadPrdPivot(selectedPrd);
});

let isModify;
let modifyBtn = document.getElementById('modifyBtn');
let modifyConfirmBtn = document.getElementById('modifyConfirmBtn');
let modifyCancelBtn = document.getElementById('modifyCancelBtn');
let disconBtn = document.getElementById('disconBtn'); 
let disconMsg = document.getElementById('disconMsg');

// 선택한 공급계획서-제품의 옵션별 집계 조회
function loadPrdPivot(selectedPrd){
  let query = new URLSearchParams({
    productCode: selectedPrd.productCode, 
    supplyPlanCode: selected.supplyPlanCode
  });
  
  fetch(`/supply/supplyPlanPivot?${query}`)
  .then(response => response.json())
  .then(result => {
    let data = result.data.contents;
    let columns = [];
    let qyColumn = { header: "수량", name: "SUPPLY_QY", align: "right", editor: 'text', disabled: true,
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
      columns.push({ header: "색상/사이즈", name: "PRODUCT_COLOR", width: 100, align: "center" });
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
        let newCol = { header: nm, name: getSizeCommonCode(nm), align: 'right', editor: 'text', disabled: true,
                       formatter: (row) => row.value == 0 || row.value == null ? null : numberFormatter(row.value) };
        columns.push(newCol);     
      });
    }
    
    if(isModify) modifyMode(false); // 수정중에 클릭 시 중복 실행 방지
    if(validSeason(selected)) modifyBtn.style.display = ''; // 수정버튼 표시
    optionGrid.resetData(data); // 데이터 입력
    optionGrid.setColumns(columns); // 컬럼 입력
  });
}

// 시즌이 시작하는 월 반환
function getSeasonMonth(str){
    switch(str){
        case '봄': return 3;
        case '여름': return 6;
        case '가을': return 9;
        case '겨울': return 12;
        default: return 0;
    }
}

// 수정/중단 가능 여부 (시즌 비교)
function validSeason(row){
    if(row.discontinued == 'Y') return false;
    let seasonMonth = getSeasonMonth(row.season);
    if(seasonMonth == 0) return true; // 상시공급이라면 제한 없음
    
    let today = new Date();
    let year = today.getFullYear();
    let month = today.getMonth() + 1;
    
    // 공급년도가 올해라면 시즌이 시작되기까지 한 달 이상 남아있어야 수정/중단 가능
    let isValid = month < seasonMonth - 1; 
    return (isValid && row.supplyYear == year) || row.supplyYear > year;
}

// 공급계획서 수정
modifyBtn.addEventListener('click', () => modifyMode(true));
modifyCancelBtn.addEventListener('click', () => modifyMode(false));

modifyConfirmBtn.addEventListener('click', () => {
    let updated = optionGrid.getModifiedRows().updatedRows;
    if(updated.length == 0){
      failToast('변경된 값이 없습니다.');
      modifyMode(false);
      return;
    }
    
    let differ = 0; // 전체 수량 계산용
    let detailArr = [];
    editableCells.forEach(obj => {
        updated.forEach(data => {
            if(data.rowKey == obj.rowKey){
                let supplyQy = data[obj.columnName];
                if(supplyQy == obj.qy) return; // 저장된 원본값과 달라진 게 없으면 건너뛰기
                else differ = differ + (supplyQy - obj.qy); // 수량 차이 저장
                
                let productColor = data['PRODUCT_COLOR'] ? data['PRODUCT_COLOR'] : 'null';
                let productSize = 'null';                
                if(data['PRODUCT_SIZE']) productSize = data['PRODUCT_SIZE']; // 사이즈코드가 있으면 넣음.
                else if(obj.columnName.substr(0,2) == 'SI') productSize = obj.columnName;

                let productCode = selectedPrd.productCode;
                detailArr.push({productCode, supplyQy, productColor, productSize});                
            }
        })
    });
    let headerObj = {supplyPlanCode: selected.supplyPlanCode};
    
    fetch('/supply/plan', {
        method: 'PUT',
        headers: {...headers, 'Content-Type': 'application/json'},
        body: JSON.stringify({ headerObj, detailArr})
    })
    .then(response => response.json())
    .then(result => {
        if(result == true){ 
          successToast('작업이 완료되었습니다.');
          // 변경사항 반영
          selectedPrd.supplyQy = selectedPrd.supplyQy + differ;
          planDetailGrid.setRow(selectedPrd.rowKey, selectedPrd);
          
          optionGrid.resetData(optionGrid.getData());
          
          selected.updateDate = dateFormatter();
          selected.supplyQy = planDetailGrid.getSummaryValues('supplyQy').sum;
          supplyGrid.setRow(selected.rowKey, selected);
        }
        else failToast('작업을 실패했습니다.');
    });
    
    modifyMode(false);
})

let editableCells = []; // 수정 가능한 위치 저장
// 수정모드 전환
function modifyMode(boolean){
    isModify = boolean;
    modifyBtn.style.display = isModify ? 'none' : '';
    modifyConfirmBtn.style.display = isModify ? '' : 'none';
    modifyCancelBtn.style.display = isModify ? '' : 'none';
    disconBtn.style.display = isModify ? 'none' : '';
    disconMsg.style.display = isModify ? 'none' : '';
    
    editableCells = getEditableCells();
    if(isModify){ // 입력 가능한 셀만 해제
        editableCells.forEach(obj => {
            optionGrid.enableCell(obj.rowKey, obj.columnName);
        });        
    } else {
        editableCells.forEach(obj => {
            console.log(obj);
            optionGrid.disableCell(obj.rowKey, obj.columnName);
        });  
    }
}

// 입력 가능한 셀 확인
function getEditableCells(){
    let gridData = optionGrid.getData();
    let columnNames = optionGrid.getColumns().map(col => col.name); // 컬럼명만 수집
    // 행-열을 순회하며 0이 아닌 셀을 찾고, [{rowKey, columnName, qy},..] 형식으로 반환
    let resultArr = [];
    gridData.forEach(data => {
        columnNames.forEach(nm => {
            if(data[nm] != 0 && data[nm] != null && !isNaN(data[nm])){
                let obj = {};
                obj.rowKey = data.rowKey;
                obj.columnName = nm;
                obj.qy = data[nm];
                resultArr.push(obj);
            }
        });
    });
    return resultArr;
}

// 입력값 유효성검사
optionGrid.on('afterChange', ev => {
  let changed = ev.changes[0];
  let rowKey = changed.rowKey;
  let row = optionGrid.getRow(rowKey);
  let val = changed.value;
  
  if(isNaN(val) || val < 0){ // 입력값이 유효하지 않은 경우
    if(isNaN(val)) failToast('입력값은 문자가 들어갈 수 없습니다.');
    else failToast('입력값은 음수가 될 수 없습니다.');
    val = changed.prevValue;
  }
  row[changed.columnName] = val;
  optionGrid.setRow(rowKey, row);
});

// 계획중단 버튼 동작 (시즌이 지나지 않은 공급계획에만 수정/중단표시)  
document.addEventListener('DOMContentLoaded', () => {
    createModal({ 
      type: 'delete',
      header: '계획중단 확인',
      content: '해당 공급계획서를 중단하시겠습니까?',
      buttonText: '확인',
      confirm: () => {
        let headerObj = {
            supplyPlanCode: selected.supplyPlanCode,
            discontinued: 'YN01'
        }
        
        fetch('/supply/plan', {
            method: 'PUT',
            headers: {...headers, 'Content-Type': 'application/json'},
            body: JSON.stringify({headerObj, detailArr: []})
        })
        .then(response => response.json())
        .then(result => {
            if(result == true){
                successToast('작업이 완료되었습니다.');
                selected.discontinued = 'Y';
                selected.updateDate = dateFormatter();
                supplyGrid.setRow(selected.rowKey, selected);
                disconBtn.style.display = 'none';
                disconMsg.style.display = 'none';
            }
            else failToast('작업을 실패했습니다.');
        });
      }
  }); 
});

disconBtn.addEventListener('click', () => {
    $('#simpleModal').modal('show');
});

// 모달 내부 닫기버튼 동작 (모두 숨김)
document.getElementById('closeBtn').addEventListener('click', () => closeAll());
document.querySelector('.btn-close').addEventListener('click', () => closeAll());

function closeAll(){
    if(isModify) modifyMode(false);
    detailList.style.display = 'none';
    productList.style.display = 'none';
    modifyBtn.style.display = 'none';
    disconBtn.style.display = 'none';
    disconMsg.style.display = 'none';
    optionGrid.resetData([]);
    optionGrid.setColumns([]);
    document.getElementById('selectedPrdCode').value = '';
    document.getElementById('selectedPrdName').value = '';
    $('#myModal').modal('hide');
}

// 모달 숨김 시 닫기 버튼과 동일한 효과
document.addEventListener('hide.bs.modal', () => closeAll());
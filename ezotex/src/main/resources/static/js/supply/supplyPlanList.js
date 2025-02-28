// 선택한 공급계획서의 상세조회 내용
let supplyPlanCode = document.getElementById('supplyPlanCode');
let supplyYear = document.getElementById('supplyYear');
let season = document.getElementById('season');
let chargerName = document.getElementById('chargerName');
let selectedPrdCode = document.getElementById('selectedPrdCode'); ///
let selectedPrdName = document.getElementById('selectedPrdName'); ///
let totalQy = document.getElementById('totalQy');
let rgsde = document.getElementById('rgsde');
let remark = document.getElementById('remark');

document.addEventListener('DOMContentLoaded', () => {
  makeQuantityTag();
});

/******************** Tui Grid Custom Renderer ********************/  
let selected;
// 선택버튼 커스텀 렌더링
class CustomBtnRender {
  constructor(props) {
    const el = document.createElement('button');
    el.type = 'button';
    el.classList = 'btn btn-outline-light btn-sm';
    el.innerText = '선택';
    
    el.addEventListener('click', () => {
      selected = supplyGrid.getRow(props.rowKey);
      console.log(selected);
      
      // 모달에 선택한 정보 표시
      supplyPlanCode.value = selected.supplyPlanCode;
      supplyYear.value = selected.supplyYear;
      season.value = selected.season;
      chargerName.value = selected.chargerName;
      totalQy.value = numberFormatter(selected.supplyQy);
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
// 공급계획 목록 그리드
const supplyGrid = new Grid({
    el: document.getElementById('supplyGrid'), // 해당 위치에 그리드 출력
    data: {
      api: { readData: { url: '/supply/supplyPlanList', method: 'GET', initParams : {supplyYear: new Date().getFullYear()} } }
    },
    columns: [
        { header: '공급계획코드', name: 'supplyPlanCode', width: 120, sortable: true },
        { header: '공급년도', name: 'supplyYear', width: 80, sortable: true },
        { header: '시즌', name: 'season', width: 50, sortable: true },
        { header: '요약', name: 'summary', whiteSpace: 'pre-line', sortable: true },
        { header: '공급량합계', name: 'supplyQy', width: 150, sortable: true, align: 'right',
           formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
        { header: '비고', name: 'remark', whiteSpace: 'pre-line', sortable: true, elipsis: true },
        { header: '담당자', name: 'chargerName', width: 100, sortable: true },
        { header: '등록일', name: 'rgsde', width: 100, formatter: (row) => dateFormatter(row.value) },
        { header: '최종수정일', name: 'updateDate', width: 100, formatter: (row) => dateFormatterNull(row.value) },
        { header: '', name: '', className: 'mtr', renderer: { type: CustomBtnRender, options: {}}, width: 100, align: 'center' }
    ],
    columnOptions: { resizable: true },
    rowHeaders: ['rowNum'],
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
        { header: '제품코드', name: 'productCode', sortable: true },
        { header: '제품명', name: 'productName', whiteSpace: 'pre-line', sortable: true },
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
// 발주계획 목록 검색 적용 
document.getElementById('planSearchBtn').addEventListener('click', () => {
  let checkedSeasons = document.querySelectorAll('input[name="season"]:checked');
  checkedSeasons = Array.from(checkedSeasons).map(ele => ele.value); // 가상의 노드들을 얕은 복사하여 배열처리
  
  let dto = {
    productCode: document.getElementById('productCode').value,
    productName: document.getElementById('productName').value,
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
  fetch(`/supply/supplyPlan/${supplyPlanCode}`)
  .then(response => response.json())
  .then(result => {
    let data = result.data.contents;
    console.log('상세내역', data);
    planDetailGrid.resetData(data);
    
    $('#myModal').modal('show');
    planDetailGrid.refreshLayout(); 
  });
}

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

// 선택한 공급계획서-제품의 옵션별 집계 조회
function loadPrdPivot(selectedPrd){
  let query = new URLSearchParams({
    productCode: selectedPrd.productCode, 
    supplyPlanCode: supplyPlanCode.value
  });
  
  fetch(`/supply/supplyPlanPivot?${query}`)
  .then(response => response.json())
  .then(result => {
    let data = result.data.contents;
    console.log(data);
    
    if(data == null){ 
      // 옵션이 없는 단일제품일 경우
      optionGrid.resetData([{ SUPPLY_QY: selectedPrd.supplyQy }]); // 데이터 입력
      optionGrid.setColumns([{ header: "수량", name: "SUPPLY_QY", align: "right" }]); // 컬럼 입력
    } else if(data[0].SUPPLY_QY){  
      // 옵션이 사이즈/색상 중 하나만 있는 경우
      let columns = [{ header: "색상", name: "PRODUCT_COLOR" },
                     { header: "사이즈", name: "PRODUCT_SIZE" },
                     { header: "수량", name: "SUPPLY_QY", align: "right" }];
                                       
      if(data[0].PRODUCT_COLOR) optionGrid.setColumns([columns[0], columns[2]]);
      else optionGrid.setColumns([columns[1], columns[2]]);
      optionGrid.resetData(data);
    } else {
      // 사이즈/색상 모두 있는 피벗 형태인 경우
      // data 형식: [{"PRODUCT_COLOR": "BLACK", "110": 0, "S": 0, "L": 0, "M": 0, ...}, ...]
      let columns = [{ header: "색상/사이즈", name: "PRODUCT_COLOR", width: 80 }];
      let sizeNmArr = [];
      
      // pivot으로 가져온 사이즈명을 분리하여 컬럼 지정
      data.forEach((row, idx) => {
        let { PRODUCT_COLOR, rowKey, sortKey, uniqueKey, rowSpanMap, _attributes, _disabledPriority, _relationListItemMap, 
              ...sizeNms } = row;
        if(idx == 0) sizeNmArr = Object.keys(sizeNms);
        sizeNmArr.forEach((nm) => {
          let commonCode = getSizeCommonCode(nm);
          row[commonCode] = row[nm];
        });
      });
      
      sizeNmArr
      .sort((current, next) => { // 공통코드 기준 오름차순 정렬
        return getSizeCommonCode(current).substr(2,3) - getSizeCommonCode(next).substr(2,3);
      })
      .forEach((nm) => { // 컬럼 생성
        let newCol = { header: nm, name: getSizeCommonCode(nm), editor: 'text', align: 'right', 
                       formatter: row => numberFormatter(row.value) };
        columns.push(newCol);     
      });
      
      optionGrid.resetData(data); // 데이터 입력
      optionGrid.setColumns(columns); // 컬럼 입력
    }
    
  });
}
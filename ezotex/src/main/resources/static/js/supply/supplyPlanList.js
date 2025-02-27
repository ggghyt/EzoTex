
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
      selected = planGrid.getRow(props.rowKey);
      console.log(selected);
      fetch('/supply/supplyPlanInfoModal?' + new URLSearchParams(selected))
      .then(response => response.text())
      .then(result => {
        document.getElementById('modalBox').innerHTML = result;
        $('#myModal').modal('show');
      });
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
const planGrid = new Grid({
    el: document.getElementById('planGrid'), // 해당 위치에 그리드 출력
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
        { header: '등록일', name: 'resde', width: 100, formatter: (row) => dateFormatter(row.value) },
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

// 발주계획 목록 검색 적용 
document.getElementById('planSearchBtn').addEventListener('click', () => {
  let checkedSeasons = document.querySelectorAll('input[name="season"]:checked');
  checkedSeasons = Array.from(checkedSeasons).map(ele => ele.value); // 가상의 노드들을 얕은 복사하여 배열처리
  
  let dto = {
    productCode: document.getElementById('productCode').value,
    productName: document.getElementById('productName').value,
    supplyDateMin: document.getElementById('supplyDateMin').value,
    supplyDateMax: document.getElementById('supplyDateMax').value,
    supplyPlanCode: document.getElementById('supplyPlanCode').value,
    supplyYear: document.getElementById('supplyYear').value,
    supplyQyMin: document.getElementById('supplyQyMin').value,
    supplyQyMax: document.getElementById('supplyQyMax').value,
    chargerName: document.getElementById('chargerName').value,
    remark: document.getElementById('remark').value,
    rgsdeMin: document.getElementById('rgsdeMin').value,
    rgsdeMax: document.getElementById('rgsdeMax').value,
    season: checkedSeasons
  };
  loadPlan(dto);
});

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
    planGrid.resetData(data);
  });
}
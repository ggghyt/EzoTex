/******************** select-option 동적 생성 ********************/
let lclasBox = document.getElementById('lclas');
let sclasBox = document.getElementById('sclas');

document.addEventListener('DOMContentLoaded', () => {
	changeClas(lclasBox, sclasBox);
	
	createModal({ 
		type: 'regist',
		confirm: null,
		loading: false
	});
	$('#insertBtn').on('click', () => {
		let supplyPlan = supplyGrid.getData();
		if(supplyPlan.length == 0) return; // 자재가 선택되지 않았으면 종료
		let isValid = validCheck(supplyPlan);
		if(!isValid) return; // 유효성 검사 실패 시 종료
		$('#simpleModal').modal('show'); // 유효성 검사 통과 시 등록 확인
	});
});

// 대분류 onChange 이벤트 등록 함수
const changeClas = function(lclasEle, sclasEle){
	lclasEle.addEventListener('change', () => {
		createOptions(sclasEle, `/product/category/${lclasEle.value}`);		
	});
}

const createOptions = function(ele, uri){	
	// 서버에서 데이터 불러오기
	fetch(uri)
	.then(response => response.json())
	.then(data => {
		ele.innerHTML = '<option value="null">전체</option>';
		
		for(let value of data){			
			let opt = document.createElement('option');
			opt.value = value;
			opt.innerText = value;
			ele.append(opt);
		}
	});
}

/******************** tui grid 출력 ********************/	
// 제품 그리드
const prdData = {
	api: { readData: { url: '/supply/productList', method: 'GET' } }
};
	
const prdGrid = new Grid({
    el: document.getElementById('prdGrid'), // 해당 위치에 그리드 출력
    data: prdData,
    columns: [
        { header: '제품코드', name: 'productCode', width: 100, sortable: true },
        { header: '제품명', name: 'productName', whiteSpace: 'pre-line', sortable: true }
    ],
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
			 		productCode: { // 컬럼명
			         template: (valueMap) => {
			             return `총 ${valueMap.cnt}건`
			         }
			     }
			 }
     }
});

let optionArr = [];

// 옵션 그리드
const optionGrid = new Grid({
		el: document.getElementById('optionGrid'), // 컨테이너 엘리먼트
		data: optionArr,
	  columns: [
        //{ header: "색상/사이즈", name: "productColor", align: "center", width: 80 }
    ],
  	scrollX: true, // 가로 스크롤
  	scrollY: true, // 세로 스크롤
  	bodyHeight: 200
});

// 공급계획 총계 그리드
const supplyGrid = new Grid({
	el: document.getElementById('supplyGrid'), // 해당 위치에 그리드 출력
    data: [],
    columns: [
        { header: '제품코드', name: 'mtrilCode', width: 100, sortable: true },
        { header: '제품명', name: 'mtrilName', whiteSpace: 'pre-line', sortable: true },
        { header: '공급계획일자', name: 'requireQy', width: 100, sortable: true, editor: 'datePicker' },
        { header: '총 수량', name: 'unitName', width: 100, sortable: true, editor: 'text', align: 'right',
				  formatter: (row) => numberFormatter(row.value) } // 천단위 콤마 포맷 적용
    ],
  	scrollX: false, // 가로 스크롤
  	scrollY: true, // 세로 스크롤
  	bodyHeight: 238
});

/******************** 제품/자재 선택 ********************/	
let selectedPrd = null;
let lastClicked = null; // 페이지 이동 시에도 이전 선택 기억하기 위함.

// 선택된 행 강조 & 정보 가져오기
prdGrid.on('focusChange', ev => {
	// 배경색 클래스 적용
	prdGrid.removeRowClassName(lastClicked, 'bg-blue'); // 이전 선택 행 배경색 삭제
	prdGrid.addRowClassName(ev.rowKey, 'bg-blue'); // 선택된 행 배경색 추가
	lastClicked = ev.rowKey; // 선택된 행 기억
	
	// 선택한 정보 가져오기
	selectedPrd = prdGrid.getRow(ev.rowKey);
	document.getElementById('selectedPrdCode').value = selectedPrd.productCode;
	document.getElementById('selectedPrdName').value = selectedPrd.productName;
	// 옵션 출력해야함.
});

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
let lclasBox = document.getElementById('lclas');
let sclasBox = document.getElementById('sclas');

let compCodeBox = document.getElementById('companyCode');
let mtrCodeBox = document.getElementById('mtrilCode');
let colorBox = document.getElementById('color');

document.addEventListener('DOMContentLoaded', () => {	
	changeClas(lclasBox, sclasBox);
	$('#chargerName').val(session_user_name);
	
	compCodeBox.onclick = () => { // 업체코드 input 클릭 시 현재 값으로 모달 호출
		let valueObj = {
			productCode: mtrCodeBox.value,
			productColor: colorBox.value
		};
		loadModalGrid('company', valueObj);
	};
	
	mtrCodeBox.onclick = () => { // 자재코드 input 클릭 시 현재 값으로 모달 호출
		loadModalGrid('material', { companyCode: compCodeBox.value });
	};
	
	createModal({ 
		type: 'regist',
		confirm: null,
		loading: false
	});
	$('#finalBtn').on('click', () => {
		let orderData = orderGrid.getData();
		if(orderData.length == 0) return; // 아무것도 입력되지 않았으면 종료
		$('#simpleModal').modal('show'); // 입력값이 있다면 등록 모달 표시
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

/******************** Tui Grid Custom Renderer ********************/	
// 행 삭제버튼 커스텀 렌더링
class CustomBtnRender {
  constructor(props) {
	// props: 화면에 표시될 때마다 생성자가 실행되며 넘어오는 객체
	// props = grid, rowKey, columnInfo, value(데이터)
	const el = document.createElement('button');
	el.classList = 'btn btn-danger btn-sm';
	el.id = props.rowKey; // 태그 자체에 rowKey 저장
	el.innerText = '삭제';
	
	el.addEventListener('click', (e) => {
		let rowKey = e.target.id;
		orderGrid.removeRow(rowKey);
	});
    this.el = el;
    this.render(props);
  }
  getElement() { return this.el; }
  render(props) {	
    this.el.value = props.value;
  }
}

/******************** Tui Grid 출력 ********************/	
// 자재 선택 그리드
const prdGrid = new Grid({
    el: document.getElementById('prdGrid'), // 해당 위치에 그리드 출력
    data: [],
    columns: [
        { header: '자재코드', name: 'productCode', width: 200, sortable: true },
        { header: '자재명', name: 'productName', width: 600, whiteSpace: 'pre-line', sortable: true },
        { header: '단위', name: 'unitName', width: 100, sortable: true },
        { header: '기본단가', name: 'unitPrice', width: 166, sortable: true, align: 'right',
		  formatter: (row) => numberFormatter(row.value) }
    ],
    rowHeaders: ['rowNum'],
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

// 업체 선택 그리드
const companyGrid = new Grid({
    el: document.getElementById('companyGrid'), // 해당 위치에 그리드 출력
    data: [],
    columns: [
        { header: '업체코드', name: 'companyCode', width: 100, sortable: true },
        { header: '업체명', name: 'companyName', width: 206, ellipsis: true, sortable: true },
        { header: '소재지', name: 'addressInfo', width: 450, ellipsis: true, sortable: true },
        { header: '담당자', name: 'companyCharger', width: 100, sortable: true },
        { header: '연락처', name: 'companyPhone', width: 210, sortable: true }
    ],
    columnOptions: { resizable: true },
    rowHeaders: ['rowNum'],
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
		             return `총 ${valueMap.cnt}건`
		         }
		     }
		 }
     }
});

// 발주 목록 그리드
const orderGrid = new Grid({
	el: document.getElementById('orderGrid'), // 해당 위치에 그리드 출력
    data: [],
    columns: [
		{ header: '업체코드', name: 'companyCode', width: 100, sortable: true },
        { header: '업체명', name: 'companyName', whiteSpace: 'pre-line', sortable: true },
        { header: '제품코드', name: 'productCode', width: 100, sortable: true },
        { header: '제품명', name: 'productName', whiteSpace: 'pre-line', sortable: true },
        { header: '발주량', name: 'orderQy', width: 100, sortable: true, align: 'right',
				  formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
	    { header: '단위', name: 'unitName', sortable: true },
		{ header: '단가', name: 'unitPrice', width: 100, sortable: true, align: 'right',
				  formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
		{ header: '금액', name: 'totalPrice', width: 100, sortable: true, align: 'right',
				  formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
	    { header: '', name: '', renderer: { type: CustomBtnRender, options: {}}, width: 50, align: 'center' },
		{ header: '데이터', name: 'allData', hidden: true }
    ],
    rowHeaders: ['rowNum'],
  	scrollX: false, // 가로 스크롤
  	scrollY: true, // 세로 스크롤
  	bodyHeight: 200,
	summary: {
  		 height: 40,
		 position: 'bottom', // or 'top'
		 columnContent: {
		 		companyCode: { // 컬럼명
			         template: (valueMap) => {
			             return `총 ${valueMap.cnt}건`
				     }
			    },
	     		totalQy: {  }
		 }
     }
});


let mtrListDiv = document.getElementById(`materialList`);
let compListDiv = document.getElementById(`companyList`);
// 모달의 자재/업체 목록 출력
function loadModalGrid(type, obj){ // type: 'material' or 'company'
	let query = new URLSearchParams(obj);
	fetch(`/supply/${type}List?${query}`)
	.then(response => response.json())
	.then(result => {
		let data = result.data.contents;
		console.log(data);
		// 데이터 입력
		if(type == 'material'){
			prdGrid.resetData(data);
			compListDiv.style.display = 'none';
			mtrListDiv.style.display = 'block';
		} else {
			companyGrid.resetData(data);
			mtrListDiv.style.display = 'none';
			compListDiv.style.display = 'block';
		}
		
		// 모달 표시
		$('#myModal').modal('show');
		prdGrid.refreshLayout();
		companyGrid.refreshLayout();
	});
}
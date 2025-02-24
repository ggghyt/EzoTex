let lclasBox = document.getElementById('lclas');
let sclasBox = document.getElementById('sclas');

let compCodeBox = document.getElementById('companyCode');
let compNameBox = document.getElementById('companyName');
let mtrCodeBox = document.getElementById('mtrilCode');
let mtrNameBox = document.getElementById('mtrilName');
let colorBox = document.getElementById('color');
let unitNameBox = document.getElementById('unitName');
let unitPriceBox = document.getElementById('unitPrice');
let inventoryQyBox = document.getElementById('inventoryQy');
let storingQyBox = document.getElementById('storingQy');
let orderQyBox = document.getElementById('qy');

document.addEventListener('DOMContentLoaded', () => {	
	changeClas(lclasBox, sclasBox);
	$('#chargerName').val(session_user_name);
	
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

// 초기화 버튼 클릭 시 입력값뿐만 아니라 옵션도 초기화
document.getElementById('resetBtn').onclick = () => {
	selectedMtr = null;
	colorInfo = [];
	colorBox.innerHTML = '<option value="null">미선택</option>';
};

// 업체/자재 선택
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

/******************** Tui Grid Custom Renderer ********************/	
// 행 삭제버튼 커스텀 렌더링
class CustomDelBtnRender {
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

let selectedMtr = {};
// 선택버튼 커스텀 렌더링
class CustomBtnRender {
  constructor(props) {
	const el = document.createElement('button');
	el.type = 'button';
	el.classList = 'btn btn-outline-light btn-sm';
	el.innerText = '선택';
	
	el.addEventListener('click', () => {
		let type = props.columnInfo.className; // className으로 자재/업체선택 구분
		if(type == 'mtr'){ 
			selectedMtr = prdGrid.getRow(props.rowKey); // 자재 기본정보 저장
			mtrCodeBox.value = selectedMtr.PRODUCT_CODE;
			mtrNameBox.value = selectedMtr.PRODUCT_NAME;
			unitNameBox.value = selectedMtr.UNIT_NAME;
			unitPriceBox.value = numberFormatter(selectedMtr.UNIT_PRICE); // 기본단가
			inventoryQyBox.value = numberFormatter(selectedMtr.INVENTORY_QY);
		} else {
			let selectedComp = companyGrid.getRow(props.rowKey);
			compCodeBox.value = selectedComp.companyCode;
			compNameBox.value = selectedComp.companyName;
		}
		loadOptionPrice(); 
		$('#myModal').modal('hide');
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
        { header: '자재코드', name: 'PRODUCT_CODE', width: 150, sortable: true },
        { header: '자재명', name: 'PRODUCT_NAME', minWidth: 250, whiteSpace: 'pre-line', sortable: true },
        { header: '단위', name: 'UNIT_NAME', width: 150, sortable: true },
        { header: '기본단가', name: 'UNIT_PRICE', width: 150, sortable: true, align: 'right',
		  		formatter: (row) => numberFormatter(row.value) },
				{ header: '', name: '', className: 'mtr', renderer: { type: CustomBtnRender, options: {}}, width: 150, align: 'center' }
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
        { header: '업체명', name: 'companyName', width: 150, ellipsis: true, sortable: true },
        { header: '소재지', name: 'addressInfo', minWidth: 250, ellipsis: true, sortable: true },
        { header: '담당자', name: 'companyCharger', width: 100, sortable: true },
        { header: '연락처', name: 'companyPhone', width: 100, sortable: true },
				{ header: '', name: '', className: 'comp', renderer: { type: CustomBtnRender, options: {}}, width: 100, align: 'center' }
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
			{ header: '업체코드', name: 'companyCode', sortable: true },
	    { header: '업체명', name: 'companyName', minWidth: 100, whiteSpace: 'pre-line', sortable: true },
	    { header: '제품코드', name: 'productCode', sortable: true },
	    { header: '제품명', name: 'productName', minWidth: 100, whiteSpace: 'pre-line', sortable: true },
			{ header: '색상', name: 'productColor', sortable: true, formatter: row => row.value == 'null' ? null : row.value},
	    { header: '발주량', name: 'orderQy', sortable: true, align: 'right',
			  		formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
	    { header: '단위', name: 'unitName', width: 50, sortable: true },
			{ header: '단가', name: 'unitPrice', sortable: true, align: 'right',
					  formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
			{ header: '금액', name: 'totalPrice', sortable: true, align: 'right',
					  formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
	    { header: '', name: '', renderer: { type: CustomDelBtnRender, options: {}}, minWidth: 60, align: 'center' },
			{ header: '색상 및 단가정보', name: 'colorInfo', hidden: true }
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
		     		orderQy: { template: () => {} },
						totalPrice: { template: () => {} }
			 }
     }
});

let modalTitle = document.getElementById('modalTitle');
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
			modalTitle.innerText = '자재 선택';
			prdGrid.resetData(data);
		} else {
			modalTitle.innerText = '업체 선택';
			companyGrid.resetData(data);
		}
		// 모달 표시
		document.getElementById(`${type == 'material' ? 'company' : 'material'}List`).style.display = 'none';
		document.getElementById(`${type == 'material' ? 'material' : 'company'}List`).style.display = 'block';
		$('#myModal').modal('show');
		prdGrid.refreshLayout();
		companyGrid.refreshLayout();
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
document.getElementById('ComSearchBtn').addEventListener('click', () => {
	let dto = {
		companyCode: document.getElementById('scCompanyCode').value,
		companyName: document.getElementById('scCompanyName').value,
		address: document.getElementById('address').value,
		productCode: mtrCodeBox.value,
		productColor: colorBox.value
	};
	loadModalGrid('company', dto);
});

let colorInfo = []; // 옵션 정보 저장
// 자재/업체 선택 시 옵션 생성 및 재고+단가 불러오기
function loadOptionPrice(){
	if(mtrCodeBox.value == '') return; // 필수값
	let valueObj = {
		productCode: mtrCodeBox.value, 
		companyCode: compCodeBox.value
	};
	let query = new URLSearchParams(valueObj);
	
	fetch(`/supply/optionPriceList?${query}`)
		.then(response => response.json())
		.then(result => {
			console.log(result);
			
			// 옵션 태그 생성 및 기존 선택 반영
			let prdColorArr = result.map(obj => obj.PRODUCT_COLOR);
			let colorIdx = prdColorArr.findIndex(val => val == colorBox.value); // 선택했던 인덱스 저장
			
			colorBox.innerHTML = '<option value="null">미선택</option>';
			
			for(let color of prdColorArr){			
				let opt = document.createElement('option');
				opt.value = color;
				opt.innerText = color;
				colorBox.append(opt);
			}
			if(colorIdx != -1){
				colorBox.value = prdColorArr[colorIdx]; // 선택값 다시 표시
			  unitPriceBox.value = numberFormatter(result[colorIdx].UNIT_PRICE);
			} 
			colorInfo = result; // 색상 선택에따라 내용을 변경하기 위해 저장
			selectedMtr.PRODUCT_COLOR = 'null';
			colorInfo.push(selectedMtr); // 자재 기본정보(null옵션) 맨끝에 추가
			changeByColor(colorBox.value);
	});	
}

colorBox.addEventListener('change', e => {
	changeByColor(e.target.value);
});

// 색상 선택 시 단가 및 정보 재반영
function changeByColor(val){
	let idx = colorInfo.findIndex(obj => obj.PRODUCT_COLOR == val);
	unitPriceBox.value = numberFormatter(colorInfo[idx].UNIT_PRICE);
	inventoryQyBox.value = numberFormatter(colorInfo[idx].INVENTORY_QY);
	let storingQy = colorInfo[idx].STORING_QY;
	storingQyBox.value = typeof(storingQy) == 'undefined' ? '' : numberFormatter(storingQy);	
}

/******************** 발주목록 추가 ********************/	
document.getElementById('insertBtn').addEventListener('click', () => {
	insertRow();
});

let modifyRowKey;
// 발주목록에 넣은 데이터 수정하기
orderGrid.on('focusChange', ev => {
	orderGrid.removeRowClassName(ev.prevRowKey, 'bg-blue'); // 이전 선택 행 배경색 삭제
	orderGrid.addRowClassName(ev.rowKey, 'bg-blue'); // 선택된 행 배경색 추가
	
	modifyRowKey = ev.rowKey; // rowKey 저장하여 수정 시 사용
	let row = orderGrid.getRow(ev.rowKey);
	compCodeBox.value = row.companyCode;
	compNameBox.value = row.companyName;
	mtrCodeBox.value = row.productCode;
	mtrNameBox.value = row.productName;
	orderQyBox.value = row.orderQy;
	unitNameBox.value = row.unitName;
	
	colorInfo = row.colorInfo; // 저장해둔 색상 배열 가져오기
	let colorIdx = colorInfo.findIndex(obj => obj.PRODUCT_COLOR == row.productColor);
	let lastIdx = colorInfo.length - 1;
	colorBox.innerHTML = '<option value="null">미선택</option>';
				
	for(let i = 0; i < lastIdx; i++){ // 마지막 null옵션 제외하고 태그 생성			
		let opt = document.createElement('option');
		opt.value = colorInfo[i].PRODUCT_COLOR;
		opt.innerText = colorInfo[i].PRODUCT_COLOR;
		colorBox.append(opt);
	}
	colorBox.value = row.productColor;
	inventoryQyBox.value = colorInfo[colorIdx].INVENTORY_QY;
	let storingQy = colorInfo[colorIdx].STORING_QY;
	storingQyBox.value = typeof(storingQy) == 'undefined' ? '' : numberFormatter(storingQy);	
	unitPriceBox.value = numberFormatter(colorInfo[colorIdx].UNIT_PRICE);
	
	modifyOrder(true); // 수정 모드로 전환
});

document.getElementById('modifyBtn').addEventListener('click', () => {
	insertRow(modifyRowKey);
	modifyOrder(false);
});

document.getElementById('modifyCancelBtn').addEventListener('click', () => {
	modifyOrder(false);
});

// 발주목록 데이터 추가
function insertRow(rowKey){
	let orderQy = orderQyBox.value;
	if(compCodeBox.value == '' || mtrCodeBox.value == ''){
		failToast('업체와 자재를 선택해주세요.');
		return;
	}
	if(orderQy == ''){
		failToast('발주량을 입력해주세요.');
		return;
	}
	// 입력값에 대해 유효성검사
	if(isNaN(orderQy)){ // 숫자가 아닌 경우
		failToast('발주량에 문자가 들어갈 수 없습니다.');
		return;
	} else if (orderQy < 0){ // 음수인 경우
		failToast('발주량은 음수가 될 수 없습니다.');
		return;
	}
	
	let unitPrice = unitPriceBox.value.replace(',', '');
	let insertObj = {
		companyCode: compCodeBox.value,
		companyName: compNameBox.value,
		productCode: mtrCodeBox.value,
		productName: mtrNameBox.value,
		productColor: colorBox.value,
		orderQy: orderQy,
		unitName: unitNameBox.value,
		unitPrice: unitPrice,
		totalPrice: unitPrice * orderQyBox.value, // 단가 * 입력수량 = 총 금액
		colorInfo: colorInfo // 표시 항목 한꺼번에 저장
	};
	if(rowKey != null) orderGrid.setRow(rowKey, insertObj);
	else orderGrid.appendRow(insertObj);
	getSum();
}

// 수정버튼 토글기능
function modifyOrder(boolean){
	if(boolean){
		document.getElementById('insertBtn').style.display = 'none'; // 등록버튼 숨김
		document.getElementById('modifyCancelBtn').style.display = 'block'; // 수정취소버튼 노출
		document.getElementById('modifyBtn').style.display = 'block'; // 수정버튼 노출
	} else {
		document.getElementById('modifyBtn').style.display = 'none'; // 수정버튼 숨김
		document.getElementById('modifyCancelBtn').style.display = 'none'; // 수정취소버튼 숨김
		document.getElementById('insertBtn').style.display = 'block'; // 등록버튼 노출
		
		document.getElementById('resetBtn').dispatchEvent(new Event('click')); // 리셋버튼 클릭 이벤트 동작
		document.getElementById('orderForm').reset(); // 폼 이벤트 - 입력값 모두 초기화
		orderGrid.removeRowClassName(modifyRowKey, 'bg-blue'); // 행 배경색 삭제
	}
}

// 그리드 summary 집계 반영
function getSum(){
	let amount = orderGrid.getSummaryValues('totalPrice').sum;
	$('#amount').val(numberFormatter(amount));
	
	let sum = orderGrid.getSummaryValues('orderQy').sum;
	$('#totalQy').val(numberFormatter(sum));
};
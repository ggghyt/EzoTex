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
let dueDateBox = document.getElementById('dueDate');
////////////////////// 평균소요일, 안전재고량 쿼리+코드에 반영해야 함.

const orderDomEventHandler = function (){ 
  changeClas(lclasBox, sclasBox);
  //document.getElementById('chargerName').value = session_user_name;
  dueDateBox.min = dateFormatter(); // 오늘 이전 날짜 선택 방지
  
  $('#finalBtn').on('click', () => {
    let orderData = orderGrid.getData();
    if(orderData.length == 0) return; // 아무것도 입력되지 않았으면 미실행
    else if(orderData.filter(data => data.companyCode != null).length != orderGrid.getRowCount()){
      // 발주계획에서 불러온 데이터의 경우 업체코드가 필수값이 아니므로, 발주서에서는 모두 입력되어야 함.
      failToast('업체코드가 모두 선택되지 않았습니다.');
      return;
    }
    else if(dueDateBox.value == ''){
      failToast('납기일을 입력해주세요.');
      return;
    }
    insertOrder();
  });
}

document.addEventListener('DOMContentLoaded', orderDomEventHandler);

// 로그인정보 fetch 이후에 실행되는 커스텀 이벤트
document.addEventListener('login', () => $('#chargerName').val(session_user_name))

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

// 발주계획서 불러오기
document.getElementById('loadPlanBtn').addEventListener('click', () => loadModalGrid('orderPlan', {}));

/******************** Tui Grid Custom Renderer ********************/	
// 행 삭제버튼 커스텀 렌더링
class CustomDelBtnRender {
  constructor(props) {
	// props: 화면에 표시될 때마다 생성자가 실행되며 넘어오는 객체
	// props = grid, rowKey, columnInfo, value(데이터)
	const el = document.createElement('button');
	el.classList = 'btn btn-outline-danger btn-sm';
	el.id = props.rowKey; // 태그 자체에 rowKey 저장
	el.innerText = '삭제';
	
	el.addEventListener('mousedown', (e) => {
      e.stopPropagation(); // tui 그리드 셀 기본 이벤트 방지
	});
	el.addEventListener('click', (e) => {
		let rowKey = e.target.id;
		orderGrid.removeRow(rowKey);
		if(isModifying) modifyMode(false);
		getSum();
	});
    this.el = el;
    this.render(props);
  }
  getElement() { return this.el; }
  render(props) {	
    this.el.value = props.value;
  }
}

let selectedMtr = null;
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
		} else if(type == 'comp') {
			let selectedComp = companyGrid.getRow(props.rowKey);
			compCodeBox.value = selectedComp.companyCode;
			compNameBox.value = selectedComp.companyName;
		} else {
            let selectedPlan = planGrid.getRow(props.rowKey);
            loadPlanData(selectedPlan.mtrilOrderPlanCode);
        }
    
		if(type != 'plan') loadColorPriceInfo(); 
		closeAll();
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

/******************** Tui Grid ********************/	
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
		    { header: '자재코드', name: 'productCode', sortable: true },
		    { header: '자재명', name: 'productName', minWidth: 100, whiteSpace: 'pre-line', sortable: true },
			  { header: '색상', name: 'productColor', sortable: true, formatter: row => row.value == 'null' ? null : row.value},
	    	{ header: '수량', name: 'orderQy', sortable: true, align: 'right',
			  		formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
	    	{ header: '단위', name: 'unitName', width: 50, sortable: true },
			  { header: '단가', name: 'unitPrice', sortable: true, align: 'right',
					  formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
			  { header: '금액', name: 'amount', sortable: true, align: 'right',
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
					amount: { template: () => {} }
			 }
     }
});

// 업체별 발주서 등록확인 그리드
const insertGrid = new Grid({
		el: document.getElementById('insertGrid'), // 해당 위치에 그리드 출력
    columns: [
			  { header: '업체코드', name: 'companyCode' },
		    { header: '업체명', name: 'companyName', minWidth: 100, whiteSpace: 'pre-line' },
	    	{ header: '발주량', name: 'totalQy', align: 'right',
			  		formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
			  { header: '발주금액', name: 'totalAmount', align: 'right',
					  formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
	    	{ header: '납기일', name: 'dueDate', 
          editor: { 
            type: 'datePicker', 
            options: { selectableRanges: [[new Date(), new Date('9999-12-31')]] }
          } 
        },
			  { header: '비고', name: 'remark', width: 200, whiteSpace: 'pre-line', editor: 'text' }
    ],
    rowHeaders: ['rowNum'],
  	scrollX: false, // 가로 스크롤
  	scrollY: true, // 세로 스크롤
  	bodyHeight: 200
});

// 발주계획서 불러오기 그리드
const planGrid = new Grid({
    el: document.getElementById('planGrid'), // 해당 위치에 그리드 출력
    columns: [
        { header: '발주계획코드', name: 'mtrilOrderPlanCode' },
        { header: '납기일', name: 'dueDate', formatter: (row) => dateFormatter(row.value) },
        { header: '요약', name: 'summary', width: 150 },
        { header: '발주계획량', name: 'orderQy', align: 'right',
            formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
        { header: '비고', name: 'remark', whiteSpace: 'pre-line', width: 150 },
        { header: '담당자', name: 'chargerName' },
        { header: '최종수정일', name: 'updateDate', formatter: (row) => dateFormatter(row.value) },
        { header: '', name: '', className: 'plan', renderer: { type: CustomBtnRender, options: {}}, width: 100, align: 'center' }
    ],
    rowHeaders: ['rowNum'],
    pageOptions: {
        useClient: true, // 페이징을 위해 필요
        perPage: 5
    },
    scrollX: false, // 가로 스크롤
    scrollY: false, // 세로 스크롤
    bodyHeight: 200,
    summary: {
       height: 30,
       position: 'bottom', // or 'top'
       columnContent: {
          mtrilOrderPlanCode: { // 컬럼명
               template: (valueMap) => {
                   return `총 ${valueMap.cnt}건`
               }
           }
       }
   }
});

/******************** Tui Grid 출력 ********************/
// 모달 El
let modalTitle = document.getElementById('modalTitle');
let confirmBtn = document.getElementById('confirmBtn');
let closeBtn = document.getElementById('closeBtn');
let mtrListDiv = document.getElementById('materialList');
let compListDiv = document.getElementById('companyList');
let planListDiv = document.getElementById('planList');
let insertListDiv = document.getElementById('insertList');

// 모달의 선택 목록 출력 (업체/자재/발주계획서 선택)
function loadModalGrid(type, obj){ // type: 'material' or 'company' or 'orderPlan'
  let {status, ...others} = obj;
  let query = new URLSearchParams(others);
  
  // 배열값이 있는 경우 쿼리스트링을 직접 만들어야 정상 출력
  if(status) status.forEach(code => query += `&status=${code}` );
	
	fetch(`/supply/${type}List?${query}`)
	.then(response => response.json())
	.then(result => {
    let data = result.data.contents;
    console.log(data);
    
    let gridArr = [
      {grid: prdGrid, div: mtrListDiv},
      {grid: companyGrid, div: compListDiv},
      {grid: planGrid, div: planListDiv}
    ];
    let gridArrIdx;
		if(type == 'material'){
			modalTitle.innerText = '자재 선택';
      gridArrIdx = 0;
		} else if(type == 'company'){
			modalTitle.innerText = '업체 선택';
      gridArrIdx = 1;
		} else {
      modalTitle.innerText = '발주계획서 목록';
      gridArrIdx = 2;
    }
    // 데이터 입력 
    gridArr[gridArrIdx].grid.resetData(data);
    gridArr[gridArrIdx].div.style.display = '';
		// 모달 표시
		$('#myModal').modal('show');
		if(gridArrIdx == 0) prdGrid.refreshLayout();
		else if(gridArrIdx == 1) companyGrid.refreshLayout();
    else planGrid.refreshLayout();
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
		productCode: mtrCodeBox.value,
		productColor: colorBox.value
	};
	loadModalGrid('company', dto);
});

// 발주계획 목록 검색 적용 
document.getElementById('planSearchBtn').addEventListener('click', () => {
  let checkedValues = document.querySelectorAll('input[name="status"]:checked');
  checkedValues = Array.from(checkedValues).map(ele => ele.value); // 가상의 노드들을 얕은 복사하여 배열처리
  
  let dto = {
    mtrilOrderPlanCode: document.getElementById('mtrilOrderPlanCode').value,
    chargerName: document.getElementById('scChargerName').value,
    dueMin: document.getElementById('dueMin').value,
    dueMax: document.getElementById('dueMax').value,
    status: checkedValues
  };
  loadModalGrid('orderPlan', dto);
});
	
let colorInfo = []; // 옵션별 정보 저장
// 자재/업체 선택 시 색상옵션 생성 및 재고+단가 불러오기
async function loadColorPriceInfo(row){
	if(mtrCodeBox.value == '') return; // 필수값
	let valueObj = {
		productCode: mtrCodeBox.value, 
		companyCode: compCodeBox.value
	};
	let query = new URLSearchParams(valueObj);
	
	await fetch(`/supply/optionPriceList?${query}`)
		.then(response => response.json())
		.then(result => {
			console.log(result);
			
			colorInfo = result; // 색상 선택에따라 내용을 변경하기 위해 저장
			
			// 옵션 태그 생성 및 기존 선택 반영
			let currentColor = colorBox.value == '' ? row.productColor : colorBox.value;
			let prdColorArr = result.map(obj => obj.PRODUCT_COLOR);
			colorBox.innerHTML = '<option value="null">미선택</option>';
			
			for(let color of prdColorArr){			
				let opt = document.createElement('option');
				opt.value = color;
				opt.innerText = color;
				colorBox.append(opt);
			}
			if(selectedMtr == null) selectedMtr = row; // 자재모달에서 직접 선택하지 않은 경우 selectedMtr null
            selectedMtr.PRODUCT_COLOR = 'null';
            colorInfo.push(selectedMtr); // 자재 기본정보(null옵션값) 맨끝에 추가
            
			colorBox.value = currentColor; // 선택값 다시 표시
			changeByColorInfo(colorBox.value);
	});	
}

colorBox.addEventListener('change', e => {
	changeByColorInfo(e.target.value);
});

// 색상 선택 시 단가 및 정보 재반영
function changeByColorInfo(val){
  let idx = colorInfo.findIndex(obj => obj.PRODUCT_COLOR == val);
  unitPriceBox.value = colorInfo[idx].UNIT_PRICE ? numberFormatter(colorInfo[idx].UNIT_PRICE) : colorInfo[idx].unitPrice;
  inventoryQyBox.value = colorInfo[idx].INVENTORY_QY ? numberFormatter(colorInfo[idx].INVENTORY_QY) : 0;
  storingQyBox.value = colorInfo[idx].STORING_QY ? numberFormatter(colorInfo[idx].STORING_QY) : 0;
}

/******************** 발주목록 추가 ********************/	
// 발주목록 데이터 추가
function insertRow(rowKey){
	let orderQy = orderQyBox.value;
	if((compCodeBox.value == '' || mtrCodeBox.value == '') && th == null){
		failToast('업체와 자재를 선택해주세요.');
		return;
	} else if(mtrCodeBox.value == ''){ // 타임리프 페이지(발주계획)에서는 업체 선택 필수 아님.
        failToast('자재를 선택해주세요.');
        return;
    }
	if(orderQy == ''){
		failToast('수량을 입력해주세요.');
        orderQyBox.focus();
		return;
	}
	// 입력값에 대해 유효성검사
	if(isNaN(orderQy)){ // 숫자가 아닌 경우
		failToast('수량에 문자가 들어갈 수 없습니다.');
        orderQyBox.focus();
		return;
	} else if (orderQy < 0){ // 음수인 경우
		failToast('수량은 음수가 될 수 없습니다.');
        orderQyBox.focus();
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
		amount: unitPrice * orderQyBox.value, // 단가 * 입력수량 = 총 금액
		colorInfo: colorInfo // 표시 항목 한꺼번에 저장
	};
	if(rowKey != null) orderGrid.setRow(rowKey, insertObj);
	else orderGrid.appendRow(insertObj);
	getSum();
    orderQyBox.value = '';
    if(isModifying) modifyMode(false);
    else orderQyBox.focus();
}

document.getElementById('insertBtn').addEventListener('click', () => insertRow());

orderQyBox.addEventListener('keyup', e => {
  if(e.key == 'Enter'){
    if(!isModifying) insertRow();
    else insertRow(modifyRowKey);
  }
});

let modifyRowKey;
// 발주목록에 넣은 데이터 수정하기
orderGrid.on('focusChange', async ev => {
	orderGrid.removeRowClassName(ev.prevRowKey, 'bg-blue'); // 이전 선택 행 배경색 삭제
	orderGrid.addRowClassName(ev.rowKey, 'bg-blue'); // 선택된 행 배경색 추가
	
	modifyRowKey = ev.rowKey; // rowKey 저장하여 수정 시 사용
	
	// 입력정보 재표시
	let row = orderGrid.getRow(ev.rowKey);
	
	compCodeBox.value = row.companyCode;
	compNameBox.value = row.companyName;
	mtrCodeBox.value = row.productCode;
	mtrNameBox.value = row.productName;
	orderQyBox.value = row.orderQy;
	unitNameBox.value = row.unitName;
	
    if(row.colorInfo == null) await loadColorPriceInfo(row);
	else colorInfo = row.colorInfo; // 저장해둔 값이 있으면 가져오기
	
    colorBox.innerHTML = '<option value="null">미선택</option>';
    	
    for(let i = 0; i < colorInfo.length - 1; i++){ // 마지막 null옵션 제외하고 태그 생성			
      let opt = document.createElement('option');
      opt.value = colorInfo[i].PRODUCT_COLOR;
      opt.innerText = colorInfo[i].PRODUCT_COLOR;
      colorBox.append(opt);
    }
    
    changeByColorInfo(row.productColor);     
    colorBox.value = row.productColor;
	
	modifyMode(true); // 수정모드로 전환
});

document.getElementById('modifyBtn').addEventListener('click', () => {
	insertRow(modifyRowKey);
});

document.getElementById('modifyCancelBtn').addEventListener('click', () => {
	modifyMode(false); // 수정모드 종료
});

// 수정버튼 토글기능
let isModifying = false;
function modifyMode(boolean){
	isModifying = boolean;
	if(isModifying){
		document.getElementById('insertBtn').style.display = 'none'; // 등록버튼 숨김
		document.getElementById('modifyCancelBtn').style.display = ''; // 수정취소버튼 노출
		document.getElementById('modifyBtn').style.display = ''; // 수정버튼 노출
	} else {
		document.getElementById('modifyBtn').style.display = 'none'; // 수정버튼 숨김
		document.getElementById('modifyCancelBtn').style.display = 'none'; // 수정취소버튼 숨김
		document.getElementById('insertBtn').style.display = ''; // 등록버튼 노출
		
		// 초기화
		document.getElementById('resetBtn').dispatchEvent(new Event('click')); // 리셋버튼 클릭 이벤트 동작
		document.getElementById('orderForm').reset(); // 폼 이벤트 - 입력값 모두 초기화
		orderGrid.removeRowClassName(modifyRowKey, 'bg-blue'); // 행 배경색 삭제
	}
}

// 그리드 summary 집계 반영
function getSum(){
	let totalAmount = orderGrid.getSummaryValues('amount').sum;
	$('#totalAmount').val(numberFormatter(totalAmount));
	
	let sum = orderGrid.getSummaryValues('orderQy').sum;
	$('#totalQy').val(numberFormatter(sum));
};

/******************** 발주서 등록 ********************/	
let insertObj;
function insertOrder(){
	let orderData = orderGrid.getData();
  let defaultRemark = document.getElementById('remark').value;
	
  let companyArr = new Set( orderData.map(data => data.companyCode) );
	companyArr = [...companyArr]; // 중복값 제거하여 다시 배열로 변환
	
	let modalData = [];
	insertObj = { companyArr };
	// 입력값에서 업체코드별로 키-값 분리하여 객체 생성
	// insertObj= { companyArr: ['COM0001',...], COM0001: {header, details},... }
	for(let compCode of companyArr){
		let compData = orderData.filter(data => data.companyCode == compCode);
		let headerObj = {
			companyCode: compCode,
			companyName: compData[0].companyName,
			dueDate: dueDateBox.value,
			chargerCode: session_user_code,
			chargerName: session_user_name,
			remark: defaultRemark
		};
		
		insertObj[compCode] = {}; // header, details 넣을 빈 공간
		insertObj[compCode].header = headerObj;
		insertObj[compCode].details = compData;
		
		// 등록확인 모달 표시용 합계 데이터 수집
		headerObj.totalQy = compData.reduce((acc, data) => acc + Number(data.orderQy), 0);
		headerObj.totalAmount = compData.reduce((acc, data) => acc + Number(data.amount), 0);
		modalData.push(headerObj);
	}
	
	// 업체별 발주서 비고/납기일 조정 모달 표시
	insertGrid.resetData(modalData);
    document.getElementById('insertCnt').innerText = modalData.length;
	modalTitle.innerText = '등록 확인';
  
	insertListDiv.style.display = '';
	confirmBtn.style.display = '';
	$('#myModal').modal('show');
	insertGrid.refreshLayout();
}

// 모달 내부 확인버튼 동작
confirmBtn.addEventListener('click', () => {
  insertGrid.getData().forEach(data => {
    let header = insertObj[data.companyCode].header;
    header.dueDate = data.dueDate;
    header.remark = data.remark;
  });
  console.log(insertObj);
  
	loading();
	fetch('/supply/mtrOrder', {
		method: 'POST',
		headers: {...headers, 'Content-Type': 'application/json'},
		body: JSON.stringify(insertObj)
	})
	.then(response => response.json())
	.then(result => {
		console.log(result);
		if(result == true){
			successToast('발주서가 등록되었습니다.');
			orderGrid.resetData([]);
			getSum(); // 합계 초기화
			closeAll();
		} else failToast('알 수 없는 오류로 실패했습니다.');
	});
});

// 모달 내부 닫기버튼 동작 (모두 숨김)
closeBtn.addEventListener('click', () => closeAll());
document.querySelector('.btn-close').addEventListener('click', () => closeAll());

function closeAll(){
	mtrListDiv.style.display = 'none';
	compListDiv.style.display = 'none';
    planListDiv.style.display = 'none';
	insertListDiv.style.display = 'none';
	confirmBtn.style.display = 'none';
}

/******************** 발주계획 불러오기 ********************/ 
function loadPlanData(planCode){  
  if(isModifying) modifyMode(false);
  
  fetch(`/supply/orderPlan/${planCode}`) // 단건조회한 결과를 발주목록에 그대로 추가함.
  .then(response => response.json())
  .then(result => {
    console.log(result);
    result.forEach(data => {
      data.amount = data.unitPrice * data.orderQy; // 총 금액 계산
    });
    orderGrid.resetData(result);
    getSum();
  });
}
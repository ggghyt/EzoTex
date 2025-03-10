/******************** select-option 동적 생성 ********************/
let lclasBox = document.getElementById('lclas');
let sclasBox = document.getElementById('sclas');

let mtrLclasBox = document.getElementById('mtr-lclas');
let mtrSclasBox = document.getElementById('mtr-sclas');

let colorBox = document.getElementById('color');
let sizeBox = document.getElementById('size');

document.addEventListener('DOMContentLoaded', () => {
	changeClas(lclasBox, sclasBox);
	changeClas(mtrLclasBox, mtrSclasBox);
	
	createModal({ 
		type: 'regist',
		confirm: insertBom
	});	
	$('#insertBtn').on('click', () => {
		let selectedBom = selectedMtrGrid.getData();
		if(selectedBom.length == 0) return; // 자재가 선택되지 않았으면 종료
		let isValid = validBom(selectedBom);
		if(!isValid) return; // 유효성 검사 실패 시 종료
		$('#simpleModal').modal('show'); // 유효성 검사 통과 시 등록 확인
	});
});

// 엑셀 내보내기 버튼 이벤트
document.getElementById('xlsx').addEventListener('click', () => {
  if(selectedMtrGrid.getRowCount() == 0) return; // 값이 없으면 미동작
  selectedMtrGrid.export('xlsx', {
    useFormattedValue: true,
    fileName: '자재명세서_' + selectedPrd.PRODUCT_CODE + '_' + document.getElementById('rgsde').value.replaceAll('-','')
  });
});

// 카테고리 변경 이벤트
const changeClas = function(lclasEle, sclasEle){
	lclasEle.addEventListener('change', () => {
		createOptions(sclasEle, `/product/category/${lclasEle.value}`);		
	});
}

// select-option 태그 생성 함수 (카테고리, 제품옵션 공통 적용)
const createOptions = async function(ele, uri){	
	if(ele === colorBox) sizeBox.innerHTML = '<option value="null">미선택</option>';
	if(ele === colorBox || ele === sizeBox) ele.innerHTML = '<option value="null">미선택</option>';
	else ele.innerHTML = '<option value="null">전체</option>';
	
	// 서버에서 데이터 불러오기
	let resultData = await fetch(uri)
	.then(response => response.json())
	.then(data => {		
		for(let value of data){	
            if((ele == colorBox || ele == sizeBox) && 
                value.productSize == null && value.productColor == null) continue; // null은 무시
			let opt = document.createElement('option');
			
			let prdOptVal = null; // 제품 색상/사이즈 옵션인 경우
			let prdOptText = null;
			if(ele === colorBox || ele === sizeBox){
				let discon = value.discontinued == 'N' ? '' : ' (단종)';
				if(ele === colorBox) {
					prdOptVal = value.productColor;
					prdOptText = value.productColor + discon;
				} else {
					prdOptVal = value.productSize;
					prdOptText = value.sizeName + discon;
				}
			}
			
			opt.value = prdOptVal != null ? prdOptVal : value;
			opt.innerText = prdOptText != null ? prdOptText : value;
			ele.append(opt);
		}
		return data;
	});
	return resultData;
}

/******************** Tui Grid Custom Renderer ********************/	
// 자재 그리드의 색상 select 커스텀 렌더링
class CustomSelectBox {
  constructor(props) {
	// props: 화면에 표시될 때마다 생성자가 실행되며 넘어오는 객체
	// props = grid, rowKey, columnInfo, value(데이터)
	const el = document.createElement('select');
	el.classList = 'form-control h-100 w-90';
	el.id = props.rowKey; // 태그 자체에 rowKey 저장		
	
	let nullOpt = document.createElement('option');
	nullOpt.value = null;
	nullOpt.innerText = '미선택';
	el.append(nullOpt);
	
	props.value.forEach(data => { // 단종되지 않았을 때만 옵션으로 추가
		if(data.productColor != null && data.discontinued != 'Y'){
			let opt = document.createElement('option');
			opt.value = data.productColor;
			opt.innerText = data.productColor;
			el.append(opt);								
		}
	});
	el.addEventListener('click', e =>  e.stopPropagation() ); // tui 그리드 셀 기본 이벤트 방지
  el.addEventListener('mousedown', e =>  e.stopPropagation() ); // tui 그리드 셀 기본 이벤트 방지
	// 옵션을 선택했을 때 저장된 배열에 데이터 반영
	el.addEventListener('change', (e) => {
		let rowKey = e.target.id; // 선택한 색상배열 인덱스
		mtrData[rowKey].mtrilColor = e.target.value; // 선택한 색상 반영
		
		let selected = selectedMtrGrid.getData();
		let idx = selected.findIndex(data => data.mtrilCode == mtrData[rowKey].mtrilCode);
		if(idx != -1){
    		selected[idx].mtrilColor = e.target.value;
    		selectedMtrGrid.resetData(selected);            
        }
    });
    this.el = el;
    this.render(props);
  }

  getElement() { return this.el; }

  render(props) {
	// 화면에 표시될 때마다 저장된 데이터로 반영
    this.el.value = mtrData[props.rowKey].mtrilColor;
  }
}

/******************** Tui Grid ********************/	
// 제품 그리드
const prdData = {
	api: { readData: { url: '/supply/bomProductList', method: 'GET' } }
};
	
const prdGrid = new Grid({
    el: document.getElementById('prdGrid'), // 해당 위치에 그리드 출력
    data: prdData,
    columns: [
        { header: '제품코드', name: 'PRODUCT_CODE', width: 100, sortable: true, align: 'center' },
        { header: '제품명', name: 'PRODUCT_NAME', whiteSpace: 'pre-line', sortable: true },
        { header: '상태', name: 'STATUS', width: 100, sortable: true, align: 'center',
          renderer: {
      	    styles: {
      	      fontWeight: 'bold',
      	      color: props => props.value == '완료' ? '#aaa' : 'orange'
      	    }
          }
        }
    ],
    rowHeaders: ['rowNum'],
    showDummyRows: true,
    bodyHeight: 600,
    pageOptions: {
        useClient: true, // 페이징을 위해 필요
        perPage: 15
  	},
  	scrollX: false, // 가로 스크롤
  	scrollY: false, // 세로 스크롤
  	summary: {
  		 height: 40,
	     position: 'bottom', // or 'top'
	     columnContent: {
	     		PRODUCT_CODE: { // 컬럼명
	             template: (valueMap) => {
	                 return `총 ${valueMap.cnt}건`
	             }
	         }
	     }
     }
});

// 자재 그리드
const mtrGrid = new Grid({
    el: document.getElementById('mtrGrid'), // 해당 위치에 그리드 출력
    columns: [
        { header: '자재코드', name: 'mtrilCode', width: 100, sortable: true, align: 'center' },
        { header: '자재명', name: 'mtrilName', whiteSpace: 'pre-line', sortable: true },
				{ header: '색상', name: 'colorList', renderer: { type: CustomSelectBox, options: {}} },
        { header: '단위', name: 'unitName', width: 100, sortable: true }
    ],
    rowHeaders: ['checkbox'],
  	scrollX: false, // 가로 스크롤
  	scrollY: true, // 세로 스크롤
    showDummyRows: true,
  	bodyHeight: 150
});

// 선택한 자재 그리드
const selectedMtrGrid = new Grid({
	el: document.getElementById('selectedMtrGrid'), // 해당 위치에 그리드 출력
    columns: [
        { header: '자재코드', name: 'mtrilCode', sortable: true, align: 'center' },
        { header: '자재명', name: 'mtrilName', whiteSpace: 'pre-line', sortable: true },
				{ header: '색상', name: 'mtrilColor', whiteSpace: 'pre-line', sortable: true,
					formatter: row => row.value == 'null' ? null : row.value
				},
        { header: '소요량', name: 'requireQy', sortable: true, editor: 'text', align: 'right',
        	formatter: row => numberFormatter(row.value) // 천단위 콤마 포맷 적용
        },
        { header: '단위', name: 'unitName', sortable: true }
    ],
  	scrollX: false, // 가로 스크롤
  	scrollY: true, // 세로 스크롤
    showDummyRows: true,
  	bodyHeight: 155,
  	summary: {
  		 height: 37,
	     position: 'bottom', // or 'top'
	     align: 'right',
	     columnContent: {
	     		unitName: { // 컬럼명
		             template: (valueMap) => {
		                 return `총 ${valueMap.cnt}건`
		             }
	            }
	     }
     }
});

/******************** Tui Grid 출력 ********************/	
// 자재 목록 불러오기
let mtrData = null; // 검색 및 입력값 저장용
let originBomData = null; // 등록 시 비교용 원본 bom
function loadMtrGrid(obj){
	let query = new URLSearchParams(obj); // 쿼리스트링으로 변환
	
	fetch(`/supply/bomMaterialList?${query}`)
	.then(response => response.json())
	.then(result => {
		let data = result.data.contents;
		originBomData = structuredClone(data); // 원본 깊은복사
		
		let saveCk = document.getElementById('remember').checked; // 기억하기 체크되어 있으면 재출력하지 않음.
        if(saveCk == true && !isReset) return;// 기억하기나 초기화버튼 동작이 아닌 경우에만 재출력
		
		selectedMtrGrid.resetData([]); // 선택된 자재 초기화
		mtrGrid.resetData(data); // 데이터 입력
		mtrData = data;
		
		data.forEach((obj, idx) => {
			if(obj.requireQy != null) mtrGrid.check(idx) // bom 등록된 자재는 자동 선택
		});
		
		let rgsde = dateFormatter(data[0].rgsde);
		let charger = data[0].chargerName == null ? session_user_name : data[0].chargerName;
		document.getElementById('rgsde').value = rgsde;
		document.getElementById('chargerName').value = charger;
	});
}

/******************** 제품/자재 선택 ********************/	
let selectedPrd = null;
let lastClicked = null; // 페이지 이동 시에도 이전 선택 기억하기 위함.

// 선택된 행 강조 & 정보 가져오기
prdGrid.on('focusChange', async ev => {
	// 배경색 클래스 적용
	prdGrid.removeRowClassName(lastClicked, 'bg-blue'); // 이전 선택 행 배경색 삭제
	prdGrid.addRowClassName(ev.rowKey, 'bg-blue'); // 선택된 행 배경색 추가
	lastClicked = ev.rowKey; // 선택된 행 기억
	
	// 선택한 정보 가져오기
	selectedPrd = prdGrid.getRow(ev.rowKey);
	document.getElementById('selectedPrdCode').value = selectedPrd.PRODUCT_CODE;
	document.getElementById('selectedPrdName').value = selectedPrd.PRODUCT_NAME;
	let colorData = await createOptions(colorBox, `/supply/options/${selectedPrd.PRODUCT_CODE}`); // 선택한 제품의 색상 목록 불러오기
	console.log('colorData', colorData);
	if(colorData.length > 0 && colorData[0].productColor == null) createOptions(sizeBox, `/supply/optionSize/${selectedPrd.PRODUCT_CODE}`); // 색상 없으면 사이즈 목록 불러오기
	loadMtrGrid( {productCode: selectedPrd.PRODUCT_CODE} ); // 선택한 제품의 bom 자재 출력
});

// 선택한 제품-색상의 사이즈 불러오기
const changeOpt = () => {
	let selectedOpt = {
		productCode: selectedPrd.PRODUCT_CODE,
		productColor: colorBox.value,
		productSize: sizeBox.value
	};
	loadMtrGrid(selectedOpt);
}

colorBox.addEventListener('change', e => {
	createOptions(sizeBox, `/supply/options/${selectedPrd.PRODUCT_CODE}/${e.target.value}`);
	changeOpt();
});

sizeBox.addEventListener('change', () => {
	changeOpt();
})

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

// 자재 목록 검색 적용
let rowEventOn = true;
document.getElementById('mtrSearchBtn').addEventListener('click', () => {
	// 검색조건 가져오기
	let searchObj = {
		mtrilCode: document.getElementById('mtrilCode').value,
		mtrilName: document.getElementById('mtrilName').value,
		lclas: document.getElementById('mtr-lclas').value,
		sclas: document.getElementById('mtr-sclas').value
	};
	// 원본 데이터 중 필터링 반영
	let filtered = mtrData.filter(obj => {
		return obj.mtrilCode.indexOf(searchObj.mtrilCode) != -1  &&
					 obj.mtrilName.indexOf(searchObj.mtrilName) != -1 &&
					 (searchObj.lclas == 'null' ? true : obj.lclas == searchObj.lclas) &&
					 (searchObj.sclas == 'null' ? true : obj.sclas == searchObj.sclas);
	});
	mtrGrid.resetData(filtered);
	
	rowEventOn = false; // check 이벤트 appendRow() 임시 방지
	filtered.forEach(data => { // 체크박스 상태 동기화
		let findArr = selectedMtrGrid.findRows({mtrilCode: data.mtrilCode});
			if(findArr.length != 0) mtrGrid.check(data.rowKey);
			else mtrGrid.uncheck(data.rowKey);
	});
	rowEventOn = true;
});

// 엔터키 즉시 검색
document.getElementById('prdForm').addEventListener('keyup', e => {
    if(e.key == 'Enter') document.getElementById('prdSearchBtn').dispatchEvent(new Event('click'));
});
document.getElementById('mtrForm').addEventListener('keyup', e => {
    if(e.key == 'Enter') document.getElementById('mtrSearchBtn').dispatchEvent(new Event('click'));
});

// 자재 선택 시 그리드 추가/삭제
// ** appendRow(), removeRow() 메소드 사용 시 동기화에 문제 있음
function addRow(row){ // grid.getRow(ev.rowKey)를 인수로 받음.
	mtrGrid.addRowClassName(row.rowKey, 'bg-blue'); // 행 배경색 추가
	if(rowEventOn){
		let currentData = selectedMtrGrid.getData();
		// 없는 데이터라면 행을 직접 추가하여 반영
		let findArr = selectedMtrGrid.findRows({mtrilCode: row.mtrilCode}); // 행 추가 중복오류 방지
		if(findArr.length == 0){
			currentData.push(row);
		}
		selectedMtrGrid.resetData(currentData);
	}
};

function removeRow(rowKey){ // ev.rowKey를 인수로 받음.
	mtrGrid.removeRowClassName(rowKey, 'bg-blue'); // 취소한 행 배경색 삭제
	if(rowEventOn){
		let currentData = selectedMtrGrid.getData();
		// 남길 행을 계산하여 반영
		let remainArr = currentData.filter(data => {
			return data.mtrilCode != mtrGrid.getRow(rowKey).mtrilCode;
		});
		selectedMtrGrid.resetData(remainArr);
	}
};

mtrGrid.on('check', ev => {
	addRow(mtrData[ev.rowKey]);
});

mtrGrid.on('uncheck', ev => {
	removeRow(ev.rowKey);
});

// 전체 선택/해제
mtrGrid.on('checkAll', () => {
	let gridData = mtrGrid.getData();
	gridData.forEach((data) => {
		addRow(mtrData[data.rowKey]);
	});
});

mtrGrid.on('uncheckAll', () => {
	let gridData = mtrGrid.getData();
	gridData.forEach((data) => {
		removeRow(data.rowKey);		
	});
});

// 입력값 유효성 검사
selectedMtrGrid.on('afterChange', ev => {
	let changed = ev.changes[0];
	let rowKey = changed.rowKey;
	
	selectedMtrGrid.check(rowKey);
	let row = selectedMtrGrid.getRow(rowKey);
	let val = changed.value;
	
	if(isNaN(val) || val < 0){ // 입력값이 유효하지 않은 경우
        if(isNaN(val)) failToast('입력값은 문자가 들어갈 수 없습니다.');
        else failToast('입력값은 음수가 될 수 없습니다.');
        val = changed.prevValue;
        row.requireQy = changed.prevValue == null ? 0 : changed.prevValue;
        selectedMtrGrid.setRow(rowKey, row);
        return;
    }
	
	// 유효한 값인 경우, 자재 원본 데이터에서 해당하는 rowKey를 찾아 입력값 저장
	let mtrRowKey;
	mtrData.forEach((data) => {
		if(data.mtrilCode == row.mtrilCode){
			mtrRowKey = data.rowKey;
			data.requireQy = val;
			return;
		}
	});
	mtrGrid.setRow(mtrRowKey, row);
	mtrGrid.check(mtrRowKey); // 체크상태 다시 반영
	selectedMtrGrid.startEditing(rowKey + 1, 'requireQy');
});

// 초기화 버튼 동작
let isReset = false;
document.getElementById('resetBtn').addEventListener('click', () => {
    if(selectedPrd == null) return; // 선택한 제품이 없으면 미실행
	createModal({ 
		type: 'modify',
		header: '초기화',
		content: '입력한 자재 정보를 모두 초기화하시겠습니까?',
		buttonText: '확인',
		confirm(){
            isReset = true;
			changeOpt();
		}
	});
	$('#simpleModal').modal('show'); // 유효성 검사 통과 시 등록 확인
});

/******************** BOM 자재 등록 ********************/
function insertBom(){
	let selectedBom = selectedMtrGrid.getData();
	
	let headerObj = {
		productCode: selectedPrd.PRODUCT_CODE,
		productColor: colorBox.value == 'null' ? null : colorBox.value.replace(' (단종)', ''),
		productSize: sizeBox.value == 'null' ? null : sizeBox.value.replace(' (단종)', ''),
		chargerCode: session_user_code,
		chargerName: session_user_name
	};
	
	let detailArr = selectedBom.map(obj => {
		return {
			mtrilCode: obj.mtrilCode,
			mtrilColor: obj.mtrilColor == 'null' ? null : obj.mtrilColor,
			requireQy: obj.requireQy
		};
	});
	
	loading();
	fetch('/supply/bom', {
		method: 'POST',
		headers: {...headers, 'Content-Type': 'application/json'},
		body: JSON.stringify({headerObj, detailArr})
	})
	.then(response => response.json())
	.then(async result => {
		console.log(result);
		if(result == true){ // 등록 성공 시
			successToast('작업이 완료되었습니다.');
			originBomData = selectedBom; // 비교값을 입력한 값으로 업데이트 (중복 등록 방지)
			
			await fetch('/supply/bomAllInserted/' + selectedPrd.PRODUCT_CODE) // 모든 옵션 등록됐는지 확인
			.then(response => response.json())
			.then(allInserted => selectedPrd.STATUS = allInserted == true ? '완료' : '등록중');
			prdGrid.setRow(selectedPrd.rowKey, selectedPrd);
		} else failToast('작업을 실패했습니다.');
	});
};

// 입력값 유효성 검사
function validBom(selectedBom){
	let isZero = false; // 소요량이 0인 값이 있는지 검증
	
	let updatedArr = selectedBom.filter(data => {
		if(data.requireQy == 0 || data.requireQy == null) { // 수량이 0인 값이 있으면 즉시 종료
			isZero = true;
			return false;
		} else {
			for(let origin of originBomData){ // 원본 데이터와 비교하여 변경되었는지 확인
				if(origin.mtrilCode == data.mtrilCode && origin.mtrilColor == data.mtrilColor &&
				   origin.requireQy == data.requireQy){
					return false;
				}
			}	
		}
		return true; // true인 값만 배열에 남김
	});
	
	// 입력값 중 0이 있으면 종료
	if(isZero){
		failToast('소요량을 모두 입력하지 않았습니다.');
		return false;
	}
	// 새로운 값이 하나도 없으면 종료
	if(updatedArr.length == 0) {
		failToast('변경된 값이 없습니다.');
		return false;
	}
	return true; // 유효성 검사 통과
}
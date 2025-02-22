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
		confirm: insertBom,
		loading: false
	});	
	$('#insertBtn').on('click', () => {
		let selectedBom = selectedMtrGrid.getData();
		if(selectedBom.length == 0) return; // 자재가 선택되지 않았으면 종료
		let isValid = validBom(selectedBom);
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

// select-option 태그 생성 함수 (카테고리, 제품옵션 공통 적용)
const createOptions = function(ele, uri){	
	// 서버에서 데이터 불러오기
	fetch(uri)
	.then(response => response.json())
	.then(data => {
		ele.innerHTML = '<option value="null">전체</option>';
		sizeBox.innerHTML = '<option value="null">전체</option>';
		
		for(let value of data){			
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
	});
}

/******************** Tui Grid Custom Renderer ********************/	
// 자재 그리드의 색상 select 커스텀 렌더링
class CustomSelectBox {
  constructor(props) {
	// props: 화면에 표시될 때마다 생성자가 실행되며 넘어오는 객체
	// props = grid, rowKey, columnInfo, value(데이터)
	const el = document.createElement('select');
	el.classList = 'form-control h-100 w-75';
	el.id = props.rowKey; // 태그 자체에 rowKey 저장		
	
	let nullOpt = document.createElement('option');
	nullOpt.value = null;
	el.append(nullOpt);
	
	props.value.forEach(data => { // 단종되지 않았을 때만 옵션으로 추가
		if(data.productColor != null && data.discontinued != 'Y'){
			let opt = document.createElement('option');
			opt.value = data.productColor;
			opt.innerText = data.productColor;
			el.append(opt);								
		}
	});
	el.addEventListener('mousedown', (e) => {
      e.stopPropagation(); // tui 그리드 셀 기본 이벤트 방지
	});
	// 옵션을 선택했을 때 저장된 배열에 데이터 반영
	el.addEventListener('change', (e) => {
		let rowKey = e.target.id; // 선택한 색상배열 인덱스
		mtrData[rowKey].mtrilColor = e.target.value; // 선택한 색상 반영
		
		let selectedRow = selectedMtrGrid.getRow(rowKey);
		if(selectedRow != null){
			selectedRow.mtrilColor = e.target.value;
			selectedMtrGrid.setRow(rowKey, selectedRow); // 다른 그리드에도 반영			
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

/******************** Tui Grid 출력 ********************/	
// 제품 그리드
const prdData = {
	api: { readData: { url: '/supply/bomProductList', method: 'GET' } }
};
	
const prdGrid = new Grid({
    el: document.getElementById('prdGrid'), // 해당 위치에 그리드 출력
    data: prdData,
    columns: [
        { header: '제품코드', name: 'PRODUCT_CODE', width: 100, sortable: true },
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
        { header: '자재코드', name: 'mtrilCode', width: 100, sortable: true },
        { header: '자재명', name: 'mtrilName', whiteSpace: 'pre-line', sortable: true },
		{ header: '색상', name: 'colorList', renderer: { type: CustomSelectBox, options: {}} },
        { header: '단위', name: 'unitName', width: 100, sortable: true }
    ],
    rowHeaders: ['checkbox'],
  	scrollX: false, // 가로 스크롤
  	scrollY: true, // 세로 스크롤
  	bodyHeight: 150
});

// 선택한 자재 그리드
const selectedMtrGrid = new Grid({
	el: document.getElementById('selectedMtrGrid'), // 해당 위치에 그리드 출력
    columns: [
        { header: '자재코드', name: 'mtrilCode', sortable: true },
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
  	bodyHeight: 150	
});

// 자재 목록 불러오기
let mtrData = null; // 검색 및 입력값 저장용
let originBomData = null; // 등록 시 비교용 원본 bom
function loadMtrGrid(obj){
	let query = new URLSearchParams(obj); // 쿼리스트링으로 변환
	
	fetch(`/supply/bomMaterialList?${query}`)
	.then(response => response.json())
	.then(result => {
		let data = result.data.contents;
		selectedMtrGrid.resetData([]); // 선택된 자재 초기화
		mtrGrid.resetData(data); // 데이터 입력
		mtrData = data;
		console.log(data);
		
		data.forEach((obj, idx) => {
			if(obj.requireQy != null) mtrGrid.check(idx) // bom 등록된 자재는 자동 선택
		});
		
		originBomData = selectedMtrGrid.getData();
		
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
prdGrid.on('focusChange', ev => {
	// 배경색 클래스 적용
	prdGrid.removeRowClassName(lastClicked, 'bg-blue'); // 이전 선택 행 배경색 삭제
	prdGrid.addRowClassName(ev.rowKey, 'bg-blue'); // 선택된 행 배경색 추가
	lastClicked = ev.rowKey; // 선택된 행 기억
	
	// 선택한 정보 가져오기
	selectedPrd = prdGrid.getRow(ev.rowKey);
	document.getElementById('selectedPrdCode').value = selectedPrd.PRODUCT_CODE;
	document.getElementById('selectedPrdName').value = selectedPrd.PRODUCT_NAME;
	createOptions(colorBox, `/supply/options/${selectedPrd.PRODUCT_CODE}`); // 선택한 제품의 색상 목록 불러오기
	loadMtrGrid( {productCode: selectedPrd.PRODUCT_CODE} ); // 선택한 제품의 bom 자재 출력
});

// 선택한 제품-색상의 사이즈 불러오기
const changeOpt = (isReset) => {
	let saveCk = document.getElementById('remember').checked; // 기억하기 체크되어 있으면 재출력하지 않음.
	if(saveCk == false || isReset){
		let selectedOpt = {
			productCode: selectedPrd.PRODUCT_CODE,
			productColor: colorBox.value,
			productSize: sizeBox.value
		};
		loadMtrGrid(selectedOpt);		
	}
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
	//addRow(mtrGrid.getRow(ev.rowKey));
	addRow(mtrData[ev.rowKey]);
});

mtrGrid.on('uncheck', ev => {
	removeRow(ev.rowKey);
});

// 전체 선택/해제
mtrGrid.on('checkAll', () => {
	let gridData = mtrGrid.getData();
	gridData.forEach((data) => {
		//addRow(data);		
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
	if(isNaN(val)){ // 입력값이 숫자가 아닌 경우
		failToast('입력값은 문자가 들어갈 수 없습니다.');
		
		// 이전 값이 있으면 이전 값으로, 없으면 0으로 출력하고 종료
		row.requireQy = changed.prevValue == null ? 0 : changed.prevValue;
		selectedMtrGrid.setRow(rowKey, row);
		return;
	} else if (val < 0){ // 음수면 양수로 전환 
		val = val * -1;
		row.requireQy = val;
		selectedMtrGrid.setRow(rowKey, row);
		failToast('입력값은 음수가 될 수 없습니다.');
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
document.getElementById('resetBtn').addEventListener('click', () => {
	createModal({ 
		type: 'confirm',
		content: '입력한 자재 정보를 모두 초기화하시겠습니까?',
		confirm(){
			changeOpt(true);
		}
	});
	$('#simpleModal').modal('show'); // 유효성 검사 통과 시 등록 확인
});

/******************** BOM 자재 등록 ********************/
function insertBom(loading){
	let selectedBom = selectedMtrGrid.getData();
	
	let headerObj = {
		productCode: selectedPrd.PRODUCT_CODE,
		productColor: colorBox.value.replace(' (단종)', ''),
		productSize: sizeBox.value,
		chargerCode: session_user_code,
		chargerName: session_user_name
	};
	
	let detailArr = selectedBom.map(obj => {
		return {
			mtrilCode: obj.mtrilCode,
			mtrilColor: obj.mtrilColor,
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
	.then(result => {
		console.log(result);
		if(result == true){
			successToast('자재명세서가 등록되었습니다.');
			originBomData = selectedBom; // 비교값을 입력한 값으로 업데이트 (중복 등록 방지)
			selectedPrd.STATUS = '완료'; // 등록 상태 반영
			prdGrid.setRow(selectedPrd.rowKey, selectedPrd);
		} else failToast('알 수 없는 오류로 실패했습니다.');
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
				if(origin.mtrilCode == data.mtrilCode && origin.requireQy == data.requireQy){
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
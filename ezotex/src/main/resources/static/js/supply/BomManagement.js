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
});

// 대분류 onChange 이벤트 등록 함수
const changeClas = function(lclasEle, sclasEle){
	lclasEle.addEventListener('change', () => {
		loadOptions(sclasEle, `/product/category/${lclasEle.value}`);		
	});
}

// select-option 태그 생성 함수 (카테고리, 제품옵션 공통 적용)
const loadOptions = function(ele, uri){	
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

/******************** tui grid 출력 ********************/	
// 제품 그리드
const prdData = {
	api: {
		readData: { url: '/supply/bomProductList', method: 'GET' }
	},
	contentType : 'application/json' // 데이터 전달 시 인코딩 필요
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
    data: [],
    columns: [
        { header: '자재코드', name: 'mtrilCode', width: 100, sortable: true },
        { header: '자재명', name: 'mtrilName', whiteSpace: 'pre-line', sortable: true },
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
    data: [],
    columns: [
        { header: '자재코드', name: 'mtrilCode', width: 100, sortable: true },
        { header: '자재명', name: 'mtrilName', whiteSpace: 'pre-line', sortable: true },
        { header: '소요량', name: 'requireQy', width: 100, sortable: true, editor: 'text', align: 'right',
        	formatter: row => Number(row.value).toLocaleString() // 천단위 콤마 포맷 적용
        },
        { header: '단위', name: 'unitName', width: 100, sortable: true }
    ],
  	scrollX: false, // 가로 스크롤
  	scrollY: true, // 세로 스크롤
  	bodyHeight: 150	
});

let originData = null; // 검색용 원본 데이터 저장
const loadMtrGrid = function(obj){
	let query = new URLSearchParams(obj); // 쿼리스트링으로 변환
	// 서버에서 데이터 불러오기
	fetch(`/supply/bomMaterialList?${query}`)
	.then(response => response.json())
	.then(result => {
		let data = result.data.contents;
		selectedMtrGrid.resetData([]); // 선택된 자재 초기화
		mtrGrid.resetData(data); // 데이터 입력
		originData = data; // 저장된 데이터 초기화
		
		data.forEach((obj, idx) => {
			if(obj.requireQy != null) mtrGrid.check(idx) // bom 등록된 자재는 자동 선택
		});
		
		let rgsde = dateFormmater(data[0].rgsde);
		let charger = data[0].chargerName == null ? session_user_name : data[0].chargerName;
		document.getElementById('rgsde').value = rgsde;
		document.getElementById('chargerName').value = charger;
	});
}

/******************** 제품/자재 선택 ********************/	
let selectedPrdCode = null;
let lastClicked = null; // 페이지 이동 시에도 이전 선택 기억하기 위함.

// 선택된 행 강조 & 정보 가져오기
prdGrid.on('focusChange', ev => {
	// 배경색 클래스 적용
	prdGrid.removeRowClassName(lastClicked, 'bg-blue'); // 이전 선택 행 배경색 삭제
	prdGrid.addRowClassName(ev.rowKey, 'bg-blue'); // 선택된 행 배경색 추가
	lastClicked = ev.rowKey; // 선택된 행 기억
	
	// 선택한 정보 가져오기
	let selected = prdGrid.getRow(ev.rowKey);
	selectedPrdCode = selected.PRODUCT_CODE;
	document.getElementById('selectedPrdCode').value = selectedPrdCode;
	document.getElementById('selectedPrdName').value = selected.PRODUCT_NAME;
	loadOptions(colorBox, `/supply/options/${selectedPrdCode}`); // 선택한 제품의 색상 목록 불러오기
	loadMtrGrid( {productCode: selectedPrdCode} ); // 선택한 제품의 bom 자재 출력
});

// 선택한 제품-색상의 사이즈 불러오기
const changeOpt = () => {
	let saveCk = document.getElementById('remember').checked; // 기억하기 체크되어 있으면 재출력하지 않음.
	if(saveCk == false){
		let selectedOpt = {
			productCode: selectedPrdCode,
			productColor: colorBox.value,
			productSize: sizeBox.value
		};
		loadMtrGrid(selectedOpt);		
	}
}

colorBox.addEventListener('change', e => {
	loadOptions(sizeBox, `/supply/options/${selectedPrdCode}/${e.target.value}`);
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
	// 전체 데이터 중 필터링 반영
	let filtered = originData.filter(obj => {
		return obj.mtrilCode.indexOf(searchObj.mtrilCode) != -1  &&
					 obj.mtrilName.indexOf(searchObj.mtrilName) != -1 &&
					 (searchObj.lclas == 'null' ? true : obj.lclas == searchObj.lclas) &&
					 (searchObj.sclas == 'null' ? true : obj.sclas == searchObj.sclas);
	});
	mtrGrid.resetData(filtered);
	
	rowEventOn = false; // check 이벤트 appendRow() 임시 방지
	for(let i = 0; i <= mtrGrid.getRowCount(); i++){ // 체크박스 상태 동기화
		if(mtrGrid.getRowClassName(i)[0] == 'bg-blue') mtrGrid.check(i);
	}
	rowEventOn = true;
});

// 자재 선택 시 그리드 이동
mtrGrid.on('check', ev => {
	if(rowEventOn){
		selectedMtrGrid.appendRow(mtrGrid.getRow(ev.rowKey), {focus: true}); // 행 추가
		mtrGrid.addRowClassName(ev.rowKey, 'bg-blue'); // 선택된 행 배경색 추가		
	}
});

mtrGrid.on('uncheck', ev => {
	mtrGrid.removeRowClassName(ev.rowKey, 'bg-blue'); // 취소한 행 배경색 삭제
	
	let row = mtrGrid.getRow(ev.rowKey); // 선택된 데이터와 취소한 데이터 비교하여 제거
	let find = selectedMtrGrid.findRows({mtrilCode: row.mtrilCode});
	selectedMtrGrid.removeRow(find[0].rowKey);
});

/******************** BOM 자재 등록 ********************/
document.getElementById('insertBtn').addEventListener('click', () => {
	let datas = selectedMtrGrid.getData();
	console.log(datas);
	let headerObj = {
		productCode: selectedPrdCode,
		productColor: colorBox.value,
		productSize: sizeBox.value,
		chargerCode: session_user_code,
		chargerName: session_user_name
	};
	
	let detailArr = datas.map(obj => {
		return {
			mtrilCode: obj.mtrilCode,
			requireQy: obj.requireQy
		};
	});
	console.log({headerObj, detailArr});
	
	fetch('/supply/bom', {
		method: 'POST',
		headers: {...headers, 'Content-Type': 'application/json'},
		body: JSON.stringify({headerObj, detailArr})
	})
	.then(response => response.json())
	.then(result => {
		console.log(result);
		if(result == true){
			alert('등록완료');
		}
	});
});	
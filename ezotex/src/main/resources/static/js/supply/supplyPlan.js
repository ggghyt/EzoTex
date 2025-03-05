/******************** select-option 동적 생성 ********************/
let lclasBox = document.getElementById('lclas');
let sclasBox = document.getElementById('sclas');

let yearBox = document.getElementById('year');
let seasonBox = document.getElementById('season');
let seasonOpts = document.querySelectorAll('#season option'); // null,봄,여름,가을,겨울 옵션이 그대로 담긴 노드배열

let today = new Date();
let thisYear = today.getFullYear();
let thisMonth = today.getMonth();

document.addEventListener('DOMContentLoaded', () => {
	// 현재 날짜에 따라 공급년도 및 시즌 제한 (시즌에서 한 달 이상 남아있어야 등록 가능)
	if(thisMonth >= 10){ // 11~12월인 경우 내년 봄시즌부터 등록 가능
		yearBox.value = thisYear + 1;
		yearBox.min = thisYear + 1;
	} else {
		yearBox.value = thisYear;
		yearBox.min = thisYear;
		validSeason(thisYear);
	}
	makeQuantityTag(); // 커스텀 input 생성 함수
	
	changeClas(lclasBox, sclasBox);
	//document.getElementById('chargerName').value = session_user_name;
	
	createModal({ 
		type: 'regist',
		confirm: insertPlan
	});
	$('#insertBtn').on('click', () => {
		let supplyPlan = supplyGrid.getData();
		if(supplyPlan.length == 0) return; // 아무것도 입력되지 않았으면 종료
		$('#simpleModal').modal('show'); // 입력값이 있다면 등록 모달 표시
	});
});

// 로그인정보 fetch 이후에 실행되는 커스텀 이벤트
document.addEventListener('login', () => $('#chargerName').val(session_user_name))

// 대분류 onChange 이벤트 등록 함수
function changeClas(lclasEle, sclasEle){
	lclasEle.addEventListener('change', () => {
		createOptions(sclasEle, `/product/category/${lclasEle.value}`);		
	});
}

function createOptions(ele, uri){	
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
// 공급계획 총계 그리드 행 삭제버튼 커스텀 렌더링
class CustomBtnRender {
  constructor(props) {
		// props: 화면에 표시될 때마다 생성자가 실행되며 넘어오는 객체
		// props = grid, rowKey, columnInfo, value(데이터)
		const el = document.createElement('button');
		el.classList = 'btn btn-outline-danger btn-sm';
		el.id = props.rowKey; // 태그 자체에 rowKey 저장
		el.innerText = '삭제';
		
		el.addEventListener('click', (e) => {
	      let rowKey = e.target.id;
	      let row = supplyGrid.getRow(rowKey);
		  // 현재 선택된 제품이 아닌 경우에만 삭제 실행
	      if(row.productCode != selectedPrd.productCode){ 
			supplyGrid.removeRow(rowKey);
			
			let prdRows = prdGrid.findRows({productCode: row.productCode});
			let prdRowKey = prdRows[0].rowKey;
			prdRows[0].inserted = null; // 제품 그리드에 체크 아이콘 숨김
			prdGrid.setRow(prdRowKey, prdRows[0]);
		  }
	      else failToast('현재 작성중인 제품은 삭제할 수 없습니다.');
    	});
    this.el = el;
    this.render(props);
  }
  getElement() { return this.el; }
  render(props) {	
    this.el.value = props.value;
  }
}

// 제품 공급계획 입력 시 체크 표시
class CustomCheckRender {
  constructor(props) {
	const el = document.createElement('i');
	el.classList = 'fa-solid fa-check';
	el.style = `color: #4b96e6; display: ${props.value == null ? 'none' : 'block'}`;
    this.el = el;
    this.render(props);
  }
  getElement() { return this.el; }
  render(props) {
    this.el.value = props.value;
  }
}

/******************** Tui Grid ********************/	
// 제품 그리드
const prdData = {
	api: { readData: { url: '/supply/productList', method: 'GET' } }
};
	
const prdGrid = new Grid({
    el: document.getElementById('prdGrid'), // 해당 위치에 그리드 출력
    data: prdData,
    columns: [
        { header: '제품코드', name: 'productCode', width: 100, sortable: true },
        { header: '제품명', name: 'productName', whiteSpace: 'pre-line', sortable: true },
        { header: ' ', name: 'inserted', renderer: { type: CustomCheckRender, options: {}}, width: 50, align: 'center' }
    ],
    rowHeaders: ['rowNum'],
    showDummyRows: true,
    bodyHeight: 483,
    pageOptions: {
        useClient: true, // 페이징을 위해 필요
        perPage: 12
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

// 옵션 그리드
const optionGrid = new Grid({
	el: document.getElementById('optionGrid'), // 컨테이너 엘리먼트
    columns: [
        //{ header: "색상/사이즈", name: "PRODUCT_COLOR", width: 80 }
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
        { header: '제품코드', name: 'productCode', width: 100, sortable: true },
        { header: '제품명', name: 'productName', whiteSpace: 'pre-line', sortable: true },
        { header: '공급계획일자', name: 'supplyDate', width: 100, sortable: true, editor: 'datePicker' },
        { header: '총 수량', name: 'totalQy', width: 100, sortable: true, align: 'right',
				  formatter: (row) => numberFormatter(row.value) }, // 천단위 콤마 포맷 적용
		{ header: '', name: '', renderer: { type: CustomBtnRender, options: {}}, width: 70, align: 'center' },
		{ header: '데이터', name: 'allData', hidden: true },
		{ header: '컬럼정보', name: 'allColumn', hidden: true }
    ],
    rowHeaders: ['rowNum'],
  	scrollX: false, // 가로 스크롤
  	scrollY: true, // 세로 스크롤
  	bodyHeight: 202,
	summary: {
	     columnContent: {
	     		totalQy: {  }
	     }
   }
});

/******************** Tui Grid 출력 ********************/	
// 선택한 제품의 옵션별 입력양식 출력
function loadBlankGrid(productCode){
	fetch(`/supply/optionPivot/${productCode}`)
	.then(response => response.json())
	.then(result => {
		let data = result.data.contents;
		console.log(data);
		
		let columns = [{ header: "색상/사이즈", name: "PRODUCT_COLOR", width: 80, align: "center" }];
		let sizeNmArr = [];
		
		// data 형식: [{"PRODUCT_COLOR": "BLACK", "110": 0, "S": 0, "L": 0, "M": 0, ...}, ...]
		// pivot으로 가져온 사이즈명을 분리하여 컬럼 지정
		data.forEach((row, idx) => {
			let { PRODUCT_COLOR, rowKey, sortKey, uniqueKey, rowSpanMap, _attributes, _disabledPriority, _relationListItemMap, 
						...sizeNms } = row;
			if(idx == 0) sizeNmArr = Object.keys(sizeNms);
			// 해당 제품에 해당 색상, 사이즈가 있는지 0,1로 미리 가져옴.
			// 각 컬럼 공통코드에도 입력가능여부 반영: [{"SI01": -1, "SI02": 0, ...} ...]
			sizeNmArr.forEach((nm) => {
				let commonCode = getSizeCommonCode(nm);
				row[commonCode] = row[nm] == 1 ? 0 : -1; // -1이면 입력할 수 없는 셀
			});
		});
		
		sizeNmArr
		.sort((current, next) => { // 공통코드 기준 오름차순 정렬
			return getSizeCommonCode(current).substr(2,3) - getSizeCommonCode(next).substr(2,3);
		})
		.forEach((nm) => { // 컬럼 생성
			let newCol = { header: nm, name: getSizeCommonCode(nm), editor: 'text', align: 'right', 
				             formatter: row => {
											let value = numberFormatter(row.value)
											return value == -1 ? null : value; // 입력할 수 없는 셀은 빈칸 처리
					 		 } 
					 	 };
			columns.push(newCol);			
		});
		
		optionGrid.resetData(data); // 데이터 입력
		optionGrid.setColumns(columns); // 컬럼 입력
	});
}

// 사이즈/색상 중 한 가지만 있거나 단일제품인 경우
async function loadBlankList(productCode){
	let result = await fetch(`/supply/optionList/${productCode}`)
	.then(response => response.json())
	.then(result => {
		let data = result.data.contents;
		let columns = [];
		
		 // 단일제품이 아닌 경우 (사이즈/색상 중 한 가지라도 있는 경우)
		if(data.length > 0){			
			let sizeCnt = 0;
			for(let obj of data){
				// 한 행이라도 사이즈/색상 둘 다 있으면 반복문 종료
				if(obj.productSize != null && obj.productColor != null)	return false;
				else if(obj.productSize != null) sizeCnt++;
			};
			
			// 컬럼, 데이터 반영
			if(sizeCnt > 0) columns.push({ header: "사이즈", name: "sizeName" });
			else columns.push({ header: "색상", name: "productColor" });
			data.forEach(obj => obj.qy = 0); // null과 구분하기 위해 기본값 넣음.
			optionGrid.resetData(data);
		} else {
			// 옵션이 없는 단일제품인 경우 빈 입력칸 생성
			optionGrid.resetData([{qy: 0}]);		
		}
		columns.push({ header: "수량", name: "qy", editor: 'text', align: 'right', 
					   formatter: (row) => numberFormatter(row.value) });
		optionGrid.setColumns(columns);		
		return true;
	});
	return result;
}

/******************** 제품 선택 ********************/	
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
	document.getElementById('selectedPrdCode').value = selectedPrd.productCode;
	document.getElementById('selectedPrdName').value = selectedPrd.productName;
	
	let supplyRows = supplyGrid.findRows({productCode: selectedPrd.productCode});
	if(supplyRows.length == 0){
		let result = await loadBlankList(selectedPrd.productCode); // 단일옵션 제품인지 먼저 확인
		if(!result) loadBlankGrid(selectedPrd.productCode); // 단일제품이 아닌 경우 옵션 피벗테이블 출력
	} else {
		optionGrid.resetData(supplyRows[0].allData); // 이미 입력했던 데이터라면 불러오기
		optionGrid.setColumns(supplyRows[0].allColumn); // 컬럼 재반영
	}
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

// 엔터키 즉시 검색
document.getElementById('prdForm').addEventListener('keyup', e => {
    if(e.key == 'Enter') document.getElementById('prdSearchBtn').dispatchEvent(new Event('click'));
});

/******************** 제품별 공급계획 입력 ********************/	
// 수량 입력 가능한지 체크하여 입력 방지
optionGrid.on('click', ev => {
	let clicked = optionGrid.getRow(ev.rowKey);
	if(clicked[ev.columnName] == -1){
		failToast('입력할 수 없는 옵션입니다.');
		optionGrid.blur();
		return;
	}
});

optionGrid.on('keydown', ev => {
	let keydowned = optionGrid.getRow(ev.rowKey);
	if(ev.keyboardEvent.key == 'Enter' && keydowned[ev.columnName] == -1){
		failToast('입력할 수 없는 옵션입니다.');
		optionGrid.blur();
		return;
	}
});

// 수량 입력 후
optionGrid.on('afterChange', ev => {
	let changed = ev.changes[0]; // 이벤트 전달 객체
	let val = Number(changed.value);
	let allData = optionGrid.getData();
	let allColumn = optionGrid.getColumns();
	let row = optionGrid.getRow(changed.rowKey);
	
	// 숫자 유효성 검사
	if(isNaN(val)){ // 입력값이 숫자가 아닌 경우
		failToast('입력값은 문자가 들어갈 수 없습니다.');
		// 이전 값이 있으면 이전 값으로, 없으면 0으로 출력하고 종료
		row[ev.columnName] = changed.prevValue == null ? 0 : changed.prevValue;
		optionGrid.setRow(rowKey, row);
		return;
	} else if (val < 0){
		failToast('입력값은 음수가 될 수 없습니다.');
		row[ev.columnName] = changed.prevValue == null ? 0 : changed.prevValue;
		optionGrid.setRow(rowKey, row);
		return;
	}
	
	let findRows = supplyGrid.findRows({productCode: selectedPrd.productCode});
	// 데이터 반영
	if(val != 0 && findRows.length == 0){ // 없는 제품이면 행 추가
		let defaultMonth = getSeasonMonth();
		
		let newRow = {
			productCode: selectedPrd.productCode,
			productName: selectedPrd.productName,
			supplyDate: `${yearBox.value}-${defaultMonth}-01`, // 공급년도-시즌 선택에 따라 기본 공급일자 적용
			totalQy: val,
			allData: allData, // allData: 모든 정보를 함께 저장
			allColumn: allColumn // allColumn: 컬럼 정보 저장
		};
		supplyGrid.appendRow(newRow);
		limitDatePicker();
		selectedPrd.inserted = true; // 제품 그리드에 체크 아이콘 표시
		prdGrid.setRow(lastClicked, selectedPrd);
	} else { // 이미 있는 제품이면 입력값 업데이트
		if(val < changed.prevValue){
			findRows[0].totalQy = findRows[0].totalQy - (changed.prevValue - val);
		} else {
			findRows[0].totalQy = findRows[0].totalQy + val;			
		} // 계산 완료
		if(findRows[0].totalQy == 0){ // 총 수량이 0이 되면 그리드에서 삭제
			supplyGrid.removeRow(findRows[0].rowKey);
			selectedPrd.inserted = null; // 제품 그리드에 체크 아이콘 숨김
			prdGrid.setRow(lastClicked, selectedPrd);
		} else {
			findRows[0].allData = allData; // allData 업데이트
			findRows[0].allColumn = allColumn; // allColumn 업데이트
			supplyGrid.setRow(findRows[0].rowKey, findRows[0]);				
		}
	}
	// summary 합계 refresh
	let gridData = supplyGrid.getData();
	supplyGrid.resetData(gridData); // 동기화 문제 방지
	let sum = supplyGrid.getSummaryValues('totalQy').sum;
	$('#total').val(numberFormatter(sum));
});

// 공급년도 변경
yearBox.addEventListener('change', (e) => {
	let supplyYear = e.target.value;
	validSeason(supplyYear); // 올해를 다시 선택했을 경우 시즌 제한 재적용
	let supplyData = supplyGrid.getData();
	supplyData.forEach(data => {
		if(data.supplyDate != ''){
			let year = data.supplyDate.substr(0, 4);
			data.supplyDate = data.supplyDate.replace(year, supplyYear);
			data.supplyDate = checkDate(data.supplyDate);		
		}
	});
	supplyGrid.resetData(supplyData); // 변경한 연도로 데이터에 일괄 반영
	limitDatePicker();
});

// 시즌 변경
seasonBox.addEventListener('change', () => {
	let seasonMonth = getSeasonMonth();
	let endMonth = Number(getNextSeasonMonth()) - 1;
	let supplyData = supplyGrid.getData();
	
  if(seasonBox.value != 'SE00'){ // 상시공급이 아닌 경우 날짜 변환
    supplyGrid.enableColumn('supplyDate');
    supplyData.forEach(data => {
  		let dataMonth = data.supplyDate.substr(5,2);
  		
  		if(data.supplyDate != '' && (dataMonth < seasonMonth || dataMonth > endMonth) && seasonBox.value != 'SE00'){
  			let date = data.supplyDate.substr(-2);
  			data.supplyDate = `${yearBox.value}-${seasonMonth}-${date}`;
  			data.supplyDate = checkDate(data.supplyDate);
  		}
  	});
  	supplyGrid.resetData(supplyData); // 변경한 월로 일괄 반영
	  limitDatePicker();
  } else supplyGrid.disableColumn('supplyDate'); // 상시공급인 경우 날짜 선택 비활성화
});

// 시즌별 월 기본값 계산 함수
function getSeasonMonth(){
	let season = seasonBox.value;
	switch(season){
		case 'SE01': return '03';
		case 'SE02': return '06';
		case 'SE03': return '09';
		case 'SE04': return '12';
		default: return '01';
	}
}

// 새로운 시즌이 시작되는 월
function getNextSeasonMonth(){
	let season = seasonBox.value;
	switch(season){
		case 'SE01': return '06';
		case 'SE02': return '09';
		case 'SE03': return '12';
		case 'SE04': return '03';
		default: return '01';
	}
}

// 공급년도/시즌별 공급일자 선택 범위 제한
function limitDatePicker(){	
	let startDate = new Date(`${yearBox.value}-${getSeasonMonth()}-01`);
	let nextSeasonMonth = getNextSeasonMonth();	
	let endYear = Number(yearBox.value);
	if(seasonBox.value == 'SE00' || seasonBox.value == 'SE04'){
		endYear = endYear + 1;
	}
	let endDate = new Date(`${endYear}-${nextSeasonMonth}-01`);
	endDate.setDate(endDate.getDate() - 1); // 시즌이 유지되는 마지막 일자 계산
	
	// DatePicker options 속성에 반영
	let col = supplyGrid.getColumn('supplyDate');
	col.editor.options.selectableRanges = [[startDate, endDate]]; // 2차원 배열 속성
}

// 공급년도에서 올해를 선택했을 때 시즌 선택 제한
function validSeason(year){
	if(year != thisYear && seasonBox.childElementCount == 5) return; // 변경할 내용이 없으면 종료
	
	seasonBox.innerHTML = ''; // 내용 비우고 미리 저장해둔 option 노드배열로 재구성
	if(year == thisYear){
		if(thisMonth >= 1 && thisMonth <= 3){ // 2~4월인 경우 여름시즌부터 등록 가능
			seasonBox.append(seasonOpts[2], seasonOpts[3], seasonOpts[4]);
		} else if(thisMonth >= 4 && thisMonth <= 6){ // 5~7월인 경우 가을시즌부터 등록 가능
			seasonBox.append(seasonOpts[3], seasonOpts[4]);
		} else seasonBox.append(seasonOpts[4]); // 8~10월인 경우 겨울시즌만 등록 가능
	} else { // 입력년도가 올해가 아니라면 전체 옵션 표시	
		seasonBox.append(seasonOpts[0], seasonOpts[1], seasonOpts[2], seasonOpts[3], seasonOpts[4]);
	}
	seasonBox.dispatchEvent(new Event('change')); // DOM객체에 변경 이벤트 발생
}

// 2000-02-31 같은 값이 되지 않기 위해 검증
function checkDate(dateStr){
	let dateVal = dateStr.substr(-2); // 일자만 추출
	if(dateVal >= 27 && dateVal <= 31){
		let year = dateStr.substr(0, 4);
		let month = dateStr.substr(5, 2);
		
		let compDate = new Date(`${year}-${month}-01`);
		compDate.setMonth(compDate.getMonth() + 1);
		compDate.setDate(compDate.getDate() - 1);
		
		if(compDate.getDate() < dateVal){ // 해당 월의 실제 마지막 일자보다 큰 경우 교체
			dateStr = dateStr.replace(dateVal, compDate.getDate());					
		}
	}
	return dateStr;
}

/******************** 공급계획 일괄 등록 ********************/	
function insertPlan(){
	let planArr = supplyGrid.getData();
	let detailArr = []; // 데이터 입력용 디테일
	console.log(planArr);
	
	// 각 행의 allData 배열에 담아둔 모든 입력정보로 디테일 입력
	planArr.forEach(plan => {
		// 옵션유형 판단
		let optType = null;
		// 'noOpt': 단일제품인 경우. qy(수량)만 존재
		// 'sizeOpt' or 'colorOpt': 색상/사이즈 중 하나는 있는 경우. qy와 productColor 또는 productSize, sizeName 존재 
		// 'multiOpt': 색상/사이즈 모두 있는 경우 qy 없음. {PRODUCT_COLOR, SI~~, SI~~... : 수량 다중값}
		if(typeof(plan.allData[0].qy) == 'undefined' || plan.allData[0].qy == null) optType = 'multiOpt';
		else if(plan.allData[0].productSize != null) optType = 'sizeOpt'
		else if(plan.allData[0].productColor != null) optType = 'colorOpt';
		else optType = 'noOpt'; // 색상, 사이즈 없고 qy만 있으면 단일제품
		
		// 수량이 있는 데이터만 디테일데이터에 입력 (공백, 0 체크)
		let sizeCodes = null;
		plan.allData.forEach((data, idx) => {
			if(optType == 'multiOpt'){
				if(idx == 0){ // SI로 시작하는 사이즈 코드만 분리
					sizeCodes = Object.keys(data).filter(key => key.substr(0,2) == 'SI');
				}
				sizeCodes.forEach(size => {
					if(data[size] > 0 && data[size] != '0' && data[size] != ''){
						detailArr.push({
							productCode: plan.productCode,
							productColor: data.PRODUCT_COLOR,
							productSize: size,
							supplyQy: data[size],
							supplyDate: plan.supplyDate
						});	
					}
				});	
			} else { // 다중 옵션이 아닌 경우 기본값
				if(data.qy == '0' || data.qy == '') return;
				let dtlObj = {
					productCode: plan.productCode,
					supplyQy: data.qy,
					supplyDate: seasonBox.value == 'SE00' ? null : plan.supplyDate // 상시공급인 경우 날짜를 넣지 않음.
				};
				if(optType == 'sizeOpt'){ // 사이즈만 있는 데이터
					dtlObj.productSize = data.productSize;
				} else if(optType == 'colorOpt'){ // 색상만 있는 데이터
					dtlObj.productColor = data.productColor;
				}
				detailArr.push(dtlObj);
			}
		});
	});
	console.log('디테일:: ', detailArr);
	
	// 헤더 정보 #{supplyYear}, #{season}, #{chargerCode}, #{chargerName}, #{remark}
	let headerObj = {
		supplyYear: yearBox.value,
		season: seasonBox.value,
		chargerCode: session_user_code,
		chargerName: session_user_name,
		remark: document.getElementById('remark').value
	};
	console.log('헤더:: ', headerObj);
	
	loading();
	fetch('/supply/plan', {
		method: 'POST',
		headers: {...headers, 'Content-Type': 'application/json'},
		body: JSON.stringify({headerObj, detailArr})
	})
	.then(response => response.json())
	.then(result => {
		console.log(result);
		if(result == true){
			successToast('작업이 완료되었습니다.');
			supplyGrid.resetData([]); // 등록 이후 처리 어떻게 할지? 
		} else failToast('작업을 실패했습니다.');
	});
}
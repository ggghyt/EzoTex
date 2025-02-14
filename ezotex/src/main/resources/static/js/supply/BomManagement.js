// 카테고리 소분류 출력
let lclasBox = document.getElementById('lclas');
let sclasBox = document.getElementById('sclas');
let nullOption = sclasBox.firstElementChild;

lclasBox.addEventListener('change', () => {
	let lclas = lclasBox.value;
	
	// 서버에서 소분류 데이터 불러오기
	fetch(`/product/category/${lclas}`)
	.then(response => response.json())
	.then(data => {
		console.log(data); // 해당 대분류에 속한 소분류 배열 출력
		sclasBox.innerHTML = '';
		sclasBox.append(nullOption);
		
		for(let value of data){			
			let sclas = document.createElement('option');
			sclas.value = value;
			sclas.innerText = value;
			sclasBox.append(sclas);
		}
	});
});


// tui grid 출력
		
// 제품 목록 불러오기
const prdData = {
	api: {
		// RestController에서 json 데이터 읽어오기
		readData: { url: '/supply/bomProductList', method: 'GET', initParams: { productType: 'PT02' } }
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

let lastClicked = null; // 페이지 이동 시에도 이전 선택 기억하기 위함.

// 선택된 행 강조 & 정보 가져오기
prdGrid.on('focusChange', ev => {
	// 배경색 클래스 적용
	prdGrid.removeRowClassName(lastClicked, 'bg-blue'); // 이전 선택 행 배경색 삭제
	prdGrid.addRowClassName(ev.rowKey, 'bg-blue'); // 선택된 행 배경색 추가
	lastClicked = ev.rowKey; // 선택된 행 기억
	
	// 선택한 정보 가져오기
	let selected = prdGrid.getRow(ev.rowKey);
	console.log(selected);
	document.getElementById('selectedPrdCode').value = selected.PRODUCT_CODE;
	document.getElementById('selectedPrdName').value = selected.PRODUCT_NAME;
	//getBomList(selected); -- 옵션까지 선택 필요
});

// 제품 목록 검색 적용
document.getElementById('prdSearchBtn').addEventListener('click', () => {
	let dto = {
			productType: 'PT02',
			productCode: document.getElementById('productCode').value,
			productName: document.getElementById('productName').value,
			lclas: document.getElementById('lclas').value,
			sclas: document.getElementById('sclas').value
	};
	
	prdGrid.setRequestParams(dto); // 조회 조건 전달
	prdGrid.reloadData(); // 그리드 재출력 (readData)
});


// 선택한 제품의 BOM 자재 불러오기
const getBomList = function(selected){
	const mtrData = {
		api: {
			// RestController에서 json 데이터 읽어오기
			readData: { 
				url: '/supply/bomMaterialList', 
				method: 'GET', 
				initParams: { 
					productCode: selected.PRODUCT_CODE
					//productSize: selected.
					//productColor: selected.
				 } }
		},
		contentType : 'application/json' // 데이터 전달 시 인코딩 필요
	};
  	
	const mtrGrid = new Grid({
	    el: document.getElementById('mtrGrid'), // 해당 위치에 그리드 출력
	    data: mtrData,
	    columns: [
	        { header: '자재코드', name: 'MTRIL_CODE', width: 100, sortable: true },
	        { header: '자재명', name: 'MTRIL_NAME', whiteSpace: 'pre-line', sortable: true }
	    ],
	    rowHeaders: ['checkbox'], // 체크박스 추가
	    pageOptions: {
	        useClient: true, // 페이징을 위해 필요
	        perPage: 10
    	},
    	scrollX: false, // 가로 스크롤
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
	
	//const selectedMtrGrid = 
};
// 타임존 설정옵션 (aws 배포 시 UTC 적용 문제 해결)
const dateOptions = { 
    timeZone: 'Asia/Seoul', 
    year: 'numeric', 
    month: '2-digit', 
    day: '2-digit' 
};

// 날짜 포맷 함수 (0000-00-00)
const dateFormatter = function(value){
  let date = value == null ? new Date() : new Date(value); // 값이 없으면 오늘 날짜 반환
  date = date.toLocaleDateString('ko-KR', dateOptions); // 한국시간으로 변경 (2025. 03. 08. 형식)
  
  return date.replaceAll('. ', '-').slice(0, -1);
}

// 날짜 포맷 함수 (null 그대로 리턴하는 버전)
const dateFormatterNull = function(value){
  if(value == null) return value;
  else return dateFormatter(value);
}

// 천 단위 콤마 숫자 포맷 함수 (9,999,999)
function numberFormatter(value){ // 그리드 내부 포맷으로 사용 시 row.value
	value = value == null ? 0 : value;
  return Number(value).toLocaleString();
};

//사이즈 입력 -> 공통코드 리턴 함수
function getSizeCommonCode(size) {
    const sizeMap = {
        "FREE": "SI01", "XS": "SI02", "S": "SI03", "M": "SI04", "L": "SI05", "XL": "SI06", "XXL": "SI07",
        "25": "SI25", "26": "SI26", "27": "SI27", "28": "SI28", "29": "SI29", "30": "SI30",
        "31": "SI31", "32": "SI32", "33": "SI33", "34": "SI34", "35": "SI35", "36": "SI36",
        "37": "SI37", "38": "SI38", "44": "SI44", "55": "SI55", "66": "SI66", "77": "SI77",
        "88": "SI88", "90": "SI90", "95": "SI95", "100": "SI100", "105": "SI105", "110": "SI110"
    };

    return sizeMap[size] || null; // 입력한 사이즈가 없으면 null
}

// 검색조건 등 최소값-최대값 제한 적용
function updateRange(startEleId, endEleId) {
    let startEle = document.getElementById(startEleId);
    let endEle = document.getElementById(endEleId);
    
    // 시작값/끝값이 입력되었을 때 동적으로 최소값 최대값 상호적용
    // 날짜일 때
    if(startEle.type == 'date' && endEle.type == 'date'){
        startEle.addEventListener('change', e => {
            if(e.target.value != '') endEle.setAttribute("min", e.target.value);
        });
        endEle.addEventListener('change', e => {
            if(e.target.value != '') startEle.setAttribute("max", e.target.value);
        });
    } else { // 숫자일 때
        let validNumberListener = function(e) {
            let startVal = startEle.value !== '' ? Number(startEle.value) : '';
            let endVal = endEle.value !== '' ? Number(endEle.value) : '';
            let val = e.target == startEle ? startVal : endVal;
            
            if(isNaN(val)){ // 숫자가 아닌 경우
              failToast('입력값은 문자가 들어갈 수 없습니다.');
              val = '';
            } else if(val < 0){
              val = val * -1; // 입력값이 음수면 양수로 변환
            }
            // 최소값/최대값 검사
            if(val !== '' && startVal !== '' && e.target == endEle && startVal > val) val = startVal;
            else if(val !== '' && endVal !== '' && e.target == startEle && val > endVal) val = endVal;
            e.target.value = val;
        }
        startEle.addEventListener('change', validNumberListener);
        endEle.addEventListener('change', validNumberListener);      
    }
}










// <input type="number"> 커스텀
/* 사용법 : <div class="quantity">
						 <input type="number" min="1" max="10" step="1" value="1">
					 </div>
*/ 
//jQuery(document).ready(makeQuantityTag);

function makeQuantityTag() {
    // .quantity 엘리먼트 뒤에 버튼 추가
    jQuery('.quantity').each(function() {
			// 변수 한꺼번에 선언
      var spinner = jQuery(this),
        input = spinner.find('input[type="number"]'),
        min = input.attr('min'),
        max = input.attr('max'),
		step = input.attr('step');
	
	  // 속성이 지정되지 않았으면 기본값 적용	
	  step = typeof(step) == 'undefined' ? 1 : Number(step);
			
	  // 새로운 버튼 추가
      jQuery('<div class="quantity-nav"><div class="quantity-button quantity-up"><span class="quantity-up-text">+</span></div><div class="quantity-button quantity-down"><span class="quantity-down-text">-</span></div></div>')
			.insertAfter(input);

      // 버튼 이벤트 핸들러 추가
      var btnUp = spinner.find('.quantity-up'),
          btnDown = spinner.find('.quantity-down');

      btnUp.click(function() {
		var oldValue;
		if(input.val() == ''){
			if(typeof(min) != 'undefined') oldValue = Number(min);
			else oldValue = 0;
		} else oldValue = parseFloat(input.val());

		if (typeof(oldValue) == 'undefined') oldValue = 0;
        if (typeof(max) == 'undefined' || oldValue < max) {
          input.val(oldValue + step);
		  input[0].dispatchEvent(new Event('change')); // 원시 DOM객체에 이벤트 발생
        }
      });

      btnDown.click(function() {
        var oldValue = parseFloat(input.val());
        if (typeof(min) == 'undefined' || oldValue > min) {
          input.val(oldValue - step);
		  input[0].dispatchEvent(new Event('change')); // 원시 DOM객체에 이벤트 발생
        }
      });
    });
  }
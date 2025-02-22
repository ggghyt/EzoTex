// 날짜 포맷 함수 (0000-00-00)
const dateFormatter = function(value){
	let date = value == null ? new Date() : new Date(value); // 값이 없으면 오늘 날짜 반환
    
    let year = date.getFullYear();
    let month = ('0' + (date.getMonth() + 1)).slice(-2);
    let day = ('0' + (date.getDate())).slice(-2);

    return year + '-' + month + '-' + day;
}

// 천 단위 콤마 숫자 포맷 함수 (9,999,999)
function numberFormatter(value){ // 그리드 내부 포맷으로 사용 시 row.value
	return Number(value).toLocaleString() 
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













// <input type="number"> 커스텀
/* 사용법 : <div class="quantity">
						 <input type="number" min="1" max="10" step="1" value="1">
					 </div>
*/ 
jQuery(document).ready(function() {
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
			
			//
			input.change(function() {
				
			});
    });
  });
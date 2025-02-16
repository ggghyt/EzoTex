// 날짜 포맷 함수 (0000-00-00)
const dateFormmater = function(value){
	let date = value == null ? new Date() : new Date(value); // 값이 없으면 오늘 날짜 반환
    
    let year = date.getFullYear();
    let month = ('0' + (date.getMonth() + 1)).slice(-2);
    let day = ('0' + (date.getDate())).slice(-2);

    return year + '-' + month + '-' + day;
}
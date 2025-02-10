addEventListener("DOMContentLoaded", () => {    
    var isRegist = false;
    var isModify = false;
    var isDelete = false;

    //등록 - 확인 버튼
    document.querySelector(".regist .checkBtn").addEventListener("click", () => {
		var loadingSpinner = document.querySelector(".loading-wrap");
	    var loadingMessage = document.getElementById("loadingMessage");
	
	    // 로딩 시작
	    loadingSpinner.style.display = "flex";
	    loadingMessage.style.color = "white";
	
	    // 1초 뒤에 로딩 완료
	    setTimeout(function () {
	    loadingSpinner.style.display = "none";
	    
	    isRegist = true;
        console.log(isRegist);
        
        successToast('작업 성공');
	    }, 600);
    });
    //등록 - 취소 버튼
    document.querySelector(".regist .denyBtn").addEventListener("click", () => {
        isRegist = false;
        console.log(isRegist);
    });

    //수정 - 확인 버튼
    document.querySelector(".modify .checkBtn").addEventListener("click", () => {
        isModify = true;
        console.log(isModify);
    });
    //수정 - 취소 버튼
    document.querySelector(".modify .denyBtn").addEventListener("click", () => {
        isModify = false;
        console.log(isModify);
    });

    //삭제 - 확인 버튼
    document.querySelector(".delete .checkBtn").addEventListener("click", () => {
        isDelete = true;
        console.log(isDelete);
    });
    //삭제 - 취소 버튼
    document.querySelector(".delete .denyBtn").addEventListener("click", () => {
        isDelete = false;
        console.log(isDelete);
    });

});




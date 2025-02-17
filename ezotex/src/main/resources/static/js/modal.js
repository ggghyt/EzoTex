function createModal(options){
	/* options = {
			id: '모달 출력할 div',
			type: 'regist' or 'modify' of 'delete', // (이미지/버튼 교체 효과)
			//// 필수입력값 끝, 아래는 선택사항
			title: '제목 null 시 기본 멘트 적용',
			content: '짧은 메세지 null 시 기본 멘트 적용',
			confirm: function(){} // (확인 버튼 클릭 시 실행할 함수),
			denyMsg: '취소 클릭 시 출력할 토스트 메세지'
		}
	*/
	let modalDiv = document.getElementById(options.id);
	
	let typeNm = null;
	let btnColor = null;
	switch(options.type){
		case 'regist' : typeNm = '등록'; btnColor = 'primary'; break;
		case 'modify' : typeNm = '수정'; btnColor = 'warning'; break;
		case 'delete' : typeNm = '삭제'; btnColor = 'danger'; break;
	}
	
	modalDiv.innerHTML = `
		<div class="modal fade" id="simpleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	      <div class="modal-dialog">
	        <div class="modal-content">
	          <div class="modal-header" style="height: 20px;">
	            <h5 class="modal-title" id="exampleModalLabel" style="font-size: 15px;">${options.title != null ? options.title : typeNm + ' 확인'}</h5>
	            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	          </div>
	          <div class="modal-body" style="text-align: center; padding: 44px; padding-bottom:10px;">
	            <div>
	              <img src="/images/modal/${options.type}.png" alt="${typeNm}확인이미지" style="width: 84px; height:84px; ">
	            </div>
	            <div>
	              <p style="margin-top: 12px;
	              font-size: 21px;
	              font-weight: bold;">알림</p>
	              <p>${options.content != null ? options.content : '내용을 ' + typeNm + '하시겠습니까?'}</p>
	            </div>
	          </div>
	          <div class="modal-footer" style="display: flex; justify-content: center; border-top: none; padding-bottom:45px;">
	            <button type="button" class="btn btn-outline-${btnColor} confirmBtn" data-bs-dismiss="modal">${typeNm}</button>
	            <button type="button" class="btn btn-secondary denyBtn" data-bs-dismiss="modal">취소</button>
	          </div>
	        </div>
	      </div>
	    </div>
	`;
	
	// 등록 - 확인 버튼
    document.querySelector(".confirmBtn").addEventListener("click", () => {
		var loadingSpinner = document.querySelector(".loading-wrap");
	    var loadingMessage = document.getElementById("loadingMessage");
	
	    // 로딩 시작
	    loadingSpinner.style.display = "flex";
	    loadingMessage.style.color = "white";
	
	    // 1초 뒤에 로딩 완료
	    setTimeout(function () {
	    loadingSpinner.style.display = "none";
	    
        if(options.confirm != null) options.confirm(); // 실행할 함수를 받았다면 실행
        
        //successToast(options.confirmMsg != null ? options.confirmMsg : '작업 성공'); // 입력한 메세지 없으면 기본 출력
	    }, 600);
    });
	
	// 등록 - 취소 버튼
	document.querySelector(".denyBtn").addEventListener("click", () => {
        if(options.denyToast != null) failToast(options.denyMsg); // 입력한 메세지 출력
    });
};

// 원본
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




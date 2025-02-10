addEventListener("DOMContentLoaded", () => {    
    var isRegist = false;

    //등록 - 확인 버튼
    document.querySelector(".regist .checkBtn").addEventListener("click", () => {
        isRegist = true;
        console.log(isRegist);
    });
    //등록 - 취소 버튼
    document.querySelector(".regist .denyBtn").addEventListener("click", () => {
        isRegist = false;
        console.log(isRegist);
    });

    //수정 - 확인 버튼
    document.querySelector(".modify .checkBtn").addEventListener("click", () => {
        isRegist = true;
        console.log(isRegist);
    });
    //수정 - 취소 버튼
    document.querySelector(".modify .denyBtn").addEventListener("click", () => {
        isRegist = false;
        console.log(isRegist);
    });

    //삭제 - 확인 버튼
    document.querySelector(".delete .checkBtn").addEventListener("click", () => {
        isRegist = true;
        console.log(isRegist);
    });
    //삭제 - 취소 버튼
    document.querySelector(".delete .denyBtn").addEventListener("click", () => {
        isRegist = false;
        console.log(isRegist);
    });

});




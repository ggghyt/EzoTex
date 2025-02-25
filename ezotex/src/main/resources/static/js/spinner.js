function loading(loadingTime) {
    //console.log('작동됨');
    var loadingSpinner = document.querySelector(".loading-wrap");
    var loadingMessage = document.getElementById("loadingMessage");

    // 로딩 시작
    loadingSpinner.style.display = "flex";
    loadingMessage.style.color = "white";

    // 1초 뒤에 로딩 완료
    setTimeout(function () {
    loadingSpinner.style.display = "none";
    }, loadingTime == null ? 500 : loadingTime);
}
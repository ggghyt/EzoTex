const ajaxUrl = `/api`;

// /api라는 부분은 배포 과정에서 사라지는 문자열임 그래서 한곳에서 모듈로 사용해야됨    배포과정에선 const ajaxUrl = ``; 이런식으로 될 예정
export {
    ajaxUrl,
}
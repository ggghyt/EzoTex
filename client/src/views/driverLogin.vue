<template>
    <p>로그인</p>
    <button class="btn btn-primary" @click="testFunc">테스트</button>
    <form class="pt-3" action="$http://localhost:80/loginProc" method="post">
                <div class="form-group">
                  <input type="text" class="form-control form-control-lg" name="id" placeholder="아이디" id="id">
                </div>
                <div class="form-group">
                  <input type="password" class="form-control form-control-lg" name="password" placeholder="비밀번호">
                </div>
                <div class="mt-3">
                  <button class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn">로그인</button>
                </div>
                <div class="my-2 d-flex justify-content-between align-items-center">
                    <label class="form-check-label text-muted">
                      <input type="checkbox" class="form-check-input" name="remember-me" id="remember-me">
                      로그인 정보 저장
                    </label>
                </div>
                <div class="my-2 d-flex justify-content-between align-items-center">
                  <a href="/login/password_reset" class="auth-link text-black">비밀번호를 잊어버렸나요?</a>
                </div>
                <div class="text-center mt-4 fw-light">
                  아직 등록을 안했나요? <a href="/login/register_main" class="text-primary">등록하기</a>
                </div>
              </form>
</template>

<script setup>
    //http://localhost:8081/api/driver/list?perPage=10&page=1 --요청 url
    import axios from 'axios';
    import { ajaxUrl } from '@/utils/commons.js';

    function getCsrfToken() {
        const csrfCookie = document.cookie
            .split('; ')
            .find(row => row.startsWith('XSRF-TOKEN='));
            
        if (!csrfCookie) {
        console.error(" CSRF 토큰이 없습니다. 쿠키를 확인하세요.");
        return null;
    }
        //console.log('csrf쿠키:', csrfCookie.split('=')[1]);
        return csrfCookie ? csrfCookie.split('=')[1] : null;
    }

    /*
    axios.get(`${ajaxUrl}/session-info`, { withCredentials: true }) // 세션을 유지하기 위해 `withCredentials` 추가
    .then(response => {
        console.log("세션 정보:", response.data);
    })
    .catch(error => {
        console.error("세션 정보 가져오기 실패:", error.response.data);
    });


    headers: {
	Authorization: `Token ${token}`
}
    */

    const testFunc = () => {
        //const params = { id: 'delivery', password: 'delivery' };

        axios({
        method: 'post',
        url: `${ajaxUrl}/loginProc`,
        data: `id=delivery&password=delivery`,
        headers: {
                'X-XSRF-TOKEN': getCsrfToken() // CSRF 토큰을 헤더에 추가
                //Authorization: `Token ${getCsrfToken()}`
            },
        })
        .then(response => {
            //console.log("로그인 성공:", response);
            if (response.data.role === "ROLE_DRIVER") {
                alert("운전자 로그인 성공!");
                // Vuex/Pinia 상태 관리에 저장 가능
            } else {
                alert("로그인 실패")
                //window.location.href = "/"; // 일반 사용자라면 리디렉션
            }
        })
        .catch(error => {
            console.error("로그인 실패:", error.response.data);
        });

        /*
        axios( `${ajaxUrl}/loginProc`, {
            id: 'delivery',
            password: 'delivery'
        }, {
            headers: {
                'X-XSRF-TOKEN': getCsrfToken() // CSRF 토큰을 헤더에 추가
            },
            withCredentials: true // 쿠키를 포함하도록 설정
        })
        .then(response => {
            //console.log("로그인 성공:", response);
            if (response.data.role === "ROLE_DRIVER") {
                alert("운전자 로그인 성공!");
                // Vuex/Pinia 상태 관리에 저장 가능
            } else {
                alert("로그인 실패")
                //window.location.href = "/"; // 일반 사용자라면 리디렉션
            }
        })
        .catch(error => {
            console.error("로그인 실패:", error.response.data);
        });
        */
    }
</script>
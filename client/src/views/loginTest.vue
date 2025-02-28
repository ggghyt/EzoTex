<script setup>
import { ref } from 'vue';
import axios from 'axios';

const username = ref('');
const password = ref('');
const token = ref('');

const login = async () => {
  try {
    const response = await axios.post('http://localhost:8080/api/auth/login', {
      username: username.value,
      password: password.value
    });

    token.value = response.data.token;
    localStorage.setItem('jwt', token.value); // JWT 저장

    console.log('로그인 성공! 토큰:', token.value);
  } catch (error) {
    console.error('로그인 실패:', error);
  }
};
</script>

<template>
  <div>
    <input v-model="username" placeholder="아이디" />
    <input v-model="password" type="password" placeholder="비밀번호" />
    <button @click="login">로그인</button>
    <p>토큰: {{ token }}</p>
  </div>
</template>

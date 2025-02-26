<template>
    <p>로그인</p>
    <button @click="testFunc">테스트</button>
</template>

<script setup>
    import axios from 'axios';
    import { ajaxUrl } from '@/utils/commons.js';

    const testFunc = async () => {
        let result = await axios.get(`${ajaxUrl}/delivery/mtrilDelivery`)
        .catch(err=>console.log(err));

        console.log(result);
    }
</script>
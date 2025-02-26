<template>
    <p>로그인</p>
    <button class="btn btn-primary" @click="testFunc">테스트</button>
</template>

<script setup>
    import axios from 'axios';
    //import { ajaxUrl } from '@/utils/commons.js';

    const testFunc = async () => {
        let result = await axios.get(`/api/delivery/mtrilDelivery`)
        .catch(err=>console.log(err));

        console.log(result);
    }
</script>
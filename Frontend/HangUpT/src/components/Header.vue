=<template>
    <nav class="navbar">
        <img src="../assets/images/HUTLogo.png" @click="goMain">
        <div class="menu_container">
            <router-link to="/map">지도</router-link>
            <router-link :to="{name:'events',query:{selectPrice:'free'}}">무료</router-link>
            <router-link :to="{name: 'events',query:{selectPrice:'paid'}}">유료</router-link>
            <router-link :to="{name:'events',query:{selectJoin:'online'}}">온라인</router-link>
            <router-link :to="{name:'events',query:{selectJoin:'offline'}}">오프라인</router-link>
            <router-link to="/boards">게시판</router-link>
        </div>

        <div class="login_container">
            <router-link v-if="!store.isLoggedIn" to="/login" class="btn">로그인</router-link>
            <router-link v-if="!store.isLoggedIn" to="/register" class="btn">회원가입</router-link>
            <router-link v-if="store.isLoggedIn" to="/mypage" class="btn">마이페이지</router-link>
            <button v-if="store.isLoggedIn" @click="logout" class="btn">로그아웃</button>
        </div>

    </nav>
</template>

<script setup>
    import { useRouter } from 'vue-router';
    import { inject } from 'vue';
    import { store } from '../data/store';

   // const loginState = inject('loginState');

    const logout = () => {
        store.isLoggedIn = false;
        router.push('/login'); 
    };


    const router =useRouter();

    const goMain=()=>{
        router.replace('/');

}

</script>

<style lang="scss" scoped>
    .navbar {
        padding:15px;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        background-color: #000000;

        div {
            display: flex;
            // justify-content: center;
            align-items: center;
        }
        img {
            cursor: pointer;
            width: 15%;
            height: auto;
        }
    

        button {
            border: none;
            background-color: transparent;
            color: #767474;
        
        }

        button:hover {
            color: #4b4b4b;
            cursor: pointer;
        }

        a{
            text-decoration: none;
            margin-right: 35px;
            color: white;
            font-size: 1.3rem;
        }

        a:hover {
            color: #7abdfc;
        }
        .btn {
            border: none;
            background-color: transparent;
            color: #767474;
            text-decoration: none;
            margin-right: 10px;
            font-size: 1rem;
            cursor: pointer;
        }

        .btn:last-child {
            margin-right: 0;
        }

        .btn:hover {
            color: #4b4b4b;
        }
    }

</style>

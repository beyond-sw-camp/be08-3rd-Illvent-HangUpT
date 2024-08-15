<template>
    <nav class="navbar">
        <img src="../assets/images/logo.png">
        <div class="menu_container">
            <router-link to="/map">지도</router-link>
            <router-link to="/events">무료</router-link>
            <router-link to="/events">유료</router-link>
            <router-link to="/events">온라인</router-link>
            <router-link to="/events">오프라인</router-link>
            <router-link to="/boards">게시판</router-link>
        </div>

        <div class="login_container">
            <button>로그인</button>
            <button>로그아웃</button>
            <button>마이페이지</button>
        </div>

    </nav>
</template>

<script setup>
    
</script>

<style lang="scss" scoped>
 .navbar {
        padding:15px;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        background-color: #BFDBF5;

        div {
            display: flex;
            // justify-content: center;
            align-items: center;
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
    }
</style>

<script>
export default {
  name: 'HeaderComponent'
}
</script>
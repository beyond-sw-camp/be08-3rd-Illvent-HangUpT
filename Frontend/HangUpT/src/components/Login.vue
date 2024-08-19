<template>
  <div class="login-container">
    <div class="login-box">
      <div class="logo-container">
        <img src="../assets/images/HUTLogo.png" alt="로고" class="logo">
      </div>
      <form @submit.prevent="login">
        <label for="user-id">이메일(아이디):</label>
        <input type="text" placeholder="Email(ID)" class="input-field" id="user-id" v-model="id">

        <label for="user-password">비밀번호:</label>
        <input type="password" placeholder="Password" class="input-field" id="user-password" v-model="password">
        
        <input type="submit" value="로그인" class="btn">
      </form>
      <div class="join">
        <router-link to="/register">회원가입</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { inject } from 'vue';
export default {
  data() {
    return {
      id: '',
      password: '',
      dummyData: [
        { id: 'kuk329', password: '1234' },
        { id: 'tyeong1102', password: '3456' },
        { id: 'juhi0916', password: '7891' },
        { id: 'jeongjinjoo', password: 'abcd' },
        { id: 'byhyen', password: 'efgh' }
      ]
    };
  },
  setup() {
    const loginState = inject('loginState');
    return {
      loginState
    };
  },
  methods: {
    login() {
      const user = this.dummyData.find(user => user.id === this.id && user.password === this.password);
      if (user) {
        alert('로그인에 성공하였습니다.');
        this.loginState.isLoggedIn = true;
        this.$router.push('/'); // 로그인 성공 시 메인으로 이동
      } else {
        alert('로그인에 실패하였습니다. 다시 입력해 주세요');
      }
    }
  }
}
</script>

<style scoped>
/* 기존 스타일 유지 */
.login-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #fbfbfb;
  top: 70px;
}

.logo-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.logo {
  width: 90%; /* 원하는 크기로 조정 가능 */
}

.login-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #f5f5f5;
  padding: 50px;
  border-radius: 15px;
  box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.2);
  width: 400px;
  transition: transform 0.3s ease;
  margin-top: 1px;
}

.login-box:hover {
  transform: translateY(-10px);
  box-shadow: 0px 15px 35px rgba(0, 0, 0, 0.3);
}

h2 {
  color: #007bb5;
  text-align: center;
  margin-bottom: 30px;
  font-size: 24px;
}

label {
  align-self: flex-start;
  margin-bottom: 10px;
  color: #007bb5;
  font-weight: bold;
}

.input-field {
  width: 100%;
  padding: 12px;
  margin-bottom: 20px;
  border: 1px solid #007bb5;
  border-radius: 5px;
  box-shadow: inset 0px 2px 5px rgba(0, 0, 0, 0.1);
}

.checkbox-container {
  display: flex;
  align-items: center;
  margin-bottom: 30px;
  width: 100%;
}

.btn {
  width: 100%;
  padding: 12px;
  background-color: #000000;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.2);
  transition: background 0.3s ease, box-shadow 0.3s ease;
}

.btn:hover {
  background-color: #6bc3ec;
  box-shadow: 0px 10px 20px rgba(0, 0, 0, 0.3);
}

.join {
  margin-top: 20px;
}

.join a {
  color: #007bb5;
  text-decoration: none;
  font-weight: bold;
  transition: color 0.3s ease;
}

.join a:hover {
  color: #005f8b;
  text-decoration: underline;
}
</style>

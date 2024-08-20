<template>
  <div class="container">
    <div class="sidebar">
      <h2>마이페이지</h2>
      <ul>
        <li :class="{ active: activeTab === '관심 행사' }" @click="setActiveTab('관심 행사')">관심 행사</li>
        <li :class="{ active: activeTab === '내 게시물' }" @click="setActiveTab('내 게시물')">내 게시물</li>
        <li :class="{ active: activeTab === '내 정보 수정' }" @click="setActiveTab('내 정보 수정')">내 정보 수정</li>
      </ul>
    </div>
    <div class="content">
      <h2>{{ activeTab }}</h2>

      <!-- 관심 행사 -->
      <div v-if="activeTab === '관심 행사'" class="event-list">
        <div v-for="(event, index) in events" :key="index" class="event-card">
          <img :src="event.image" alt="Event Image" />
          <h3>{{ event.title }}</h3>
        </div>
      </div>

      <!-- 내 게시물 -->
      <div v-if="activeTab === '내 게시물'">
        <table>
          <thead>
            <tr>
              <th scope="col" style="width: 5%;">ID</th>
              <th scope="col" style="width: 12%;">지역</th>
              <th scope="col" style="width: 50%;">제목</th>
              <th scope="col" style="width: 8%;">좋아요</th>
              <th scope="col" style="width: 7%;">조회수</th>
              <th scope="col" style="width: 12%;">날짜</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(post, index) in posts" :key="post.no">
              <td>{{ index + 1 }}</td>
              <td>{{ post.region }}</td>
              <td class="text-start">
                <router-link :to="`/boards/${post.no}`" class="text-decoration-none" @click.prevent="incrementViews(post.id)">
                  {{ post.title }}
                </router-link>
              </td>
              <td>{{ post.likes }}</td>
              <td>{{ post.views }}</td>
              <td>{{ post.createDate.split('T')[0] }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      
      <!-- 내 정보 수정 -->
      <div v-if="activeTab === '내 정보 수정'">
        <form @submit.prevent="submitForm">
          <div class="form-group">
            <label for="name">이름</label>
            <input type="text" id="name" v-model="userInfo.name" />
          </div>
          <div class="form-group">
            <label for="email">이메일</label>
            <input type="email" id="email" v-model="userInfo.email" />
          </div>
          <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" v-model="userInfo.password" />
          </div>
          <div class="form-group">
            <label for="location">지역</label>
            <input type="text" id="location" v-model="userInfo.location" />
          </div>
          <button type="submit">수정 완료</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import event1 from '../assets/event1.jpg';
import event2 from '../assets/event2.jpg';
import axios from 'axios';

export default {
  name: "MyPage",
  data() {
    // localStorage에서 userInfo 가져오기
    const storedUserInfo = JSON.parse(localStorage.getItem("userInfo"));

    return {
      activeTab: '관심 행사', 
      userInfo: storedUserInfo || {  // localStorage에 정보가 없을 경우 기본값 설정
        name: '',
        email: '',
        password: '',
        location: '',
      },
      events: [
        {
          image: event1, 
          title: '핑구 화남 행사'
        },
        {
          image: event2, 
          title: '핑구 삐짐 행사'
        }
      ],
      posts: []  // 서버에서 받아올 게시물 목록
    };
  },
  methods: {
    setActiveTab(tabName) {
      this.activeTab = tabName;
    },

    submitForm() {
      console.log("사용자 정보:", this.userInfo);
      alert("정보가 성공적으로 수정되었습니다!");

      // 수정된 정보를 localStorage에 다시 저장
      localStorage.setItem("userInfo", JSON.stringify(this.userInfo));
    },

    async loadUserPosts() {
      try {
        // 서버에 해당 회원이 작성한 게시물을 요청
        const response = await axios.get(`http://localhost:8080/v1/api/member/list/post/${this.userInfo.no}`);
        this.posts = response.data;
      } catch (error) {
        console.error("게시물 로딩 중 오류 발생:", error);
      }
    }
  },

  mounted() {
    this.loadUserPosts();  // 컴포넌트가 로드될 때 사용자 게시물 로드
  },
};
</script>

<style scoped>
.container {
  display: flex;
  padding-top: 50px;
  background-color: #ffffff; /* 전체 배경을 흰색으로 유지 */
  min-height: 100vh; /* 페이지 전체 높이 */
}

.sidebar {
  width: 20%;
  padding: 20px;
  background-color: transparent; /* 배경을 투명하게 설정 */
  /* 불필요한 네모 박스 및 그림자 제거 */
}

.sidebar h2 {
  font-size: 1.5rem;
  margin-bottom: 30px;
  font-family: 'Pretendard', sans-serif;
  font-weight: bold;
  color: #333;
}

.sidebar ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar li {
  margin-bottom: 20px;
  padding: 10px;
  color: #555;
  cursor: pointer;
  font-family: 'Pretendard', sans-serif;
  border-radius: 8px;
  transition: color 0.3s ease, background-color 0.3s ease;
  background-color: transparent; /* 배경색 투명 */
}

.sidebar li:hover {
  background-color: #f7f9fc; /* 마우스 오버 시 색상 변경 */
}

.sidebar .active {
  color: #4f75e6;
  font-weight: bold;
}

.content {
  width: 80%;
  padding: 30px;
  font-family: 'Pretendard', sans-serif;
}

.content h2 {
  font-size: 1.8rem;
  margin-bottom: 20px;
  font-family: 'Pretendard', sans-serif;
  font-weight: bold;
  color: #333;
  border-bottom: 2px solid #4f75e6;
  padding-bottom: 10px;
}

.event-list {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}

.event-card {
  width: 220px;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  padding: 15px;
  text-align: center;
  background-color: #fff;
  transition: transform 0.3s ease;
}

.event-card:hover {
  transform: translateY(-5px);
}

.event-card img {
  width: 100%;
  height: auto;
  border-radius: 10px;
  margin-bottom: 15px;
}

.event-card h3 {
  font-size: 1.2rem;
  margin: 0;
  font-family: 'Pretendard', sans-serif;
  color: #333;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: #555;
}

.form-group input {
  width: 100%;
  padding: 12px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  font-family: 'Pretendard', sans-serif;
  transition: border-color 0.3s ease;
}

.form-group input:focus {
  border-color: #4f75e6;
  outline: none;
}

button {
  background-color: #4f75e6;
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  cursor: pointer;
  font-family: 'Pretendard', sans-serif;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #3e5bb5;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  border: 1px solid #e0e0e0;
  padding: 15px;
  text-align: left;
  font-family: 'Pretendard', sans-serif;
}

th {
  background-color: #f7f9fc;
  font-weight: bold;
  color: #555;
}

a {
  color: #4f75e6;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style>

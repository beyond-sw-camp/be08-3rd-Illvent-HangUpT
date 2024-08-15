<template>
  <div class="container-fluid mt-5">
    <h1 class="mb-4 text-center">자유 게시판</h1>

    <div class="row justify-content-center">
      <!-- 필터링 체크박스 -->
      <div class="col-md-3">
        <h5>지역 필터링</h5>
        <div v-for="city in cities" :key="city" class="form-check">
          <input class="form-check-input" type="checkbox" :id="city" :value="city" v-model="selectedCities">
          <label class="form-check-label" :for="city">
            {{ city }}
          </label>
        </div>
      </div>

      <!-- 테이블 -->
      <div class="col-md-9">
        <div class="d-flex justify-content-between mb-3">
          <div>
            <label for="postsPerPage" class="form-label">페이지당 게시물 수</label>
            <select id="postsPerPage" v-model="postsPerPage" class="form-select">
              <option value="10">10개</option>
              <option value="20">20개</option>
              <option value="30">30개</option>
            </select>
          </div>

          <div>
            <label for="sortKey" class="form-label">정렬 기준</label>
            <select id="sortKey" v-model="sortKey" class="form-select">
              <option value="id">기본 설정 (ID 내림차순)</option>
              <option value="likes">좋아요 수</option>
              <option value="views">조회수</option>
            </select>
          </div>
        </div>

        <table class="table table-hover table-bordered text-center">
          <thead class="table-dark">
            <tr>
              <th scope="col" style="width: 5%;">ID</th>
              <th scope="col" style="width: 7%;">지역</th>
              <th scope="col" style="width: 50%;">제목</th>
              <th scope="col" style="width: 10%;">좋아요 수</th>
              <th scope="col" style="width: 7%;">조회수</th>
              <th scope="col" style="width: 10%;">날짜</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="post in filteredPosts" :key="post.id">
              <td>{{ post.id }}</td>
              <td>{{ post.area }}</td>
              <td class="text-start">
                <router-link :to="`/boards/${post.id}`" class="text-decoration-none">{{ post.title }}</router-link>
              </td>
              <td>{{ post.likes }}</td>
              <td>{{ post.views }}</td>
              <td>{{ post.date }}</td>
            </tr>
          </tbody>
        </table>

        <nav class="mt-3">
          <ul class="pagination justify-content-center">
            <li class="page-item" :class="{ disabled: currentPage === 1 }">
              <button class="page-link" @click="prevPage">이전</button>
            </li>
            <li v-for="page in totalPages" :key="page" class="page-item" :class="{ active: currentPage === page }">
              <button class="page-link" @click="goToPage(page)">{{ page }}</button>
            </li>
            <li class="page-item" :class="{ disabled: currentPage === totalPages }">
              <button class="page-link" @click="nextPage">다음</button>
            </li>
          </ul>
        </nav>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import data from '../data/board.js'

// 지역 목록
const cities = ["서울", "부산", "인천", "대구", "대전", "광주"]

const posts = ref(data)
const currentPage = ref(1)
const postsPerPage = ref(10)
const sortKey = ref('id')  // 기본 정렬은 ID 기준 내림차순
const selectedCities = ref([]) // 선택된 도시 필터

const sortedPosts = computed(() => {
  return [...posts.value].sort((a, b) => {
    if (sortKey.value === 'id') {
      return b.id - a.id  // 기본 설정: ID 내림차순
    } else if (sortKey.value === 'likes') {
      return b.likes - a.likes
    } else if (sortKey.value === 'views') {
      return b.views - a.views
    }
  })
})

const filteredPosts = computed(() => {
  let filtered = sortedPosts.value
  if (selectedCities.value.length > 0) {
    filtered = filtered.filter(post => selectedCities.value.includes(post.area))
  }
  const start = (currentPage.value - 1) * postsPerPage.value
  const end = start + postsPerPage.value
  return filtered.slice(start, end)
})

const totalPages = computed(() => {
  const totalFilteredPosts = selectedCities.value.length > 0
    ? sortedPosts.value.filter(post => selectedCities.value.includes(post.area)).length
    : sortedPosts.value.length
  return Math.ceil(totalFilteredPosts / postsPerPage.value)
})

// 페이지와 정렬 변경 시 페이지네이션 업데이트
watch([postsPerPage, sortKey, selectedCities], () => {
  currentPage.value = 1 // 페이지나 정렬 기준이 바뀔 때 첫 페이지로 이동
})

const prevPage = () => {
  if (currentPage.value > 1) currentPage.value--
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) currentPage.value++
}

const goToPage = (page) => {
  currentPage.value = page
}
</script>

<style scoped>
.container-fluid {
  max-width: 1200px;
}

h5 {
  margin-bottom: 15px;
}

.table-hover tbody tr:hover {
  background-color: #f8f9fa;
}

.pagination .page-link {
  color: #007bff;
}

.pagination .page-link:hover {
  color: #0056b3;
}

.table-dark {
  background-color: #343a40;
  color: white;
}

.table-dark th {
  text-align: center;
}

.form-check-label {
  margin-left: 5px;
}
</style>

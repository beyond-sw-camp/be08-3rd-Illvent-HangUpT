import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router/router';
// bootstrap 사용시 추가
// import 'bootstrap/dist/css/bootstrap.min.css';
// import 'bootstrap/dist/js/bootstrap.bundle.min';

createApp(App).use(router).mount('#app')

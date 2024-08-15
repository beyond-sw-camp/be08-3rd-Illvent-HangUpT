import { createWebHistory, createRouter } from "vue-router";
import Main from "../components/Main.vue";
import Board from "../components/Board.vue";
import BoardDetail from "../components/BoardDetail.vue";

const routes = [
    { path: "/", component: Main },
    { path: "/boards", component: Board },
    { path: "/boards/:id", component: BoardDetail },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;

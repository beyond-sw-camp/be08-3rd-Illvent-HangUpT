import { createWebHistory, createRouter } from "vue-router";
import Main from "../components/Main.vue";
import Board from "../components/Board.vue";
import BoardDetail from "../components/BoardDetail.vue";
import EventView from "../views/EventView.vue";
import BoardNew from "../components/BoardNew.vue";
import EventDetail from "../components/EventDetails.vue"
import KakaoMap from "../components/KakaoMap.vue";

const routes = [
    { path: "/", component: Main, props: route => ({ id: route.query.id })},
    { path: "/boards", component: Board },
    { path: "/boards/new", component: BoardNew },
    { path: "/boards/:id", component: BoardDetail },
    { path:"/events",name:'events',component: EventView}
    { path: "/event", component: EventDetail, props: true},
    { path: "/map", component: KakaoMap},
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
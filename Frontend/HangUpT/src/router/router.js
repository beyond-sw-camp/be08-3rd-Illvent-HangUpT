import { createWebHistory, createRouter } from "vue-router";
import Main from "../components/Main.vue";


const router = createRouter({
    history:createWebHistory(),
    routes:[
        {path:"/",component:Main},
    ]

})

export default router;
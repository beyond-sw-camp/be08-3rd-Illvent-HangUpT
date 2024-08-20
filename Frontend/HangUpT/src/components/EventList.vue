<template>
    <div class="container">
        <!-- 행사 출력 조건 -->
        <div class="title">
            <div class="event_count">
              <span>{{ props.events.length }}</span>   개의 행사가 검색되었습니다.
            </div>
        </div>

        <div class="content">
            <EventCard :events="events" @refresh-data="refreshData"/>
          
        </div>
    
    </div>

</template>

<script setup>
import { ref } from 'vue';
import EventCard from './EventCard.vue';

import { useRoute, useRouter } from 'vue-router';

    const currentRoute = useRoute();
    const router = useRouter();

    const props = defineProps(['events']);
    const emit = defineEmits(['refresh-data']);

    const refreshData=()=>{
        emit('refresh-data');
    }

   
    
</script>

<style lang="scss" scoped>
    .title {
        display: flex;
        justify-content: space-between;
        height: 50px;
        // background-color: aqua;
    }

    .event_count{
        font-size: large;
        span {
            color:purple;
        }
    }
  

   
</style>
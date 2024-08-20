<template>
    <div class="container">
        <!-- 내용 -->
        <div class="content_container">
            <!-- 필터 목록 -->
            <div class="filter_container">
                <!-- 필터 초기화 -->
                <div class="filter_header">
                    <div >필터</div>
                    <button id="filter_reset" @click="filterReset">필터 초기화</button>
                </div>
                <!-- 필터 >> 일시 -->
                <div class="filter_date">
                    <div class="filter_title">일시</div>
                    <div><input type="radio" name="date" value="ALL" v-model="selectDate"><label for="ALL">전체</label></div>
                    <div><input type="radio" name="date" value="TODAY" v-model="selectDate"><label for="TODAY">오늘</label></div>
                    <div><input type="radio" name="date" value="THIS_WEEK" v-model="selectDate"><label for="THIS_WEEK">이번주</label></div>
                    <div><input type="radio" name="date" value="THIS_MONTH" v-model="selectDate"><label for="THIS_MONTH">이번달</label></div>
                </div>
                <!-- 필터 >> 지역 -->
                <div>
                    <div class="filter_title">지역</div>
                    <select name="area" id="" v-model="selectRegion">
                        <option value="전체" :selected="selectRegion=='전체'">전체</option>
                        <option value="서울/경기/인천" :selected="selectRegion=='서울/경기/인천'">서울/경기/인천</option>
                        <option value="부산/울산/경남" :selected="selectRegion=='부산/울산/경남'">부산/울산/경남</option>
                        <option value="대구/경북" :selected="selectRegion=='대구/경북'">대구/경북</option>
                        <option value="충청/대전/세종" :selected="selectRegion=='충청/대전/세종'">충청/대전/세종</option>
                        <option value="전라/광주" :selected="selectRegion=='전라/광주'">전라/광주</option>
                        <option value="강원" :selected="selectRegion=='강원'">강원</option>
                        <option value="제주" :selected="selectRegion=='제주'">제주</option>
                    </select>
                </div>
                 <!-- 필터 >> 참여 방법 -->
                 <div>
                    <div class="filter_title">참여 방법</div>
                    <div><input type="radio" name="join" value="onoff" v-model="selectJoin"><label for="onoff">전체</label></div>
                    <div><input type="radio" name="join" value="online" v-model="selectJoin"><label for="online">온라인</label></div>
                    <div><input type="radio" name="join" value="offline" v-model="selectJoin"><label for="offline">오프라인</label></div>
                </div>
                <!-- 필터 >> 가격 -->
                <div>
                    <div class="filter_title">가격</div>
                    <div><input type="radio" name="price" value="freeAndPaid" v-model="selectPrice"><label for="freeAndPaid">전체</label></div>
                    <div><input type="radio" name="price" value="free" v-model="selectPrice"><label for="free">무료</label></div>
                    <div><input type="radio" name="price" value="paid" v-model="selectPrice"><label for="paid">유료</label></div>
                </div>
            </div>
            <!-- 이벤트 목록 -->
            <div class="event_container">
                <EventList :events="events" @refresh-data="refreshData"/>

             
            </div> 

        </div>
    </div>
</template>

<script setup>
    import { computed, onMounted, ref, watch } from 'vue';
    import EventList from '../components/EventList.vue';
    import { useRoute } from 'vue-router';
    import axios from 'axios';

    const route = useRoute();

    const events = ref([]);
    const selectDate = ref('ALL'); // 일시 
    const selectRegion = ref('전체'); // 지역 
    const selectJoin = ref(route.query.selectJoin||'onoff'); // 참여 방법 
    const selectPrice = ref(route.query.selectPrice||'freeAndPaid'); // 가격 

    
    onMounted(()=>{
        fetchEventData();
    });

    watch(()=>route.query,(newQuery)=>{
        if(route.query.selectPrice!=null){
            selectPrice.value = route.query.selectPrice;
        }
        if(route.query.selectJoin!=null){
            selectJoin.value = route.query.selectJoin;
        }
    });
    watch([selectDate,selectRegion,selectJoin,selectPrice],()=>{
        // 필터 조건값이 바뀌면 api 재 요청
        fetchEventData();
    });

    const filterReset=()=>{
        alert('필터를 초기화합니다.');
        selectDate.value = 'ALL';
        selectRegion.value = '전체';
        selectJoin.value = 'onoff';
        selectPrice.value = 'freeAndPaid';
        fetchEventData();
    }

    const fetchEventData = ()=>{
        // todo : api 호출
        console.log('api 호출 !')
        getEventsAPI();

    }

    async function getEventsAPI(){
        try{
            const response = await axios.get('http://localhost:8080/v1/api/event',{
                params:{
                    page:0,
                    size: 9,
                    date: selectDate.value,
                    // date:"ALL",
                    region: selectRegion.value,
                    join: selectJoin.value,
                    price:selectPrice.value
                }
            });
            console.log(response);
            console.log(response.data);
            events.value = response.data;

        }catch(error){
            console.log('API 요청 실패 : ',error);
        }
    }
    const refreshData = ()=>{
        getEventsAPI();
    }
</script>

<style lang="scss" scoped>

.container {
    margin: 0 auto;
    height: 100vh;
    padding: 0 1px;
}

.content_container {
    // background-color: bisque;
    display: flex;

}
.filter_container{
    // background-color: rgb(157, 202, 242);
    margin-top:20px;
    width: 20%;
    padding:0 12px 0 0;

    >div{
        margin-bottom: 25px;
    }
    select {
        width: 100%;
        height: 30px;
        margin-right:10px;
        border-radius: 5px;
        color: gray;
      &:focus {
        border: 1.5px solid purple;
        outline: none;
       
      }
    }

    input {
        margin-right:4px;
    }


    div{
        margin-top:4px;
    }

}

.event_container {
    margin-top:20px;
    width:80%;

}

.filter_header{
    display: flex;
    justify-content: space-between;
    div{
        color: purple;
    }
}

.filter_date {
    display: flex;
    flex-direction: column;
   

}

.filter_title {
    font-weight: 600;
    margin-bottom: 5px;
}
#filter_reset{
    color:gray;
    border: none;
    background-color: transparent;
}

</style>
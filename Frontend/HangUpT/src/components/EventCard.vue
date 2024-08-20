<template>
    <div class="container">
        <div  class="item_container" v-for="event in events" :key="event.id">
        <div>
            <div class="img_container">
               
                <img :src="event.imgUrl" :alt="event.title">  
                <!-- <img :src="scrapImgUrl" class="scrap-button" @click="onScrap"></img> -->
                <img :src="event.wish? checkImg:unCheckImg" class="scrap-button" @click="onScrap(event.id)"></img>
              

            
            
            </div>
            
        </div>  
        
        <div>
            <div class="date">{{ event.eventDate }}</div>
            <div class="title">{{ event.title }}</div>
            <div class="etc_container">
                <div class="price">{{ event.price }}</div>
                <div class="view_count">조회 {{ event.views }}</div>
            </div>
        </div>
    </div>
    </div>
   
</template>

<script setup>
    import { computed, onMounted, ref } from 'vue';
    import scrapUnCheck from '../assets/images/scrap_uncheck.png';
    import scrapCheck from '../assets/images/scrap_check.png';
    import axios from 'axios';

    const scrapImgUrl = ref(scrapUnCheck);
    const unCheckImg = ref(scrapUnCheck);
    const checkImg = ref(scrapCheck);

    const props = defineProps(['events']);
    const emit = defineEmits(['refresh-data']);

    // const events = ref(props.events);


    // onMounted(()=>{
    //     events.value = props.events;
    // });


    const onScrap =(eventNo)=>{
     //   scrapImgUrl.value = scrapCheck;
     // 로그인 여부 먼저 확인 
     // todo 스크랩 api 호출
     console.log('onScrap 클릭');
     const isLoggedIn = localStorage.getItem("isLoggedIn");
    
     
     if(isLoggedIn=="false"){
        alert("로그인후 이용해주세요");
        return;
     }
     // todo : 관심행사 등록 api 호출
     // api 재실행 
     const userInfo = localStorage.getItem("userInfo");
     const memberNo = JSON.parse(userInfo)['no'];

     console.log(`memberNo : ${memberNo}`);
     console.log(`eventNo : ${eventNo}`);

     scrapApi(memberNo,eventNo);

    }

    const scrapApi = async(memberNo,eventNo)=>{
        try{
                await axios.post('http://localhost:8080/v1/api/wish/register',{
                 memberNo,
                 eventNo
            }).then((res)=>{
                if(res.status==201){
                    alert("관심행사로 등록되었습니다.");
                    emit('refresh-data');
                }else if(res.status==400){
                    alert("관심행사 등록에 실패했습니다.");
                }
            })

        }catch(err){
            console.log(err);
        }

    }
    
    
</script>

<style lang="scss" scoped>
    .container {
        margin-top: 20px;
        // display: flex;
        // flex-direction: row;
        // flex-wrap: wrap;
        // justify-content: space-between;
        display: grid;
        grid-template-columns: repeat(3, 1fr); 
        gap: 15px; /* 아이템 간의 간격 */

    }
    .item_container {
        position: relative;
        margin-bottom: 20px;
        img{
            width: 100%;
        }
    }

    .date{
        font-size: small;
        color: gray;
    }

    .etc_container {
        display: flex;
        justify-content: space-between;
    }

    .title {
        font-weight: 500;
    }

    .price {
        color: purple;
    }

    .view_count {
        color: gray;
    }

    .scrap-button {
       position: absolute;
       top:5px;
       right:10px;
       width:35px !important;
       cursor: pointer;
     
    }

</style>
<template>
    <div class="carousel-wrapper">
    <Carousel :items-to-show="4.5" :wrap-around="false" :autoplay="false" transition="500">
        <Slide v-for="item in list" :key="item.id">
        <div class="slide_item">
            <img :src="getImageUrl(item.image)" class="slide-image" />
            <div class="slide-content">
            <p class="slide-date">{{ item.date }}</p>
            <h2 class="slide-title">{{ item.title }}</h2>
            <h4 class="slide-price">{{ item.price }}</h4>
            </div>
        </div>
        </Slide>

        <template #addons>
        <!-- <div class="navigation-container">
            <Navigation />
        </div> -->
        </template>
    </Carousel>
    </div>
</template>

<script>
import { defineComponent } from 'vue'
import { Carousel, Navigation, Slide } from 'vue3-carousel'

import 'vue3-carousel/dist/carousel.css'

import data from '../assets/list.js'

export default defineComponent({
    name: 'WrapAround',
    components: {
        Carousel,
        Slide,
        Navigation,
    },
    data(){
        return {
            list : data
        }
    },
    methods:{
            getImageUrl(url) {
                return new URL(`${url}`, import.meta.url).href;
            }
    },
})
</script>

<style>
.carousel-container {
    position: relative;
    width: 100%;
}
    
.slide-item {
    position: relative;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
    
.slide-image {
    width: 100%;
    height: 300px;
    object-fit: cover;
}
    
.slide-overlay {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.4);
    color: white;
    padding: 15px;
    box-sizing: border-box;
    border-top-left-radius: 8px;
    border-top-right-radius: 8px;
}

.slide-content {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}

.slide-date {
    font-size: 14px;
    color: #b0b0b0;
    margin-bottom: 5px;
}

.slide-title {
    font-size: 18px;
    margin: 0;
    font-weight: bold;
}

.slide-price {
    font-size: 14px;
    font-weight: bold;
    color: blueviolet;
    margin-top: 20px;
}

</style>
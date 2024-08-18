<template>
    <p>{{ message }}</p>
    <div class="carousel-wrapper">
        <Carousel :items-to-show="4.5" :wrap-around="false" :autoplay="false" transition="500">
        <Slide v-for="item in list" :key="item.no">
            <div class="slide-item">
                <router-link :to="{path: `/event`, query: {id: item.no}}"><h4>{{ item }}</h4></router-link>
            <!-- <img :src="item.image" class="slide-image" />
            <div class="slide-content">
                <p class="slide-date">{{ item.date }}</p>
                <h2 class="slide-title">{{ item.title }}</h2>
                <h4 class="slide-price">{{ item.price }}</h4>
            </div> -->
            </div>
        </Slide>

        <template #addons>
        </template>
        </Carousel>
    </div>
</template>

<script>
    import { ref, onMounted } from 'vue';
    import { Carousel, Navigation, Slide } from 'vue3-carousel';
    import 'vue3-carousel/dist/carousel.css';
    import axios from 'axios';

    const baseUrl = 'http://localhost:8080/v1/api/event';

export default {
    name: 'WrapAround',
    components: {
        Carousel,
        Slide,
        Navigation,
    },
    setup() {
        const list = ref([]);
        const message = ref('Loading...');

        const getImageUrl = (url) => {
            return new URL(`${url}`, import.meta.url).href;
        };

        const fetchData = async() => {
            try {
                const result = await axios.get(`${baseUrl}/list`);
                console.log(result.data);
                list.value = result.data;
            } catch (error) {
                console.error('Error fetching data:', error);
            } finally {
                message.value = "Data loaded";
            }
        };

        onMounted(() => {
            fetchData();
        });

        return {
            list,
            message,
            getImageUrl,
        };
    },
};
</script>

<style>
.carousel-wrapper {
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

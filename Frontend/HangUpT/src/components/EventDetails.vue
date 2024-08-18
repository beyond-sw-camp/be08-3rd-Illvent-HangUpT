<template>
    <div class="event-details-container">
        <header class="event-header">
            <p>{{id}}</p>
            <p>{{event.id}}</p>
        </header>
        <section class="sidebar">
            <!-- <p>{{event.title }}</p> -->
            <p>경기/인천/서울</p>
            <h3>Busan NE(O)RDINARY DemoDay : FLASH</h3>
            <img src="../assets/list/1.jpg"><br>
            <a align="center" class="register-button">신청하기</a>
            <a align="center" class="inquiry-button">관심 행사 ♡</a>
        </section>
        <section class="event-content">
            <!-- <img :src="getImageUrl(event.bannerImage)" class="event-banner" alt="Event Banner"/> -->
            <img src="https://eventusstorage.blob.core.windows.net/evs/Image/sparklabs-demoday12/88048/ProjectInfo/Cover/29a292e4e86e44cebe25ad72929b975e.jpg" 
            clss="event-banner" alt="Event Banner">
            <div class="event-info">
                <div class="event-details">
                    <h2>행사 정보</h2>
                    <ul>
                        <li><strong>일시</strong> {{ event.date }} </li>
                        <li><strong>비용</strong> {{ event.price }}</li>
                        <li><strong>장소</strong> {{ event.location }}</li>
                        <li><strong>주소</strong> {{ event.address }} </li>
                        <!-- <li><strong>Organizer</strong> {{ event.organizer }}</li>
                            <li><strong>Speakers</strong> {{ event.speakers }}</li> -->
                    </ul>
                </div>
                <!-- <div v-for="(des, i) in event.description" :key="i">
                    <p class="event-description" >{{ des }}</p>
                </div> -->
            </div>
        </section>
        
        <footer class="event-footer">
        </footer>
    </div>
</template>
    
<script>
    import { defineComponent, ref, computed, onMounted } from 'vue';
    import { useRoute } from 'vue-router';
    import axios from 'axios';
    const baseUrl = 'http://localhost:8080/v1/api/event';
    
export default {
    name: 'EventDetails',
    components: {
        
    },
    setup() {
        const event = ref({});
        const message = ref('');

        const route = useRoute();
        const id = route.query.id;
    
        const getImageUrl = (url) => {
            return new URL(`${url}`, import.meta.url).href;
        };
        
        const fetchData = async() => {
            try {
                const result = await axios.get(`${baseUrl}/${id}`);
                console.log(result.data);
                event.value = result.data;
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
            event,
            getImageUrl,
            id,
            event,
            route,
            message
        };
    }
};
</script>

<style>
.event-details-container {
    float: left;
    width: 80%;
    margin-top: 100px;
    padding: 20px;
    align-items: left;
    /* padding-right: 400px; */
    padding-left: 15%;
    height: 2000px;
}

.event-header {
    text-align: center;
    margin-bottom: 20px;
}

.event-title {
    font-size: 2rem;
    margin: 0;
}

.event-meta {
    color: grey;
}

.event-date,
.event-location {
    margin: 5px 0;
}

.event-content {
    display: flex;
    flex-direction: column;
    align-items: left;
    padding-right: 30px;
}

.event-banner {
    width: 100%;
    height: auto;
    max-height: 400px;
    object-fit: cover;
    margin-bottom: 20px;
}

.event-info {
    /* max-width: 800px; */
    text-align: center;
    padding-top: 200px;
}

.event-description {
    font-size: 1.2rem;
    margin-bottom: 20px;
    padding-top: 30px;
    text-align: left;
}

.event-details {
    text-align: left;
}

.event-details h2 {
    font-size: 1.5rem;
}

.event-details ul {
    list-style: none;
    padding: 0;
}

.event-details li {
    margin-bottom: 10px;
    padding-top: 10px;
}

.event-details li strong {
    padding-right: 30px;
}

.event-footer {
    text-align: center;
    margin-top: 20px;
}

.register-button,
.inquiry-button {
    display: inline-block;
    padding: 10px 20px;
    margin: 10px;
    color: white;
    text-decoration: none;
    border-radius: 5px;
    font-size: 1rem;
    width: 44%;
}

.register-button {
    background-color: rebeccapurple;
}

.inquiry-button {
    background-color: rgb(138, 236, 239);
    color: rgb(55, 118, 218);
}

.sidebar {
    position: sticky;
    top: 100px;
    width: 400px; 
    background-color: white;
    padding: 20px;
    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.3);
    float: right;
}
.sidebar h {
    font-size: 25px;
    font-weight: bold;
}
.sidebar p {
    font-size: 13px;
    color: gray;
}
</style>
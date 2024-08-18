<template>
    <div>
      <div id="map" style="width: 80%; height: 1000px;"></div>
      <div v-if="zoomLevel <= 5" class="marker-count">Markers: {{ markers.length }}</div>
    </div>
</template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  const map = ref(null);
  const markers = ref([]);
  const zoomLevel = ref(3);
  const markerClusterer = ref(null);
  const infowindow = new kakao.maps.InfoWindow({ zIndex: 1 }); // 인포윈도우 추가
  
  const initializeMap = () => {
    // 지도의 중심좌표 설정
    const mapCenter = new kakao.maps.LatLng(37.5665, 126.9780); // 서울 시청 좌표 예시
  
    // 지도를 생성합니다.
    map.value = new kakao.maps.Map(document.getElementById('map'), {
      center: mapCenter,
      level: zoomLevel.value, // 지도의 확대 레벨
    });
  
    // 클러스터러를 생성합니다.
    markerClusterer.value = new kakao.maps.MarkerClusterer({
      map: map.value, // 지도 객체
      averageCenter: true, 
      minLevel: 5, 
      disableClickZoom: true, 
      gridSize: 60, 
    });
  
    // 마커 생성 및 인포윈도우 설정
    const positions = [
    { title: '창덕궁 달빛기행', latlng: new kakao.maps.LatLng(37.579617, 126.991061), url: 'https://example.com/changdeokgung', info: '유네스코 세계문화유산인 창덕궁에서 달빛을 따라 궁궐을 탐방하는 프로그램입니다.' },
    { title: '경복궁 수문장 교대식', latlng: new kakao.maps.LatLng(37.579617, 126.977041), url: 'https://example.com/gyeongbokgung', info: '조선 시대 궁궐의 문을 지키던 수문장 교대 의식을 재현합니다.' },
    { title: '덕수궁 석조전 음악회', latlng: new kakao.maps.LatLng(37.566347, 126.974655), url: 'https://royal.khs.go.kr/ROYAL/contents/R201000000.do?undefined&act=view&ceIdx=202406070114', info: '서양식 건축물인 석조전에서 열리는 클래식 음악 공연입니다.' },
    { title: '창경궁 야간개장', latlng: new kakao.maps.LatLng(37.578453, 126.995669), url: 'https://example.com/changkyunggung', info: '밤에 조명이 켜진 창경궁을 자유롭게 둘러볼 수 있는 특별 개방 프로그램입니다.' },
    { title: '서울 한양도성 걷기', latlng: new kakao.maps.LatLng(37.592372, 127.009112), url: 'https://example.com/hanyangdoseong', info: '서울 한양도성을 따라 걸으며 역사와 자연을 동시에 느낄 수 있는 행사입니다.' },
    { title: '국립중앙박물관 특별전시', latlng: new kakao.maps.LatLng(37.523987, 126.980048), url: 'https://example.com/nationalmuseum', info: '국립중앙박물관에서 열리는 다양한 주제의 특별 전시입니다.' },
    { title: '남산 봉수대 체험', latlng: new kakao.maps.LatLng(37.551169, 126.987862), url: 'https://example.com/namsan', info: '남산에 위치한 봉수대에서 옛 통신 방법을 체험할 수 있는 행사입니다.' },
    { title: '북촌 한옥마을 문화 체험', latlng: new kakao.maps.LatLng(37.582604, 126.983013), url: 'https://example.com/bukchon', info: '북촌 한옥마을에서 전통 한옥을 둘러보고 다양한 전통 문화를 체험할 수 있는 프로그램입니다.' },
    { title: '서울역사박물관 야간개장', latlng: new kakao.maps.LatLng(37.570789, 126.971148), url: 'https://example.com/seoulmuseum', info: '서울의 역사를 조명하는 서울역사박물관의 특별 야간 개장입니다.' },
    { title: '이화마을 벽화길 걷기', latlng: new kakao.maps.LatLng(37.580482, 127.005284), url: 'https://example.com/ihwamural', info: '서울 종로구 이화동의 벽화마을을 따라 걷는 문화 행사입니다.' },

    { title: '수원 화성 문화제', latlng: new kakao.maps.LatLng(37.288115, 127.015441), url: 'https://example.com/suwonhwaseong', info: '수원 화성에서 열리는 전통 문화 축제입니다.' },
    { title: '양평 두물머리 생태 체험', latlng: new kakao.maps.LatLng(37.531662, 127.319032), url: 'https://example.com/dumulmori', info: '남한강과 북한강이 만나는 두물머리에서 자연 생태를 체험하는 프로그램입니다.' },
    { title: '남한산성 성곽 투어', latlng: new kakao.maps.LatLng(37.473683, 127.160186), url: 'https://example.com/namhansanseong', info: '유네스코 세계문화유산 남한산성을 둘러보는 투어입니다.' },
    { title: '파주 헤이리 예술마을 페스티벌', latlng: new kakao.maps.LatLng(37.803644, 126.684974), url: 'https://example.com/heyri', info: '예술마을 헤이리에서 열리는 다양한 예술 관련 행사입니다.' },
    { title: '안성 남사당놀이 체험', latlng: new kakao.maps.LatLng(37.005721, 127.268544), url: 'https://example.com/namsadang', info: '안성에서 전통 남사당놀이를 체험할 수 있는 프로그램입니다.' },
    { title: '용인 에버랜드 전통문화 체험', latlng: new kakao.maps.LatLng(37.294979, 127.202199), url: 'https://example.com/everland', info: '에버랜드에서 열리는 다양한 전통문화 체험 프로그램입니다.' },
    { title: '광주 남한산성 행궁', latlng: new kakao.maps.LatLng(37.477825, 127.165109), url: 'https://example.com/haenggung', info: '남한산성 행궁에서 열리는 역사 체험 프로그램입니다.' },
    { title: '고양 행주산성 문화제', latlng: new kakao.maps.LatLng(37.618732, 126.812268), url: 'https://example.com/haengju', info: '고양 행주산성에서 열리는 전통 문화제입니다.' },
    { title: '포천 아트밸리 음악회', latlng: new kakao.maps.LatLng(37.894589, 127.248204), url: 'https://example.com/artvalley', info: '포천 아트밸리에서 열리는 야외 음악회입니다.' },
    { title: '여주 세종대왕릉 문화제', latlng: new kakao.maps.LatLng(37.295720, 127.634290), url: 'https://example.com/sejong', info: '세종대왕릉에서 열리는 전통 문화 행사입니다.' },
    { title: '가평 자라섬 국제 재즈페스티벌', latlng: new kakao.maps.LatLng(37.842636, 127.515419), url: 'https://example.com/jarasum', info: '자라섬에서 열리는 국제적인 재즈 음악 축제입니다.' },
    { title: '화성 융건릉 문화제', latlng: new kakao.maps.LatLng(37.206258, 126.944307), url: 'https://example.com/yunggeolleung', info: '화성 융건릉에서 열리는 전통 문화 행사입니다.' },
    { title: '평택 농악 축제', latlng: new kakao.maps.LatLng(36.990182, 127.112423), url: 'https://example.com/pyeongtaek', info: '평택에서 열리는 전통 농악 축제입니다.' },
    { title: '안양 예술공원 전통공예 체험', latlng: new kakao.maps.LatLng(37.401956, 126.927626), url: 'https://example.com/anyang', info: '안양 예술공원에서 열리는 전통공예 체험 프로그램입니다.' },
    { title: '양주 천년의 숲 걷기', latlng: new kakao.maps.LatLng(37.785742, 127.045206), url: 'https://example.com/yangju', info: '양주 천년의 숲을 걷고 자연을 체험하는 행사입니다.' },
    { title: '의정부 회룡문화제', latlng: new kakao.maps.LatLng(37.739709, 127.047216), url: 'https://example.com/uijeongbu', info: '의정부에서 열리는 전통 문화제입니다.' },
    { title: '김포 문수산성 문화제', latlng: new kakao.maps.LatLng(37.699119, 126.580841), url: 'https://example.com/munsusan', info: '김포 문수산성에서 열리는 전통 문화 행사입니다.' },
    { title: '이천 도자기 축제', latlng: new kakao.maps.LatLng(37.279327, 127.442208), url: 'https://example.com/icheon', info: '이천에서 열리는 전통 도자기 축제입니다.' },
    { title: '오산 독산성 역사 체험', latlng: new kakao.maps.LatLng(37.145774, 127.070040), url: 'https://example.com/osanseong', info: '오산 독산성에서 열리는 역사 체험 행사입니다.' },
    { title: '동두천 소요산 걷기 대회', latlng: new kakao.maps.LatLng(37.948544, 127.060684), url: 'https://example.com/soyosan', info: '동두천 소요산에서 열리는 자연 걷기 대회입니다.' },
    { title: '춘천 닭갈비 축제', latlng: new kakao.maps.LatLng(37.881315, 127.730000), url: 'https://example.com/chuncheon', info: '춘천에서 열리는 닭갈비 축제입니다.' },
    { title: '강릉 단오제', latlng: new kakao.maps.LatLng(37.755832, 128.896901), url: 'https://example.com/gangneung', info: '유네스코 인류무형문화유산으로 지정된 강릉 단오제입니다.' },
    { title: '속초 설악문화제', latlng: new kakao.maps.LatLng(38.207192, 128.591782), url: 'https://example.com/seorak', info: '속초에서 열리는 전통 문화제입니다.' },
    { title: '동해 묵호등대 별빛축제', latlng: new kakao.maps.LatLng(37.572651, 129.117934), url: 'https://example.com/mukho', info: '묵호등대에서 열리는 별빛축제입니다.' },
    { title: '평창 대관령 눈꽃축제', latlng: new kakao.maps.LatLng(37.673731, 128.706635), url: 'https://example.com/daegwallyeong', info: '평창 대관령에서 열리는 눈꽃축제입니다.' },
    { title: '정선 아리랑제', latlng: new kakao.maps.LatLng(37.380032, 128.660622), url: 'https://example.com/jeongseon', info: '정선에서 열리는 전통 아리랑 축제입니다.' },
    { title: '영월 동강 뗏목축제', latlng: new kakao.maps.LatLng(37.184757, 128.461594), url: 'https://example.com/donggang', info: '영월에서 열리는 동강 뗏목 축제입니다.' },
    { title: '원주 한지문화제', latlng: new kakao.maps.LatLng(37.342218, 127.920162), url: 'https://example.com/hanji', info: '원주에서 열리는 전통 한지 축제입니다.' },
    { title: '철원 한탄강 얼음축제', latlng: new kakao.maps.LatLng(38.146641, 127.313167), url: 'https://example.com/hantan', info: '철원 한탄강에서 열리는 얼음 축제입니다.' },
    { title: '홍천강 별빛축제', latlng: new kakao.maps.LatLng(37.691987, 127.888939), url: 'https://example.com/hongcheon', info: '홍천강에서 열리는 별빛 축제입니다.' },
    { title: '삼척 해양레일바이크 축제', latlng: new kakao.maps.LatLng(37.441631, 129.165664), url: 'https://example.com/samcheok', info: '삼척에서 열리는 해양레일바이크 축제입니다.' },
    { title: '양양 송이축제', latlng: new kakao.maps.LatLng(38.076127, 128.619995), url: 'https://example.com/yangyang', info: '양양에서 열리는 송이 축제입니다.' },
    { title: '고성 설악산 낙산사 축제', latlng: new kakao.maps.LatLng(38.200837, 128.591531), url: 'https://example.com/naksansa', info: '고성 설악산 낙산사에서 열리는 전통 축제입니다.' },
    { title: '화천 산천어 축제', latlng: new kakao.maps.LatLng(38.106276, 127.709247), url: 'https://example.com/hwacheon', info: '화천에서 열리는 산천어 축제입니다.' },
    { title: '인제 빙어 축제', latlng: new kakao.maps.LatLng(38.069395, 128.170564), url: 'https://example.com/inje', info: '인제에서 열리는 빙어 축제입니다.' },
    { title: '양구 펀치볼 문화제', latlng: new kakao.maps.LatLng(38.103174, 127.989368), url: 'https://example.com/punchbowl', info: '양구에서 열리는 펀치볼 문화제입니다.' },
    { title: '강릉 경포대 전통문화 체험', latlng: new kakao.maps.LatLng(37.795686, 128.905275), url: 'https://example.com/gyeongpodae', info: '강릉 경포대에서 전통 문화를 체험할 수 있는 행사입니다.' },
    { title: '춘천 소양강 스카이워크 축제', latlng: new kakao.maps.LatLng(37.903874, 127.744739), url: 'https://example.com/soyanggang', info: '춘천 소양강에서 열리는 스카이워크 축제입니다.' },
    { title: '속초 해맞이 축제', latlng: new kakao.maps.LatLng(38.207081, 128.591686), url: 'https://example.com/sokcho', info: '속초에서 열리는 해맞이 축제입니다.' },
    { title: '고성 공룡박물관 문화제', latlng: new kakao.maps.LatLng(38.379395, 128.467246), url: 'https://example.com/dinopark', info: '고성 공룡박물관에서 열리는 전통 문화 행사입니다.' },
    { title: '양양 오색약수 문화제', latlng: new kakao.maps.LatLng(38.119392, 128.535654), url: 'https://example.com/oseak', info: '양양 오색약수에서 열리는 전통 문화제입니다.' },
    { title: '동해 망상해변 해맞이 축제', latlng: new kakao.maps.LatLng(37.524294, 129.114555), url: 'https://example.com/mangsang', info: '동해 망상해변에서 열리는 해맞이 축제입니다.' },
    { title: '강릉 솔향기길 걷기 축제', latlng: new kakao.maps.LatLng(37.773625, 128.923801), url: 'https://example.com/solhyanggi', info: '강릉 솔향기길에서 열리는 걷기 축제입니다.' },
    { title: '원주 소금산 출렁다리 축제', latlng: new kakao.maps.LatLng(37.293328, 127.847131), url: 'https://example.com/sogeumsan', info: '원주 소금산에서 열리는 출렁다리 축제입니다.' },
    { title: '철원 고석정 문화제', latlng: new kakao.maps.LatLng(38.146374, 127.312853), url: 'https://example.com/goseokjeong', info: '철원 고석정에서 열리는 전통 문화제입니다.' },
    { title: '경주 불국사 야경', latlng: new kakao.maps.LatLng(35.789403, 129.332360), url: 'https://example.com/bulguksa', info: '경주 불국사에서 열리는 야경 체험 행사입니다.' },
    { title: '부산 국제영화제', latlng: new kakao.maps.LatLng(35.101944, 129.032502), url: 'https://example.com/biff', info: '부산에서 열리는 세계적인 국제영화제입니다.' },
    { title: '안동 하회마을 탈춤', latlng: new kakao.maps.LatLng(36.538923, 128.516236), url: 'https://example.com/hahoe', info: '유네스코 세계문화유산인 하회마을에서 탈춤 공연이 열립니다.' },
    { title: '대구 서문시장 야시장', latlng: new kakao.maps.LatLng(35.871303, 128.585732), url: 'https://example.com/seomun', info: '대구 서문시장에서 열리는 야시장입니다.' },
    { title: '울산 태화강 대공원 불꽃놀이', latlng: new kakao.maps.LatLng(35.551086, 129.293126), url: 'https://example.com/taehwagang', info: '울산 태화강 대공원에서 열리는 불꽃놀이 행사입니다.' },
    { title: '포항 영일대 해수욕장 축제', latlng: new kakao.maps.LatLng(36.053963, 129.367809), url: 'https://example.com/yeongildae', info: '포항 영일대 해수욕장에서 열리는 축제입니다.' },
    { title: '창원 진해 군항제', latlng: new kakao.maps.LatLng(35.152759, 128.710727), url: 'https://example.com/jinhae', info: '창원 진해에서 열리는 군항제입니다.' },
    { title: '경주 첨성대 별빛축제', latlng: new kakao.maps.LatLng(35.835277, 129.221898), url: 'https://example.com/cheomseongdae', info: '경주 첨성대에서 열리는 별빛축제입니다.' },
    { title: '김해 가야문화축제', latlng: new kakao.maps.LatLng(35.233572, 128.889607), url: 'https://example.com/gaya', info: '김해에서 열리는 가야문화축제입니다.' },
    { title: '진주 남강유등축제', latlng: new kakao.maps.LatLng(35.192469, 128.084636), url: 'https://korean.visitkorea.or.kr/kfes/detail/fstvlDetail.do?cmsCntntsId=506926', info: '진주에서 열리는 남강유등축제입니다.' },
    { title: '울산 간절곶 해맞이 축제', latlng: new kakao.maps.LatLng(35.409244, 129.360598), url: 'https://example.com/ganjeolgot', info: '울산 간절곶에서 열리는 해맞이 축제입니다.' },
    { title: '부산 해운대 모래축제', latlng: new kakao.maps.LatLng(35.158697, 129.160384), url: 'https://example.com/haeundae', info: '부산 해운대에서 열리는 모래축제입니다.' },
    { title: '통영 한산대첩 축제', latlng: new kakao.maps.LatLng(34.838398, 128.429408), url: 'https://example.com/hansan', info: '통영에서 열리는 한산대첩을 기념하는 축제입니다.' },
    { title: '거제 해금강 낚시대회', latlng: new kakao.maps.LatLng(34.618586, 128.634446), url: 'https://example.com/haegeumgang', info: '거제 해금강에서 열리는 낚시대회입니다.' },
    { title: '포항 호미곶 해맞이 축제', latlng: new kakao.maps.LatLng(36.228036, 129.566747), url: 'https://example.com/homigot', info: '포항 호미곶에서 열리는 해맞이 축제입니다.' },
    { title: '안동 국제탈춤 페스티벌', latlng: new kakao.maps.LatLng(36.565838, 128.729569), url: 'https://example.com/talchum', info: '안동에서 열리는 국제탈춤 페스티벌입니다.' },
    { title: '대구 동성로 거리축제', latlng: new kakao.maps.LatLng(35.868945, 128.595559), url: 'https://example.com/dongseongro', info: '대구 동성로에서 열리는 거리축제입니다.' },
    { title: '경주 양동마을 문화제', latlng: new kakao.maps.LatLng(35.819808, 129.351722), url: 'https://example.com/yangdong', info: '경주 양동마을에서 열리는 전통 문화제입니다.' },
    { title: '울산 옹기축제', latlng: new kakao.maps.LatLng(35.566254, 129.259158), url: 'https://example.com/onggi', info: '울산에서 열리는 전통 옹기축제입니다.' },
    { title: '김해 봉하마을 평화축제', latlng: new kakao.maps.LatLng(35.244756, 128.717113), url: 'https://example.com/bongha', info: '김해 봉하마을에서 열리는 평화축제입니다.' },
    { title: '울진 대게축제', latlng: new kakao.maps.LatLng(36.990569, 129.400624), url: 'https://example.com/daege', info: '울진에서 열리는 대게 축제입니다.' },
    { title: '창원 용지호수 문화제', latlng: new kakao.maps.LatLng(35.230274, 128.675176), url: 'https://example.com/yongji', info: '창원 용지호수에서 열리는 전통 문화제입니다.' },
    { title: '경산 압량시장 나눔축제', latlng: new kakao.maps.LatLng(35.814726, 128.809738), url: 'https://example.com/yeongnam', info: '경산 압량시장에서 열리는 나눔축제입니다.' },
    { title: '영덕 바다축제', latlng: new kakao.maps.LatLng(36.415141, 129.365135), url: 'https://example.com/yeongdeok', info: '영덕에서 열리는 바다 축제입니다.' },
    { title: '밀양 영남루 축제', latlng: new kakao.maps.LatLng(35.502457, 128.747641), url: 'https://example.com/yeongnamru', info: '밀양에서 열리는 전통 문화제입니다.' },
    { title: '전주 한옥마을 전통체험', latlng: new kakao.maps.LatLng(35.815084, 127.153564), url: 'https://example.com/hanokvillage', info: '전주 한옥마을에서 전통문화를 체험할 수 있는 행사입니다.' },
    { title: '광주 비엔날레', latlng: new kakao.maps.LatLng(35.160072, 126.878967), url: 'https://example.com/biennale', info: '광주에서 열리는 세계적인 현대미술 전시회입니다.' },
    { title: '남원 춘향제', latlng: new kakao.maps.LatLng(35.416414, 127.390979), url: 'https://example.com/chunhyang', info: '남원에서 열리는 춘향을 기념하는 전통 축제입니다.' },
    { title: '목포 항구축제', latlng: new kakao.maps.LatLng(34.807219, 126.392551), url: 'https://example.com/mokpo', info: '목포 항구에서 열리는 축제입니다.' },
    { title: '여수 밤바다 불꽃놀이', latlng: new kakao.maps.LatLng(34.760429, 127.662222), url: 'https://example.com/yeosu', info: '여수 밤바다에서 열리는 불꽃놀이 행사입니다.' },
    { title: '순천만 국제정원박람회', latlng: new kakao.maps.LatLng(34.950794, 127.490762), url: 'https://example.com/suncheon', info: '순천만에서 열리는 국제적인 정원박람회입니다.' },
    { title: '완도 해양치유축제', latlng: new kakao.maps.LatLng(34.315929, 126.756787), url: 'https://example.com/wando', info: '완도에서 열리는 해양치유 축제입니다.' },
    { title: '담양 대나무축제', latlng: new kakao.maps.LatLng(35.319002, 126.987853), url: 'https://example.com/damyang', info: '담양에서 열리는 대나무 축제입니다.' },
    { title: '고창 청보리밭 축제', latlng: new kakao.maps.LatLng(35.433425, 126.560062), url: 'https://example.com/gochang', info: '고창에서 열리는 청보리밭 축제입니다.' },
    { title: '무주 반딧불이 축제', latlng: new kakao.maps.LatLng(35.906174, 127.660037), url: 'https://example.com/muju', info: '무주에서 열리는 반딧불이 축제입니다.' },
    { title: '나주 배꽃축제', latlng: new kakao.maps.LatLng(35.015346, 126.710131), url: 'https://example.com/naju', info: '나주에서 열리는 배꽃축제입니다.' },
    { title: '군산 시간여행 축제', latlng: new kakao.maps.LatLng(35.978421, 126.711143), url: 'https://example.com/gunsan', info: '군산에서 열리는 시간여행 축제입니다.' },
    { title: '진안 홍삼축제', latlng: new kakao.maps.LatLng(35.791772, 127.429297), url: 'https://example.com/jinan', info: '진안에서 열리는 홍삼 축제입니다.' },
    { title: '광양 매화축제', latlng: new kakao.maps.LatLng(34.975131, 127.587274), url: 'https://example.com/gwangyang', info: '광양에서 열리는 매화 축제입니다.' },
    { title: '해남 땅끝해양 마라톤 대회', latlng: new kakao.maps.LatLng(34.389172, 126.601323), url: 'https://example.com/haenam', info: '해남 땅끝에서 열리는 마라톤 대회입니다.' },
    { title: '구례 산수유꽃 축제', latlng: new kakao.maps.LatLng(35.203996, 127.462219), url: 'https://example.com/gurye', info: '구례에서 열리는 산수유꽃 축제입니다.' },
    { title: '부안 곰소젓갈 축제', latlng: new kakao.maps.LatLng(35.729100, 126.559800), url: 'https://example.com/gomso', info: '부안에서 열리는 곰소젓갈 축제입니다.' },
    { title: '화순 국화향연', latlng: new kakao.maps.LatLng(35.061089, 126.986026), url: 'https://example.com/hwasun', info: '화순에서 열리는 국화향연 축제입니다.' },
    { title: '강진 청자축제', latlng: new kakao.maps.LatLng(34.634326, 126.771546), url: 'https://example.com/gangjin', info: '강진에서 열리는 청자 축제입니다.' },
    { title: '장흥 편백숲 건강걷기 대회', latlng: new kakao.maps.LatLng(34.682209, 126.907663), url: 'https://example.com/jangheung', info: '장흥에서 열리는 편백숲 걷기 대회입니다.' },
    { title: '곡성 기차마을 축제', latlng: new kakao.maps.LatLng(35.282682, 127.290942), url: 'https://example.com/gokseong', info: '곡성에서 열리는 기차마을 축제입니다.' },
    { title: '영광 불갑사 상사화 축제', latlng: new kakao.maps.LatLng(35.284805, 126.512072), url: 'https://example.com/bulgapsa', info: '영광 불갑사에서 열리는 상사화 축제입니다.' },
    { title: '순천 순천만습지 갈대축제', latlng: new kakao.maps.LatLng(34.950677, 127.490762), url: 'https://example.com/suncheonreeds', info: '순천만습지에서 열리는 갈대축제입니다.' },
    { title: '광주 5.18 민주화운동 기념행사', latlng: new kakao.maps.LatLng(35.146514, 126.919816), url: 'https://example.com/gwangju518', info: '광주에서 열리는 5.18 민주화운동 기념행사입니다.' },
    { title: '목포 해양레일바이크 축제', latlng: new kakao.maps.LatLng(34.808957, 126.392551), url: 'https://example.com/mokporailbike', info: '목포에서 열리는 해양레일바이크 축제입니다.' },
    { title: '대전 엑스포 과학공원 축제', latlng: new kakao.maps.LatLng(36.374499, 127.385309), url: 'https://example.com/expo', info: '대전 엑스포 과학공원에서 열리는 과학 축제입니다.' },
    { title: '충주 세계무술축제', latlng: new kakao.maps.LatLng(36.971923, 127.935034), url: 'https://example.com/martialarts', info: '충주에서 열리는 세계적인 무술 축제입니다.' },
    { title: '천안 흥타령 춤축제', latlng: new kakao.maps.LatLng(36.815191, 127.113892), url: 'https://example.com/heungtaryeong', info: '천안에서 열리는 흥타령 춤축제입니다.' },
    { title: '아산 온천 축제', latlng: new kakao.maps.LatLng(36.789644, 127.004598), url: 'https://example.com/onsen', info: '아산에서 열리는 온천 축제입니다.' },
    { title: '청주 청남대 국화축제', latlng: new kakao.maps.LatLng(36.539825, 127.494962), url: 'https://example.com/cheongnamdae', info: '청주 청남대에서 열리는 국화축제입니다.' },
    { title: '공주 무령왕릉 축제', latlng: new kakao.maps.LatLng(36.455078, 127.124783), url: 'https://example.com/muryeong', info: '공주에서 열리는 무령왕릉 축제입니다.' },
    { title: '서산 개심사 꽃축제', latlng: new kakao.maps.LatLng(36.695159, 126.588663), url: 'https://example.com/gaesimsa', info: '서산 개심사에서 열리는 꽃축제입니다.' },
    { title: '보령 머드 축제', latlng: new kakao.maps.LatLng(36.320989, 126.528327), url: 'https://example.com/mudfestival', info: '보령에서 열리는 머드 축제입니다.' },
    { title: '단양 온달문화축제', latlng: new kakao.maps.LatLng(36.983423, 128.365458), url: 'https://example.com/ondal', info: '단양에서 열리는 온달문화축제입니다.' },
    { title: '태안 안면도 꽃지해변 축제', latlng: new kakao.maps.LatLng(36.518372, 126.331316), url: 'https://example.com/taean', info: '태안 안면도에서 열리는 꽃지해변 축제입니다.' },
    { title: '제천 청풍호 벚꽃축제', latlng: new kakao.maps.LatLng(37.004167, 128.144748), url: 'https://example.com/cheongpung', info: '제천 청풍호에서 열리는 벚꽃축제입니다.' },
    { title: '계룡산 천문대 별축제', latlng: new kakao.maps.LatLng(36.356161, 127.258088), url: 'https://example.com/gyeryong', info: '계룡산 천문대에서 열리는 별축제입니다.' },
    { title: '공주 금강둔치 벚꽃길 축제', latlng: new kakao.maps.LatLng(36.455995, 127.126336), url: 'https://example.com/geumgang', info: '공주에서 열리는 금강둔치 벚꽃길 축제입니다.' },
    { title: '홍성 남당항 대하축제', latlng: new kakao.maps.LatLng(36.566278, 126.634086), url: 'https://example.com/hongseong', info: '홍성 남당항에서 열리는 대하축제입니다.' },
    { title: '부여 서동 연꽃축제', latlng: new kakao.maps.LatLng(36.272884, 126.916590), url: 'https://example.com/seodong', info: '부여에서 열리는 서동 연꽃축제입니다.' },
    { title: '서산 해미읍성 축제', latlng: new kakao.maps.LatLng(36.766137, 126.515758), url: 'https://example.com/haemieupseong', info: '서산에서 열리는 해미읍성 축제입니다.' },
    { title: '논산 연무시장 소고기 축제', latlng: new kakao.maps.LatLng(36.201902, 127.087652), url: 'https://example.com/yeonmu', info: '논산 연무시장에서 열리는 소고기 축제입니다.' },
    { title: '청양 칠갑산 얼음분수 축제', latlng: new kakao.maps.LatLng(36.449416, 126.801570), url: 'https://example.com/chilgapsan', info: '청양 칠갑산에서 열리는 얼음분수 축제입니다.' },
    { title: '천안 유관순 열사 추모제', latlng: new kakao.maps.LatLng(36.810368, 127.112789), url: 'https://example.com/yugwan', info: '천안에서 열리는 유관순 열사 추모제입니다.' },
    { title: '당진 삽교호 물축제', latlng: new kakao.maps.LatLng(36.893246, 126.622016), url: 'https://example.com/sapgyo', info: '당진에서 열리는 삽교호 물축제입니다.' },
    { title: '서천 한산모시축제', latlng: new kakao.maps.LatLng(36.066277, 126.689262), url: 'https://example.com/hansan', info: '서천에서 열리는 한산모시 축제입니다.' },
    { title: '천안 독립기념관 대한민국 역사축제', latlng: new kakao.maps.LatLng(36.802609, 127.162361), url: 'https://example.com/dokrip', info: '천안 독립기념관에서 열리는 대한민국 역사축제입니다.' },
    { title: '청주 수암골 벽화축제', latlng: new kakao.maps.LatLng(36.636686, 127.490621), url: 'https://example.com/suamgol', info: '청주 수암골에서 열리는 벽화축제입니다.' },
    { title: '보은 대청호반 축제', latlng: new kakao.maps.LatLng(36.509942, 127.729402), url: 'https://example.com/daecheongho', info: '보은에서 열리는 대청호반 축제입니다.' },
    { title: '서천 홍원항 바지락 축제', latlng: new kakao.maps.LatLng(36.094834, 126.691085), url: 'https://example.com/hongwon', info: '서천 홍원항에서 열리는 바지락 축제입니다.' },
    { title: '춘천 막국수 축제', latlng: new kakao.maps.LatLng(37.881392, 127.729285), url: 'https://example.com/makguksu', info: '춘천에서 열리는 막국수 축제입니다.' },
    { title: '평창 대관령 눈꽃 축제', latlng: new kakao.maps.LatLng(37.671640, 128.737497), url: 'https://example.com/daegwallyeong', info: '평창에서 열리는 대관령 눈꽃 축제입니다.' },
    { title: '강릉 커피축제', latlng: new kakao.maps.LatLng(37.751853, 128.876057), url: 'https://example.com/gangneung', info: '강릉에서 열리는 커피 축제입니다.' },
    { title: '속초 해변가요제', latlng: new kakao.maps.LatLng(38.202768, 128.590728), url: 'https://example.com/sokcho', info: '속초 해변에서 열리는 가요제입니다.' },
    { title: '영월 별마로 천문대 축제', latlng: new kakao.maps.LatLng(37.187764, 128.461702), url: 'https://example.com/byeolmaro', info: '영월에서 열리는 별마로 천문대 축제입니다.' },
    { title: '홍천강 꽁꽁축제', latlng: new kakao.maps.LatLng(37.689864, 127.888900), url: 'https://example.com/hongcheongang', info: '홍천강에서 열리는 꽁꽁축제입니다.' },

    { title: '철원 얼음골 축제', latlng: new kakao.maps.LatLng(38.146661, 127.312032), url: 'https://example.com/cheorwon', info: '철원에서 열리는 얼음골 축제입니다.' },
    { title: '양양 송이축제', latlng: new kakao.maps.LatLng(38.075059, 128.619583), url: 'https://example.com/yangyang', info: '양양에서 열리는 송이 축제입니다.' },
    { title: '횡성 한우축제', latlng: new kakao.maps.LatLng(37.488515, 127.985628), url: 'https://gangwon.to/gwtour/now/festival?articleSeq=60707', info: '횡성에서 열리는 한우축제입니다.' },
    { title: '고성 통일염원 축제', latlng: new kakao.maps.LatLng(38.380697, 128.467224), url: 'https://example.com/goseong', info: '고성에서 열리는 통일염원 축제입니다.' },
    { title: '양구 배꼽축제', latlng: new kakao.maps.LatLng(38.108728, 127.989973), url: 'https://example.com/yanggu', info: '양구에서 열리는 배꼽축제입니다.' },
    { title: '태백 산눈축제', latlng: new kakao.maps.LatLng(37.164839, 128.985578), url: 'https://example.com/taebaek', info: '태백에서 열리는 산눈축제입니다.' },
    { title: '고성 해양낚시 축제', latlng: new kakao.maps.LatLng(38.380697, 128.467224), url: 'https://example.com/goseongfishing', info: '고성에서 열리는 해양낚시 축제입니다.' },
    
    { title: '삼척 대나무 축제', latlng: new kakao.maps.LatLng(37.445917, 129.165686), url: 'https://example.com/samcheokbamboo', info: '삼척에서 열리는 대나무 축제입니다.' },
    { title: '홍천 찰옥수수 축제', latlng: new kakao.maps.LatLng(37.690358, 127.888638), url: 'https://example.com/hongcheoncorn', info: '홍천에서 열리는 찰옥수수 축제입니다.' },
    { title: '동해 오징어축제', latlng: new kakao.maps.LatLng(37.547072, 129.115318), url: 'https://example.com/squidfestival', info: '동해에서 열리는 오징어축제입니다.' },
    { title: '제주 감귤 축제', latlng: new kakao.maps.LatLng(33.471733, 126.498846), url: 'https://example.com/jejumandarinfestival', info: '제주에서 열리는 감귤 축제입니다.' },
    { title: '서귀포 유채꽃 국제 걷기대회', latlng: new kakao.maps.LatLng(33.252069, 126.510007), url: 'https://example.com/seogwipoyuchae', info: '서귀포에서 열리는 유채꽃 국제 걷기대회입니다.' },
    { title: '제주들불 축제', latlng: new kakao.maps.LatLng(33.477723, 126.456749), url: 'https://example.com/jejudulbul', info: '제주에서 열리는 들불 축제입니다.' },
    { title: '성산일출봉 축제', latlng: new kakao.maps.LatLng(33.458517, 126.940728), url: 'https://example.com/seongsan', info: '성산일출봉에서 열리는 축제입니다.' },
    { title: '한림공원 벚꽃축제', latlng: new kakao.maps.LatLng(33.394521, 126.265344), url: 'https://example.com/halimpark', info: '한림공원에서 열리는 벚꽃축제입니다.' },
    { title: '우도 마라도 청정해변 축제', latlng: new kakao.maps.LatLng(33.502034, 126.954844), url: 'https://example.com/udomarado', info: '우도와 마라도에서 열리는 청정해변 축제입니다.' },
    { title: '제주 허브동산 라벤더 축제', latlng: new kakao.maps.LatLng(33.325065, 126.784805), url: 'https://example.com/herbfestival', info: '제주 허브동산에서 열리는 라벤더 축제입니다.' },
    { title: '서귀포 칠십리 해안도로 걷기대회', latlng: new kakao.maps.LatLng(33.249611, 126.513503), url: 'https://example.com/seogwipo70ri', info: '서귀포 칠십리 해안도로에서 열리는 걷기대회입니다.' },
    { title: '제주 돌문화 공원 축제', latlng: new kakao.maps.LatLng(33.480498, 126.516327), url: 'https://example.com/dolmunhwagongwon', info: '제주 돌문화 공원에서 열리는 축제입니다.' },
    { title: '제주 유네스코 세계지질공원 축제', latlng: new kakao.maps.LatLng(33.462048, 126.550653), url: 'https://example.com/unescojeju', info: '제주에서 열리는 유네스코 세계지질공원 축제입니다.' },
    { title: '제주 자연사랑 건강걷기 대회', latlng: new kakao.maps.LatLng(33.490288, 126.498006), url: 'https://example.com/jejunature', info: '제주에서 열리는 자연사랑 건강걷기 대회입니다.' },
    { title: '제주 용암동굴 축제', latlng: new kakao.maps.LatLng(33.509046, 126.554175), url: 'https://example.com/jejuvolcanocave', info: '제주 용암동굴에서 열리는 축제입니다.' },
    { title: '제주 비자림 축제', latlng: new kakao.maps.LatLng(33.484892, 126.600657), url: 'https://example.com/jejuvija', info: '제주 비자림에서 열리는 축제입니다.' },
    { title: '한라산 봄꽃 축제', latlng: new kakao.maps.LatLng(33.361678, 126.528371), url: 'https://example.com/hallaflower', info: '한라산에서 열리는 봄꽃 축제입니다.' },
    { title: '제주 바다길 걷기대회', latlng: new kakao.maps.LatLng(33.495951, 126.496128), url: 'https://example.com/searoadjeju', info: '제주에서 열리는 바다길 걷기대회입니다.' },
]

  
    // 마커를 생성하고 클러스터러에 추가합니다.
    positions.forEach((position) => {
      const marker = new kakao.maps.Marker({
        position: position.latlng,
        title: position.title,
      });
  
      // 마커에 클릭 이벤트 추가하여 인포윈도우 표시
      kakao.maps.event.addListener(marker, 'click', () => {
        showInfoWindow(marker, position.title, position.url);
      });
  
      markers.value.push(marker);
    });
  
    // 클러스터러에 마커들 추가
    markerClusterer.value.addMarkers(markers.value);
  
    // 지도 이벤트 리스너 등록
    kakao.maps.event.addListener(map.value, 'zoom_changed', updateZoomLevel);
    kakao.maps.event.addListener(map.value, 'zoom_changed', updateMarkers);
  };
  
  const showInfoWindow = (marker, title, url) => {
    const content = `
      <div style="padding:5px;font-size:12px;">
        <a href="${url}" target="_blank" style="color:blue;text-decoration:underline;">${title}</a>
      </div>`;
    infowindow.setContent(content);
    infowindow.open(map.value, marker);
  };
  
  const updateZoomLevel = () => {
    zoomLevel.value = map.value.getLevel();
  };
  
  const updateMarkers = () => {
    const bounds = map.value.getBounds();
  
    markers.value.forEach((marker) => {
      if (bounds.contain(marker.getPosition())) {
        marker.setMap(map.value);
      } else {
        marker.setMap(null);
      }
    });
  
    // 마커를 클러스터러에 다시 추가
    markerClusterer.value.clear();
    markerClusterer.value.addMarkers(markers.value);
  };
  
  onMounted(() => {
    initializeMap();
  });
  </script>
  
  <style>
  #map {
    width: 100%;
    height: 500px;
  }
  </style>
  
<template>
  <div id="category" class="container">
    <!-- <div class="banner"></div>
    <div class="bannerText">
      WETUBE 내의 인기있는 카테고리를 한눈에 보세요.<br>
      간편한 클릭으로 내가 보고싶은 컨텐츠를 확인할 수 있습니다.<br>
    </div> -->
     <img class="categoryHedaer" src="../../assets/img/categoryHeader.png" />
    <div class="search_write">
      <button class="menuLink" @click="moveListWithProp('스포츠')">
        <img src="../../assets/img/category/sport.png" alt="스포츠"/>
        <p class="category">스포츠</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('유머')">
        <img src="../../assets/img/category/humor.png" alt="유머"/>
        <p class="category">유머</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('브이로그')">
        <img src="../../assets/img/category/vlog.png" alt="브이로그"/>
        <p class="category">브이로그</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('게임')">
        <img src="../../assets/img/category/game.png" alt="게임"/>
        <p class="category">게임</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('사회')">
        <img src="../../assets/img/category/society.png" alt="사회"/>
        <p class="category">사회</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('음악')">
        <img src="../../assets/img/category/music.png" alt="음악" />
        <p class="category">음악</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('연예')">
        <img src="../../assets/img/category/entertainment.png" alt="연예"/>
        <p class="category">연예</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('영화')">
        <img src="../../assets/img/category/movie.png" alt="영화"/>
        <p class="category">영화</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('만화')">
        <img src="../../assets/img/category/cartoon.png" alt="만화" />
        <p class="category">만화</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('연애')">
        <img src="../../assets/img/category/love.png" alt="연애"/>
        <p class="category">연애</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('음식')">
        <img src="../../assets/img/category/food.png" alt="음식"/>
        <p class="category">음식</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('공부')">
        <img src="../../assets/img/category/study.png" alt="취업/공부"/>
        <p class="category">공부</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('디지털')">
        <img src="../../assets/img/category/IT.png" alt="디지털/IT"/>
        <p class="category">디지털</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('패션')">
        <img src="../../assets/img/category/fashion.png" alt="패션"/>
        <p class="category">패션</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('군대')">
        <img src="../../assets/img/category/military.png" alt="군대" />
        <p class="category">군대</p>
      </button>
      <button class="menuLink" @click="moveListWithProp('건강')">
        <img src="../../assets/img/category/health.png" alt="건강"/>
        <p class="category">건강</p>
      </button>
    </div>
  </div>
</template>


<script>
import "../../assets/css/post.scss";

export default {
  name: "Category",
  methods: {
    moveListWithProp(data) {
      this.$router.push({ name: "search", params: { category: data } });
    },
  },
};
</script>

<style>
/* .taglist {
  width: 230px;
  height: 300px;
  margin-right: 0;
} */
/* .button-up img {
  width: 48px;
  height: 48px;
  margin-top: -80px;
} */
.menuLink {
  margin: 10px;
}
/* .category {
  width: 200px;
  margin-bottom: 5px;
} */
.banner{
/* background-color: black;  */
/* background-image: url('../../assets/img/wetubeBanner.png');  */
width:140%;
height: 400px;
margin-top: -60px;
margin-left: -205px;
margin-bottom: 30px;
background-size : cover;
opacity: 0.7;
z-index: 0;
}
.bannerText{
    padding:15px;
    position: absolute;
    top:210px;
    left: 200px;
    font-size : 36px; 
    font-family: 'Do Hyeon', sans-serif;
    /* font-weight: bold;
    font-size: 25px; */
     font-weight: bold;
    /* font-family: 'NEXON Lv1 Gothic OTF'; */
    color: rgb(5, 5, 5);
    background-color: whitesmoke;
    opacity: 0.9;
    border-radius: 10px;   
 z-index: 3;
}
@import url("https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap");
</style>

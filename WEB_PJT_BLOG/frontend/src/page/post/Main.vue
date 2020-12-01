<template>
<div>
    <div class ="banner">
        
    </div>
    <div class="bannerText">
      <div class="bannerInput">
            어서오세요, 우리들의 영상리뷰 WeTube 입니다.<br>
            관심있는 유튜브 영상과 리뷰를 올리고 다른사람과 공유해보세요!<br>
<!-- 다른사람들과 의견을 나누고 관심있는 리뷰에 좋아요를 눌러 관심을 표시해주세요.<br> -->
      </div>
        <!-- <TagList class="mainTag"/> -->
    </div>
    <h2>
        인기동영상
        <!-- <button  class="write" v-if="this.$store.state.isLogged" @click="moveWrite">글작성</button> -->
    </h2>
     
   
    <div class="popular">
     <div class="row row-cols-1 row-cols-sm-2 row-cols-md-2 row-cols-lg-3 row-cols-xl-3 mainCard">
               <Card v-for="board in boards" :board="board" :key="board.id"/> 
    </div>
    </div>
</div>

</template>

<script>
import '../../assets/css/post.scss'
import TagList from '../../components/common/TagList'
import Card from '../../components/common/Card'
import axios from 'axios'
import constants from '../../lib/constants'
export default {
      components:{
        TagList,
        Card
      },
     data: () => {
        return {
            boards: [],
              offset: 0,
              keyword:'',
              filter:''
        }
    },
    methods: {

        getBoards: function() {
    
          this.offset+=3
          axios.get(constants.baseUrl+'/boards/recommendation')
            .then(res => {
              if(res.data.length == 0){// 받아온게 없으면 flag를 false로 바꿔서 동작안하도록
                this.IsDataLeft=false;
                this.loading=false;
              }else{
                this.IsDataLeft=true;
                this.loading=true;
              this.boards = [...this.boards, ...res.data]
              }
            })
            .catch(err => console.error(err))
        },
          moveWrite: function(){
          this.$router.push('/boardCreate')
        }
    },
     created: function () {
      this.getBoards()
    },
    
}
</script>
<style scoped>
.banner{
/* background-color: black;  */
background-image: url('../../assets/img/wetubeBanner.png'); 
margin-left: 0px;
width:100%;
height: 400px;
margin-top: -60px;
background-size : cover;
opacity: 0.7;
z-index: 0;
}

.popular{
    margin-top: 30px;
    margin-left: 270px;
    margin-right: 30px;
}
.bannerText{
    padding:17px 25px;
    position: absolute;
    top:200px;
    left: 350px;
    font-size : 28px; 
    font-weight: 22;
    font-family: 'NEXON Lv1 Gothic OTF';
    height: 160px;
    color: rgb(5, 5, 5);
    background-color: whitesmoke;
    opacity: 0.8;
    border-radius: 10px;   
    z-index: 3;

}
.bannerInput{
  margin-top: 25px;
}
h2{
    margin-top: 90px;
    margin-bottom: 40px;
    padding-left: 45%;
    font-weight: bold;
    font-size: 45px;
    font-family: 'Do Hyeon', sans-serif;
}

 .mainTag{
    margin-left: -100px;
 }
 
  

  
  
  
</style>
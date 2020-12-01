<template>
    <div id="list" class="container">
              <TagList @getTag= "getBoardsBySearch" class="mainTag"/>
      <div v-if = "this.category==null" class="search_write">
        <!-- <div style="width: 300px"> -->
          <SearchBar @search = "getBoardsBySearch"/>
        <!-- </div> -->
       
      </div>
      <div class="row">
          <div class="col-12">
                  <h2 class="categoryText" v-if = "this.category!=null">{{this.category}} 카테고리</h2>
            <hr>
            <div class="row row-cols-1 row-cols-sm-2 row-cols-md-2 row-cols-lg-3 row-cols-xl-3">
              <div v-if="boards.length==0">
               <img class="emptyIcon" src="../../assets/img/zero.png">
              <span class="emptyText" v-if="this.category==null" >검색결과에 맞는 게시글이 존재하지 않습니다.</span>
              <span class="emptyText" v-else >해당 카테고리에 게시글이 존재하지 않습니다.</span>
              </div>
               <Card v-for="board in boards" :board="board" :key="board.id"/> <!--값을 card.vue에 넘겨서 card 생성 -->
            </div>
          </div>
           <div class="col-3 d-none d-md-block taglist"> 
              <button @click="scrollToTop" class="button-up"><img src ="../../assets/img/up.png"></button>
           </div> 
      </div>
      
      <div v-if="loading" class="col-5 container"><img src ="../../assets/img/loadingCustom.gif"></div> 
      <div id="bottomSensor">.</div> <!-- 하단 센서(인피니티 스크롤) -->
   </div>
</template>


<script>
import '../../assets/css/post.scss'
import Card from '../../components/common/Card.vue'
import SearchBar from '../../components/common/SearchBar.vue'
import TagList from '../../components/common/TagList.vue'
import axios from 'axios'
import constants from '../../lib/constants'

export default {
    name:"Post",
    props: ["category"],
    components:{
        Card,
        TagList,
        SearchBar
    },
    data: () => {
        return {
            boards: [],
            page: 1,
            offset: 0,
            IsDataLeft: true,
            loading : true,
            filter:'',
            keyword:''
        }
    },
    methods: {
        getBoards: function() {
          const options = {
            params: { 
              offset: this.offset,
              limit: 3,
              filter:this.filter,
              keyword:this.keyword
            }
          }
          this.offset+=3
          axios.get(constants.baseUrl+'/boards', options)
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
        addScrollWatcher: function () { // 무한 스크롤을 위해 기준이 될 영역을 설정하는 메서드
          const bottomSensor = document.querySelector('#bottomSensor')
          const watcher = scrollMonitor.create(bottomSensor) // bottomsensor를 기준영역으로 설정
          watcher.enterViewport(() => { //현재 뷰포트에 들어오면
          if(this.IsDataLeft){
            setTimeout(() => { // 0.5초 지난후에 getBoards() 호출
              this.getBoards()
            }, 500)
            
          }
          })
        },
        loadUntilViewportIsFull: function () { 
          const bottomSensor = document.querySelector('#bottomSensor')
          const watcher = scrollMonitor.create(bottomSensor)
          if (watcher.isFullyInViewport) {
             setTimeout(() => { // 0.5초 지난후에 getBoards() 호출
              this.getBoards()
            }, 500)
          }
        },
        scrollToTop: function () {
          scroll(0, 0)
        },
        getBoardsBySearch: function(data) { // 검색 & 인기태그 검색 
          this.keyword=data.keyword
          this.filter=data.filter
          this.loading=true
          this.offset=0
          this.boards=[]
          this.getBoards()
          
        }
    },
  // created: 초기화 이후 AJAX 요청을 보내기 좋은 시점(Data, Methods 에 접근 가능.)
    created: function () {
      if(this.category!=null){
          this.keyword=this.category+""
          this.filter="tag"
          this.loading=true
          this.offset=0
          this.boards=[]
          this.getBoards()
      }
      else{
        this.getBoards()
      }
    },

    // mounted: DOM 과 Vue 인스턴스가 연동이 완료되고 난 이후에 실행할 일들.
    mounted: function() {
      this.addScrollWatcher()
    },
    
    // updated: data({}) 가 바뀌고 나서, 화면이 다시 렌더된 이후,
    updated: function() {
      //if(this.IsDataLeft) // 받아올게 있는경우만 동작
        this.loadUntilViewportIsFull()
    },
}
</script>

<style>
.button-up img{
    width: 48px;
    height: 48px;
    margin-top: -80px
}

.categoryText{
  margin-left : -114px;
  margin-bottom: -10px;
  font-weight: bold;
  font-family: 'Do Hyeon', sans-serif;
}
.emptyIcon{
            position:absolute;
            top:100px;
            left:270px;
            width: 250px;
            height: 250px;

}
.emptyText{
           position:absolute;
            top:360px;
            left:160px;
            font-family: 'NEXON Lv1 Gothic OTF';
            font-size: 25px;

}
@import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap');
</style>

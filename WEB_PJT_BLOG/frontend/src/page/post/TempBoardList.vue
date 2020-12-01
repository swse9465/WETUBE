<template>
  <div id="temp" class="container">
    <div class="tempHeader">
      <img src="../../assets/img/tempHeader.png" />
      <p>임시보관함</p>
    </div>
    <div class="row">
      <div class="col-12">
        <button @click = "deleteSelected()" class="btn-delete">삭제</button>
        <button @click = "selectCancel()"  class="btn-selectAll">선택취소</button>
        <button @click = "selectAll()"  class="btn-selectAll">전체선택</button>
       
        <hr>
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-2 row-cols-lg-3 row-cols-xl-3">
          <div class="tempNo" v-if ="IsEmpty">
             <img class="zeroIcon" src="../../assets/img/zero.png">
             <span>임시저장된 게시글이 없습니다.</span>
          </div>
        <!-- <TempCard  class="boardCard" :selectAll="all" v-for="board in boards" :board="board" :key="board.id" /> -->
          <div id="card" class="col boardCard"  v-for="board in boards"  :key="board.id">
         <input type="checkbox" :id="'check'+ board.boardId" :key="board.id" :checked= all >
         <div class="card h-100">
            <div class="card-top">
            <!-- <img class="viewsIcon" src="../../assets/img/viewsIcon.png"> -->
            
          <img @click="boardDetailPush(board.boardId)" :src="'https://img.youtube.com/vi/' + board.url + '/0.jpg'" class="card-img-top" alt="thumnail_img" /> 
      </div>
     <div class="card-body">
        <h6 @click="boardDetailPush()" class="card-title">{{ board.subject }}</h6>
        <div class="row justify-content-around">
          <div class="col-3 align-self-right">
            <img v-if="board.user.imgSrc" class="user_img user_profile" :src="board.user.imgSrc" />
            <img v-else class="user_img" src="../../assets/img/BasicProfileIcon.svg" />
          </div>
          <div class="col-9 align-self-left nickname">{{board.nickname}}</div>
          <div class="row cols-15 wrap-date-like">
            <div class="cols-10 align-self-left date">{{ date(board.createDate) }}</div>
            <div class="cols-10 align-self-right">
              <div class="wrap-like" style="width:70px">
                <img
                  v-if="likeFlag"
                  @click="likeDeleteHandler()"
                  class="like"
                  src="../../assets/img/heart2.png"
                />
                <img
                  v-else
                  @click="likePostHandler()"
                  class="like"
                  src="../../assets/img/heart.png"
                />
                <!-- <span>{{ likeCount }}</span> -->
              </div>
            </div>
          </div>
        </div>
      </div> 
    </div>
  </div>
          <!--값을 card.vue에 넘겨서 card 생성 -->
        </div>
      </div>
    </div>
    <button @click="scrollToTop" class="button-up">
      <img src="../../assets/img/up.png" />
    </button>
  </div>
</template>


<script>
import "../../assets/css/post.scss";
/* import TempCard from "../../components/common/TempCard";  */
import axios from "axios";
import constants from "../../lib/constants";
/* import "url-search-params-polyfill"; */

export default {
  name: "Post",
  components: {
   /*  TempCard */
  },
  data: () => {
    return {
      boards: [],
      IsEmpty : false,
      all : '',
      likeFlag :false
    };
  },
  computed: {
    time() {
      return this.board.createDate.slice(11);
    },
    
    url() {
      return "https://img.youtube.com/vi/" + this.board.url + "/0.jpg";
    }
  },
  methods: {
    getBoards: function () {
      const options = {
        params: {
          status: 0,
        },
      };
      axios
        .get(constants.baseUrl + "/boards/" + this.$store.state.userId, options)
        .then((res) => {
          if (res.data.length == 0) {
            // 받아온게 없으면 flag를 false로 바꿔서 동작안하도록
            this.IsEmpty=true;
          } else {
            this.IsEmpty=false;
            this.boards = [...this.boards, ...res.data];
          }
        })
        .catch((err) => console.error(err));
    },

    scrollToTop: function () {
      scroll(0, 0);
    },
    date(createDate) {
     
      var year = createDate.slice(0, 4);
      var month = createDate.slice(5, 7);
      var day = createDate.slice(8, 10);
      return year + "-" + month + "-" + day;
    },

    deleteSelected:function(){
 

    var ncount =0;

      for (let i = 0; i < this.boards.length; i++) {

        let tmpCheck = document.getElementById('check'+this.boards[i].boardId).checked;
        if(tmpCheck){
          ncount++
          var boardId = this.boards[i].boardId -'0';
         
          axios
        .delete(constants.baseUrl + "/tempboards/"+boardId,
         {
          headers: { accesstoken: this.$store.state.accesstoken 
          }
        
      
      }
       
        
        )
        .then(({ data }) => {
        
        })
        .catch(function (error) {
            console.log(error);
            return;
        });
          
        }

      }
       if(ncount==0){
         alert("삭제할 게시글을 선택해주세요!")
        
       }  
     else{
      alert("삭제완료")
      window.location.reload()
     }
                
    },
    selectAll:function(){
        this.all='checked';
    },
    selectCancel:function(){
        window.location.reload();
      
    },
    boardDetailPush(boardId) {
      this.$router.push("/board?boardid=" +boardId);
    },
    
  },
  // created: 초기화 이후 AJAX 요청을 보내기 좋은 시점(Data, Methods 에 접근 가능.)
  created: function () {
    this.getBoards();
  },
};
</script>

<style scoped>
p {
  text-align: center;
  position: absolute;
  top: 230px;
  right: 650px;

  /* transform: translate( -50%, 50% );  */
  font-family: "Black Han Sans", sans-serif;
  font-size: 52px;
}
.tempBoardDiv{
  width:720px;
  margin:0;
  padding: 0;

/*   display: inline-block; */


}
</style>

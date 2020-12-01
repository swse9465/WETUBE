<template>
  <div id="card" class="col">
    <input v-if = "this.$route.name ==='TempBoardList'" type="checkbox">
    <div class="card h-100">
        <div class="card-top">
            <!-- <img class="viewsIcon" src="../../assets/img/viewsIcon.png"> -->
            <span class="viewsText">조회수 : {{board.views}}</span>
            <img @click="boardDetailPush()" :src="url" class="card-img-top" alt="thumnail_img" />
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
            <div class="cols-10 align-self-left date">{{ date }}</div>
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
                <span>{{ likeCount }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>  
<script>
import "../../assets/css/post.scss";
import constants from "../../lib/constants";
import axios from "axios";
import "url-search-params-polyfill";
export default {
  props: {
    board: Object,
  },
  computed: {
    time() {
      return this.board.createDate.slice(11);
    },
    date() {
      /* var format = this.board.createDate.format("MM월 dd, yyyy"); */
      // return this.board.createDate.slice(0,10)
      var year = this.board.createDate.slice(0, 4);
      var month = this.board.createDate.slice(5, 7);
      var day = this.board.createDate.slice(8, 10);
      return year + "-" + month + "-" + day;
    },
    url() {
      return "https://img.youtube.com/vi/" + this.board.url + "/0.jpg";
    },
  },
  created() {
    if (this.$store.state.userId) {
      axios
        .get(constants.baseUrl + "/likeExist", {
          params: {
            boardId: this.board.boardId,
            userId: this.$store.state.userId,
          },
        })
        .then(({ data }) => {
          this.likeFlag = data;
        })
        .catch(function (error) {
          if (error.response) {
            // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            // 요청이 이루어 졌으나 응답을 받지 못했습니다.
            // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
            // Node.js의 http.ClientRequest 인스턴스입니다.
            console.log(error.request);
          } else {
            // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
            console.log("Error", error.message);
          }
          console.log(error.config);
        });
    }
  },
  data() {
    return {
      likeFlag: false,
      likeCount: this.board.likes,
    };
  },
  methods: {
    boardDetailPush() {
      this.$router.push("/board?boardid=" + this.board.boardId);
    },
    likePostHandler() {
      var params = new URLSearchParams();
      params.append("boardId", this.board.boardId);
      params.append("userId", this.$store.state.userId);
      axios
        .post(constants.baseUrl + "/like", params, {
          headers: { accesstoken: this.$store.state.accesstoken },
        })
        .then(({ data }) => {
          console.log(data);
          this.likeFlag = !this.likeFlag;
          this.likeCountHandler();
        })
        .catch(function (error) {
          if (error.response) {
            // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            // 요청이 이루어 졌으나 응답을 받지 못했습니다.
            // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
            // Node.js의 http.ClientRequest 인스턴스입니다.
            console.log(error.request);
          } else {
            // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
            console.log("Error", error.message);
          }
          console.log(error.config);
        });
    },
    likeDeleteHandler() {
      axios
        .delete(constants.baseUrl + "/like", {
          params: {
            boardId: this.board.boardId,
            userId: this.$store.state.userId,
          },
          headers: { accesstoken: this.$store.state.accesstoken },
        })
        .then(({ data }) => {
          this.likeFlag = !this.likeFlag;
          this.likeCountHandler();
        })
        .catch(function (error) {
          if (error.response) {
            // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            // 요청이 이루어 졌으나 응답을 받지 못했습니다.
            // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
            // Node.js의 http.ClientRequest 인스턴스입니다.
            console.log(error.request);
          } else {
            // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
            console.log("Error", error.message);
          }
          console.log(error.config);
        });
    },
    likeCountHandler(){
      axios
      .get(constants.baseUrl + '/like?boardId=' + this.board.boardId)
      .then(({ data }) => {
        this.likeCount = data;
      });
    }
  },
};
</script>

<style>
    .card {
        cursor: pointer;
    }
    .card:hover{
      box-shadow: 8px 8px 10px #ffb3b3;
    }
    .user_profile{
        width:33.6px;
        height: 33.6px;
        border-radius: 100%;
    }
    .viewsIcon{
       position: absolute;
       width:30x;
       height: 30px;
       top:0;
       right:48px;
    }
    .viewsText{
        position: absolute;
        top:0;
        right:10px; 
        color:white;
        font-size: 15px;
        margin-top: 5px
    }

@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap");
            @font-face {
    font-family: 'NEXON Lv1 Gothic OTF';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
</style>
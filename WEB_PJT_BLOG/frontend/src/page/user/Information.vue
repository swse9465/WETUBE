<template>
  <div class="user" id="information">
    <div class="wrapC table">
      <div class="form-wrap container">
        <div class="profileImg">
          <input ref="imageInput" type="file" hidden @change="changeIamge">
          <img id="profileImg" v-if="imgSrc" :src="imgSrc">
          <img id="basicImg" v-else src="../../assets/img/BasicProfileIcon.svg" />
          <!-- <img src="../../assets/img/delete.png"/> -->
          <div id="btn-two">
          <button @click="updateImage"><img src="../../assets/img/add.png" id="add"/></button>
          <button @click="removeImage"><img src="../../assets/img/delete.png" id="remove"/></button>
          </div>
        </div>

        <div class="row">
          <!-- <h6>닉네임</h6> -->
          <input v-model="nickname" id="nickname" ref="nickname" type="text" />
        </div>

        <div class="row">
          <!-- <h6>이메일</h6> -->
          <input v-model="email" id="email" ref="email" type="text" disabled/>
        </div>

          <div class="row">
          <!-- <h6>자기소개</h6> -->
          <textarea 
            v-model="introduce"
            id="introduce"
            type="text"
            rows="2"
            cols="47"
            style="border:1px solid black;"
            placeholder="자기소개글 입력해주세요"
          />
        </div>

        <!-- <div class="input-wrap password-wrap">
          <h6>비밀번호</h6>
          <input v-model="password" id="password" :type="passwordType" />
          <span :class="{active : passwordType==='text'}">
            <i class="fas fa-eye"></i>
          </span>
        </div> -->
         <button class="btn btn-primary btn-remove" @click="deleteHandler">탈퇴</button>
         <button class="btn btn-primary btn-modify" @click="updateHandler">수정</button>
      </div>
    </div>

    <div id="tablist">
      <b-tabs content-class="mt-3" justified>
        <b-tab title="내가 사용한 태그" active>
          <GChart v-if="chartData.length > 1" id="GChart" type="PieChart" :data="chartData" :options="chartOptions"/>
          <div v-else> 
          <img class="hashtag" src="../../assets/img/hashtag.png"/>
          <p>사용한 태그가 없습니다.</p>
          <p class="hash">해시태그를 추가하여 확인해보세요</p>
          </div> 
        </b-tab>
        <b-tab title="내가 작성한 글">
          <table class="list" v-if="wroteList.length != 0">
            <tr>
              <th>글 번호</th>
              <th>제목</th>
              <th>댓글 수</th>
              <th>좋아요 수</th>
              <th>작성일자</th>
            </tr>
            <tr class="write_list" v-for="(post, index) in wroteList" :key="index">
              <td>{{index+1}}</td>
              <td @click="move(post.boardId)"  class="title">{{post.subject}}</td>
              <td>{{post.replies}}</td>
              <td>{{post.likes}}</td>
              <td>{{post.createDate}}</td>
            </tr>
          </table>
          <div v-else>
            <img class="write" src="../../assets/img/paper.png"/>
            <p>작성한 글이 없습니다.</p>
            <p>자신만의 리뷰를 작성하여 공유해보아요</p>
          </div>
        </b-tab>
        <b-tab title="좋아하는 글">
          <table class="list" v-if="likedList.length != 0">
            <tr>
              <th>글 번호</th>
              <th>제목</th>
              <th>댓글 수</th>
              <th>좋아요 수</th>
              <th>작성일자</th>
            </tr>
            <tr class="like_list" v-for="(post, index) in likedList" :key="index">
              <td>{{index+1}}</td>
              <td @click="move(post.boardId)" class="title">{{post.subject}}</td>
              <td>{{post.replies}}</td>
              <td>{{post.likes}}</td>
              <td>{{post.createDate}}</td>
            </tr>
          </table>
          <div v-else>
            <img class="like" src="../../assets/img/like_write.png"/>
            <p>좋아하는 글이 없습니다.</p>
            <p class="second">마음에 드는 리뷰에 좋아요를 눌러 저장해보아요</p>
          </div>
        </b-tab>
      </b-tabs>
    </div>
        <div class="empty" style="height: 180px"></div>

  </div>
</template>

<script>
import axios from "axios";
import constants from "../../lib/constants";
import config from "../../lib/FireBaseConfig";
import firebase from "firebase/app"; // 파이어베이스 import 
import "firebase/storage";
import {GChart} from "vue-google-charts"; // 구글 차트 API

// Initialize Firebase
firebase.initializeApp(config.apiKey);

export default {
  name: "Update",
  components:{
    GChart
  },
  data: function () {
    return {
      nickname: "",
      email: "",
      introduce: "",
      password: "",
      passwordType: "password",
      file: "",
      imgSrc: "",
      originSrc: "",
      chartData: [["Tags","Rating"]],
      chartOptions: {
          pieHole: 0.4,
          // 윗 color: 명도 변화,  아래 color: 채도 변화
          colors: ['#b30000','#cc0000','#e60000','#ff0000', '#ff1a1a','#ff3333','#ff4d4d','#ff6666','#ff8080','#ff9999','#ffb3b3','#ffcccc','#ffe6e6'],
          // colors: ['#ff0000','#f90606','#f20d0d','#ec1313','#e61919','#df2020','#d92626','#d22d2d','#cc3333','#c63939','#bf4040','#b94646','#b34d4d','#ac5353','#a65959','#9f6060','#996666']
      },
      wroteList:"",
      likedList:""
    };
  },
  methods: {
    updateHandler() {
      // 루트 경로기준에서 profileImage이라는 폴더에 user + userid 파일명으로 이미지를 넣어라
      var profileRef = firebase.storage().ref().child("profileImage/user"+this.$store.state.userId);

      if(!this.imgSrc || this.imgSrc==null){ // 이미지가 없는 경우
        this.imgSrc=null;
        profileRef.delete().then(function(){}).catch(function(err){console.log("해당되는 이미지가 없습니다.")})
      }else if(this.imgSrc!=this.originSrc){
        this.imgSrc = "https://firebasestorage.googleapis.com/v0/b/webblog-884ea.appspot.com/o/profileImage%2Fuser"+this.$store.state.userId
        +"?alt=media"; 
        profileRef.put(this.file).then(function(snapshot){}); // 파이어베이스 스토리지에 저장
      }

      axios
        .put(constants.baseUrl + "/account", {
          userId : this.$store.state.userId,
          nickname: this.nickname,
          introduce: this.introduce,
          imgSrc: this.imgSrc,
        },{ headers : {'accesstoken':this.$store.state.accesstoken} }) // 토큰 인증을 위해 헤더에 내용 추가
        .then(({ data }) => {
          // location.reload();
          // this.$router.go(name="constants.URL_TYPE.POST.MAIN"); 
          this.$store.state.nickname= this.nickname;
          alert("수정 완료되었습니다.")
          this.$router.go("/user/Inforamtion"); 
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
    deleteHandler() {
      if(this.originSrc){ // 원래 스토리지에 있던 경우에 삭제 수행
        var profileRef = firebase.storage().ref().child("profileImage/user"+this.$store.state.userId);
        profileRef.delete().then(function(){}).catch(function(err){console.log("해당되는 이미지가 없습니다.")});
      }

      axios
        .delete(constants.baseUrl + "/account/" + this.$store.state.userId
        ,{ headers : {'accesstoken':this.$store.state.accesstoken} })
        .then(({ data }) => {
          this.$store.commit("LOGOUT");
          this.$router.push("/");
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
    updateImage() {
      this.$refs.imageInput.click();
    },
    changeIamge(e){
      if(e.target.files.length==0) // 사진 등록 안하면 그냥 종료
        return;
      this.file = e.target.files[0]; // 파일을 가져옴
      this.imgSrc = URL.createObjectURL(this.file); // 미리보기용으로 url 생성
    },
    removeImage(){
      this.imgSrc=null;
      this.$refs.imageInput.value="";
    },
    getWrote(){
      const options = {
        params: { 
          status: 1,
        }
      }
      axios.get(constants.baseUrl +"/boards/"+this.$store.state.userId, options)
      .then(({data}) =>{
        this.wroteList = data;
        this.date(this.wroteList);
      })
    },
    getLiked(){
      axios.get(constants.baseUrl +"/boards/like/"+this.$store.state.userId)
      .then(({data}) =>{
        this.likedList = data;
        this.date(this.likedList);
      })
    },
    date(list){
      list.forEach(e => {
        let date = e.createDate.slice(0, 10);
        let time = e.createDate.slice(11,16);
        e.createDate = date + " " + time; 
      });
    },
    move(boardId){
      console.log("/board?boardid="+boardId);
      this.$router.push("/board?boardid="+boardId);
    }
  },
  created() {
    axios
      .get(constants.baseUrl + "/account/" + this.$store.state.userId)
      .then(({ data }) => {
        this.nickname = data.nickname;
        this.email = data.email;
        this.introduce = data.introduce;
        this.password = data.password;
        this.imgSrc = data.imgSrc;
        this.originSrc = data.imgSrc; // 회원 탈퇴시 이미지 삭제 여부 판단용
      });

    axios.get(constants.baseUrl+"/tags/" + this.$store.state.userId)
    .then(({data}) => {
      data.forEach(e => {
        this.chartData.push([e.tagName, e.count]);
      });
    })

    this.getWrote();
    this.getLiked();
  },
};
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');
.list{
  width: 100%;
  text-align: center;
}
.list th{border-bottom: 2px solid lightgray;}
td{border-bottom: 1px solid lightgray;}
.title{cursor: pointer; white-space: nowrap;} 
.title:hover{color: #ffb3b3;}
</style>
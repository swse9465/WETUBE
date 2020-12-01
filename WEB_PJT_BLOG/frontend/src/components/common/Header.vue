<template>
  <nav id="header" class="navbar navbar-light justify-content-between" v-if="isHeader">
    <div class="main" @click="moveMain()">
      <img
        class="logo"
        src="https://lab.ssafy.com/s03-webmobile1-sub2/s03p12a302/uploads/63679883087144683bd8e380816ef481/youtube-video-social-media-play_icon-icons.com_59108__1_.png"
      />
      <div>WETUBE</div>
    </div>

    <div class="menu">
      <button class="search-btn" @click="moveSearch">검색</button>
      <button class="category-btn" @click="moveCategory">인기카테고리</button>
    </div>

    <div class="right">
      <div v-if="this.$store.state.isLogged">
        <button class="write-btn"  @click="moveWrite"  v-if="this.$store.state.isLogged" >
          <img src="../../assets/img/boardWrite.png" style="width:30px;height:30px;">
        </button>
        <button class="user-btn" @click="openUserModal">
          <img src="../../assets/img/user.png" alt="회원 모달" width="38" height="38" />
        </button>
        <!-- <button class="logout-btn" @click="logout">LOGOUT</button> -->
      </div>
      <div v-else>
        <button class="login-btn" @click="openModal">LOGIN</button>
        <button class="join-btn" @click="moveJoinMember">JOIN</button>
      </div>
    </div>
    <loginModal v-if="loginModalFlag"></loginModal>
    <userModal :visible.sync="userModalFlag"></userModal>
  </nav>
</template>   

<script>
import "../../assets/css/common.scss";
import constants from "../../lib/constants";
import loginModal from "../../page/user/Login";
import userModal from "./userModal";
export default {
  name: "Header",
  components: {
    loginModal,
    userModal,
  },
  props: ["isHeader"],
  computed: {},
  watch: {},
  created() {},
  methods: {
    logout() {
      this.$store.commit("LOGOUT");
    },
    openModal() {
      this.loginModalFlag = true;
    },
    closeModal() {
      this.loginModalFlag = false;
    },
    // moveUserInfo(){
    //     this.$router.push('/user/Inforamtion')
    // },
    // moveTempBoard(){
    //     this.$router.push('/TempBoardList')
    // },
    moveJoinMember() {
      this.$router.push("/user/Join");
    },
    moveMain() {
      if (this.$route.name == "main") {
        //현재페이지가 메인페이지이면 새로고침
        this.$router.go("/");
      } else {
        //아니면 메인페이지로 이동
        this.$router.push("/");
      }
    },
    onInput() {
      if (this.keyword !== "") {
        this.$router.push({
          name: "BoardSearchList",
          params: { filter: "all", keyword: this.keyword },
        });
      }
    },
    openUserModal() {
      this.userModalFlag = !this.userModalFlag;
    },
    moveSearch() {
      if (this.$route.name == "search") {
        this.$router.go("/search");
      } else {
        this.$router.push("/search");
      }
    },
    moveWrite: function () {
      if (this.$route.name == "BoardCreate") {
        this.$router.go("/boardCreate");
      } else {
        this.$router.push("/boardCreate");
      }
    },
    moveCategory() {
      this.$router.push("/category");
    },
  },
  data: function () {
    return {
      constants,
      keyword: "",
      loginModalFlag: false,
      userModalFlag: false,
    };
  },
};
</script>
<style scoped>
h1 {
  cursor: pointer;
  /* font-family: 'Luckiest Guy', cursive; */
}
.main {
  cursor: pointer;
  display: inline;
}

.menu {
  position: absolute;
  left: 250px;
  top: 25px;
}
button {
  margin-left: 20px;
}
@import url("https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap");
</style>


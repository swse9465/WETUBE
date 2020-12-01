<template>
  <transition name="modal" appear>
    <div class="modal-mask" @click.self="handleWrapperClick">
      <div class="modal-container user" id="login">
        <div class="wrapC table">
          <div class="middle">
            <h1 class="login_h1">LOGIN</h1>
            <div class="input-wrap">
              <input v-model="email" id="email" ref="email" placeholder="Email" type="text" />
            </div>
            <div class="input-wrap">
              <input
                v-model="password"
                type="password"
                id="password"
                ref="password"
                placeholder="Password"
              />
            </div>
            <button class="btn btn--back btn--login" @click="checkEmail">로그인</button>  
            <p>x</p>
            <button class="btn btn--join" @click="moveJoin">회원가입</button>
             <button class="btn--findpwd" @click="moveFindPW">비밀번호 찾기</button>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import "../../assets/css/user.scss";
import constants from "../../lib/constants";
import axios from "axios";
export default {
  components: {},
  created() {},
  watch: {},
  methods: {
    checkEmail() {
      let err = true;
      !this.email && ((err = false), this.$refs.email);
      err && !this.password && ((err = false), this.$refs.password);
      let isContainAt = false;
      let isContainDot = false;
      for (let i = 0; i < this.email.length; i++) {
        const element = this.email[i];
        if (!isContainAt && element == "@") {
          isContainAt = true;
        }
        if (!isContainDot && element == ".") isContainDot = true;
      }
      if (isContainAt && isContainDot) {
        this.login();
      } else {
        alert("이메일 양식을 확인하세요.");
      }
    },
    login() {
    
      axios
        .get(constants.baseUrl+'/account/login', {
          params: {
            email: this.email,
            password: this.password,
          }
        })
        .then((response ) => {
          this.$parent.closeModal();
          this.$store.commit("LOGIN", response.data);
        })
        .catch((error) => {
          console.log(error);
          if(error.response.status==404){
          alert("이메일 혹은 비밀번호를 확인하세요.");
          }else{
            consle.log(error);
          }
        });
    },
    moveFindPW() {
      this.handleWrapperClick();
      this.$router.push("/user/FindPw");
    },
    moveJoin() {
      this.handleWrapperClick();
      this.$router.push("/user/Join");
    },
    handleWrapperClick() {
      this.$parent.closeModal();
    },
  },
  data: () => {
    return {
      constants,
      email: "",
      password: "",
    };
  },
};
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Short+Stack&display=swap');
</style>




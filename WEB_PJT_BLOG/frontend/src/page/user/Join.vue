<template>
  <div class="user" id="join">
    <div class="wrapC table">
      <div class="middle">
        <h1>JOIN</h1>
        <div class="form-wrap">
          <div class="row">
            <p>닉네임</p>
            <div class="col input-wrap">
              <input v-model="nickname" id="nickname" ref="nickname" placeholder="회원님을 나타내는 닉네임을 입력해주세요" type="text" />
            </div>
          </div>
          <div class="row">
            <p>이메일</p>
            <div class="col input-wrap">
              <input v-model="email" id="email" ref="email" placeholder="이메일주소를 입력해주세요" type="text" />
            </div>
          </div>
          <div class="row">
            <p>비밀번호</p>
            <div class="col input-wrap password-wrap">
              <input
                v-model="password"
                id="password"
                ref="password"
                :type="passwordType"
                placeholder="영문, 숫자 혼용 8자 이상"
              />
              <span :class="{active : passwordType==='text'}">
                <i class="fas fa-eye"></i>
              </span>
            </div>
          </div>
          <div class="row">
            <p>비밀번호 확인</p>
            <div class="col input-wrap password-wrap">
              <input
                v-model="passwordConfirm"
                id="passwordConfirm"
                ref="passwordConfirm"
                :type="passwordConfirmType"
                placeholder="비밀번호를 한번더 입력해주세요"
              />
              <span :class="{active : passwordConfirmType==='text'}">
                <i class="fas fa-eye"></i>
              </span>
            </div>
          </div>
        </div>
        <div class="agree">
          <label>
            <input v-model="isTerm" type="checkbox" id="term" />
            <span>약관에 동의합니다</span>
          </label>
          <span class="go-term">약관 보기</span>
        </div>
        <div class="input-wrap wrap-checknumber">
          <input v-model="validNumber" id="validNumber" ref="validNumber" placeholder="인증번호를 입력해주세요" type="text" />
          <button class="btn btn--back btn--checknumber" @click="checkNumber">인증번호 발송</button>
        </div>
        <button class="btn btn-joinsubmit" @click="checkBeforeSubmit">
          <span>회원가입</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import "../../assets/css/user.scss";
import axios from 'axios';
import constants from '../../lib/constants'
export default {
  components: {},
  created() {},
  methods: {
    checkBeforeSubmit() {
      let err = true;
      let msg = "";
      !this.nickname &&
        ((msg = "닉네임 입력해주세요"), (err = false), this.$refs.nickname.focus());
      err &&
        !this.email &&
        ((msg = "이메일 입력해주세요"), (err = false), this.$refs.email.focus());
      err &&
        !this.email &&
        ((msg = "비밀번호 입력해주세요"), (err = false), this.$refs.password.focus());
      err &&
        this.password !== this.passwordConfirm &&
        ((msg = "비밀번호가 다릅니다."), (err = false), this.$refs.passwordConfirm.focus());
      err && 
       !this.isTerm && 
       ((msg = "약관에 동의해주세요."), (err = false), this.$refs.passwordConfirm.focus());
      err &&
        !this.validNumber &&
        ((msg = "인증번호를 입력해주세요."), (err = false), this.$refs.validNumber.focus());


      if (!err) alert(msg);
      else this.submit();
    },
    submit() {
      if(this.validNumber === this.validNumberReturn){
        axios
        .post(constants.baseUrl + '/account/signup', {
          nickname : this.nickname,
          email : this.email,
          password : this.password
        })
        .then(({ data }) => {
          alert("위튜브의 회원이 되신 것을 환영합니다.")
          this.$router.push("/")
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

      } else {
        alert("인증번호를 다시 입력해주세요.")
      }
    },
    checkNumber() {
      if(!this.email){
        alert("이메일을 입력해주세요")
      } else {
            axios
      .get(constants.baseUrl + '/account/memberverify',{
        params: {
           email: this.email,
           nickname: this.nickname,
        },
      })
      .then(({ data }) => { 
        console.log(data)
        this.validNumberReturn = data
        alert("인증번호가 발송 되었습니다.")
      })
      .catch(function (error) {
        if(error.response.status==409){
              if(error.response.data=='email'){
                alert("이메일 중복!! 이미 등록된 이메일입니다.")
              }else if(error.response.data=='nickname'){
                alert("닉네임 중복!! 다른 닉네임을 입력해주세요.")
              }
          }
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
    }

  },
  
  watch: {},
  data: () => {
    return {
      email: "",
      nickname: "",
      password: "",
      passwordConfirm: "",
      level:0,
      isTerm: false,
      passwordType: "password",
      passwordConfirmType: "password",
      validNumber: "",
      validNumberReturn: "",
    };
  }
};
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Short+Stack&display=swap');
</style>



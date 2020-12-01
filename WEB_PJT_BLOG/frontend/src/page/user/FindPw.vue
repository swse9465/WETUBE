<template>
    <div class="user" id="findPw">
        <div class="wrapC table">
            <div class="middle">
                 <h1>FORGOTTEN PASSWORD?</h1>
                 <div class="form-wrap">
                    <div class="row">
                        <p>닉네임</p>
                        <div class="col input-wrap">
                            <input v-model="nickname" id="nickname" ref="nickname" placeholder="닉네임을 입력해주세요" type="text" />
                        </div>
                     </div>
                     <div class="row">
                        <p>이메일</p>
                        <div class="col input-wrap">
                            <input v-model="email" id="email" ref="email" placeholder="이메일을 입력해주세요" type="text" />
                        </div>
                     </div>
                    <div class="input-wrap wrap-sendnumber">
                     <input v-model="validNumber" id="validNumber" ref="validNumber" placeholder="인증번호를 입력해주세요" type="text" />
                     <button class="btn btn--back btn--checknumber" @click="checkNumber">인증번호 발송</button>
                    </div>
                    <button class="btn btn--back btn--findpw" @click="findPw">비밀번호 찾기</button>
                 </div>
            </div>
        </div>
    </div>
</template>

<script>
import "../../assets/css/user.scss";
import constants from "../../lib/constants";
import axios from "axios";
export default {
    components: {},
    created() {
    },
    watch: {},
    methods: {
        findPw() {
            if(this.validNumberReturn === this.validNumber){
                axios
                .get(constants.baseUrl + '/account/findpw?email=' + this.email)
                .then(({ data }) => {
                     alert("찾으시는 비밀번호는" + " [ " + data + " ] " + "입니다.");
                     this.$router.push("/");
                })

            } else {
                alert("인증번호를 다시 입력해주십시오.");
            }
        },
        checkNumber() {
            axios
            .get(constants.baseUrl + '/account/pwdverify?email=' + this.email)
            .then(({ data }) => {
                this.validNumberReturn = data;
                alert("인증번호가 발송 되었습니다.")
            })
        }
    },
    data: () => {
    return {
      nickname: "",
      email: "",
      validNumber: "",
      validNumberReturn: "",
      password: "",
    };
  },

}
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Short+Stack&display=swap');
</style>
import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState  from 'vuex-persistedstate';
Vue.use(Vuex)
import VueMoment from 'vue-moment'
Vue.use(VueMoment);

export default new Vuex.Store({
  state: {
    userId : '',
    nickname : '',
    isLogged : false,
    accesstoken : '',
  },
  getters: {
    getUid : state => {
        return state.userId
    }
  },
  mutations: {
    LOGIN(state, data) {
        state.userId = data.user.userId;
        state.nickname = data.user.nickname;
        state.isLogged = true;
        state.accesstoken = data.accesstoken;
    },
    LOGOUT(state){
        state.userId = '';
        state.nickname = '';
        state.isLogged = false;
        state.accesstoken='';
    }
  },
  actions: {
  },
  plugins : [
    createPersistedState({ storage: window.sessionStorage })
    //로컬스토리지 대신 세션스토리지 사용 : 브라우저 창을 닫으면 자동으로 로그아웃됨 
  ]
})
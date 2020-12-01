import Vue from 'vue'
import Router from 'vue-router'

import constants from '../lib/constants'

// 유저
import Login from '../page/user/Login.vue'
import Join from '../page/user/Join.vue'
import Information from '../page/user/Information'
import FindPw from '../page/user/FindPw'
// 포스트
import Main from '../page/post/Main.vue'
import List from '../page/post/List.vue'
import Category from '../page/post/Category.vue'
import BoardCreate from '../page/post/BoardCreate.vue'
import BoardDetail from '../page/post/BoardDetail.vue'
import TempBoardList from '../page/post/TempBoardList.vue'
// 에러
import PageNotFound from '../page/error/PageNotFound'


Vue.use(Router) 
 
export default new Router({
  mode: 'history',
  routes: [   
    // 로그인/가입
    { 
      path: '/user/login',
      name: constants.URL_TYPE.USER.LOGIN,
      component: Login
    },
    {
      path: '/user/join',
      name: constants.URL_TYPE.USER.JOIN,
      component: Join
    },
    {
      path: '/user/Inforamtion',
      name: constants.URL_TYPE.USER.INFORMATION,
      component: Information
    },
    {
      path: '/user/FindPw',
      name: 'FindPw',
      component: FindPw
    },
    // 포스트
    { 
      path: '/',
      name: constants.URL_TYPE.POST.MAIN,
      component: Main,
    },
    { 
      path: '/list',
      name: constants.URL_TYPE.POST.MAIN,
      component: Main,
    },
    { 
      path: '/search',
      name: 'search',
      component: List,
      props: true
    },
    { 
      path: '/category',
      name: 'category',
      component: Category,
    },
    {
      path: '/board',
      name: 'BoardDetail',
      component: BoardDetail
    },
    {
      path: '/TempBoardList',
      name: 'TempBoardList',
      component: TempBoardList
    },
    // 게시물 생성
    { 
      path: '/boardCreate',
      name: 'BoardCreate',
      component: BoardCreate,
    },
    // 에러
    {
      path:'*',
      redirect:'/404'
    },
    {
      path:'/404',
      component:PageNotFound
    }
  ]
})

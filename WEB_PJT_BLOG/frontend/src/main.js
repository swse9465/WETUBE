import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import scrollMonitor from 'scrollmonitor'
import './assets/sass/main.scss'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

// Install BootstrapVue
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router, 
  components: { App },
  store,
  scrollMonitor,
  template: '<App/>'
})
 
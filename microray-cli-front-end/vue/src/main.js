/* eslint-disable no-new */

import Vue from 'vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import store from './vuex/store.js'
import vStorage from './utils/vStorage.js'
import TreeView from 'vue-json-tree-view'

import ElementUI from 'element-ui'
import './assets/css/theme-mr/index.css'
// Style
import './assets/css/bootstrap.css'
import './assets/css/font-awesome.css'
import './assets/css/animate.css'
import './assets/css/style.css'
import './assets/css/custom.css'
// Js
// import config from '../config'
import './assets/js/jquery-2.1.1'
import './assets/js/bootstrap'
import App from './app.vue'
// Common Components
import registerComponent from './components/registerComponent'
// Directives
import registerDirective from './directives/registerDirective'
import router from './router'

Vue.use(ElementUI)

Vue.use(VueRouter)
Vue.use(vStorage, {
  storageKeyPrefix: 'rtm-guardian'
})
Vue.use(TreeView)
registerComponent(Vue)

registerDirective(Vue)

// Storage
Vue.prototype.$session = window.sessionStorage || {}
Vue.prototype.$store = window.localStorage || {}

// config

Vue.use(VueResource)

Vue.http.headers.common['Access-Control-Allow-Origin'] = '*'
Vue.http.headers.common['Content-Type'] = 'application/json;charset=utf-8'
Vue.http.options.root = '/'

new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: {App}
})

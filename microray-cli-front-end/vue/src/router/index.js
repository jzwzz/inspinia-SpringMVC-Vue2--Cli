import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../vuex/store'
import API from '../config.js'

Vue.use(VueRouter)

let routes = [
  {
    path: '/',
    name: 'main',
    component: resolve => {
      require(['../views/layout.vue'], resolve)
    },
    meta: {requiresAuth: true},
    children: [
      {
        path: '/dashboard',
        name: 'dashboard',
        component: resolve => {
          require(['../views/dashboard.vue'], resolve)
        },
        meta: {requiresAuth: true}
      },
      {
        path: '/todo/list',
        name: 'todo',
        component: resolve => {
          require(['../views/todo/list.vue'], resolve)
        },
        meta: {requiresAuth: true}
      },
      {
        path: '/todo/add',
        name: 'todo',
        component: resolve => {
          require(['../views/todo/add.vue'], resolve)
        },
        meta: {requiresAuth: true}
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: resolve => {
      require(['../views/login.vue'], resolve)
    },
    meta: {requiresAuth: false}
  },
  {
    path: '*',
    redirect: '/login'
  }
]

// vue-router setting
const router = new VueRouter({
  mode: 'hash',
  routes: routes
})

/**
 * 登录状态检查
 * */
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // 未登录状态
    if (!store.state.isLogin) {
      // 存在authorization信息，则验证下。
      if (Vue.$localStorage.authorization) {
        _checkAuth().then(
          function () {
            next()
          },
          function () {
            redirectToCas()
          }
        )
      } else {
        redirectToCas()
      }
    } else {
      _checkAuth().then(
        function () {
          next()
        },
        function () {
          redirectToCas()
        }
      )
    }
  } else {
    next() // 确保一定要调用 next()
  }
})

/**
 * Token验证，只是对时间验证过期否
 * */
function _checkAuth () {
  return new Promise(function (resolve, reject) {
    let authorization = Vue.$localStorage.authorization

    let time = parseInt(authorization.time)
    console.log(authorization)
    if (new Date().getTime() - time < 1000 * 60 * 60 * 2) {
      // token有效,能进入
      if (!store.state.isLogin) {
        let userinfo = Vue.$localStorage.userInfo
        store.commit('setLoginState', true)
        store.commit('updateUserInfo', {
          user: userinfo
        })
        // 设置请求的token
        Vue.http.headers.common['authorization'] =
          'Bearer  ' + authorization.token
      }
      resolve()
    } else {
      Vue.$localStorage.$delete('authorization')
      Vue.$localStorage.$delete('userInfo')
      store.commit('setLoginState', false)
      store.commit('updateUserInfo', {
        user: null
      })
      reject()
    }
  })
}

function redirectToCas () {
  window.location.href = API.casLogin + API.backendCasLogin
}

export default router

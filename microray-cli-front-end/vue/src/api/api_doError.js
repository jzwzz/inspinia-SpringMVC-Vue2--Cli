import Vue from 'vue'
import { Message } from 'element-ui'
import store from '../vuex/store.js'

export const doError = function (code) {
  code = parseInt(code)
  switch (code) {
    case 1002:
      Message({
        message: '服务器内部错误!',
        type: 'error',
        duration: 3000
      })
      return code
    case 101:
      Message({
        message: '该用户不存在!',
        type: 'error',
        duration: 3000
      })
      return code
    case 1005:
      Message({
        message: '数据库错误!',
        type: 'error',
        duration: 3000
      })
      return code
    case 1003:
      Message({
        message: '系统内部错误!',
        type: 'error',
        duration: 3000
      })
      return code
    case 10:
      Message({
        message: 'Token超时,请再登陆!',
        type: 'warning',
        duration: 3000
      })
      // 清空本地数据
      Vue.$localStorage.$delete('authorization')
      Vue.$localStorage.$delete('commentInfo')
      // 修改登录状态
      store.dispatch('setLoginState', false)
      // 跳转
      window.$router.replace({
        name: 'login'
      })
      return 10
    default:
      Message({
        message: '系统内部错误',
        type: 'error',
        duration: 3000
      })
      return code
  }
}

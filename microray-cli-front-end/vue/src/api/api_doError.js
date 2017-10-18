/**
 * Description:统一的错误处理
 * 统一的错误处理方法: 8-数据库查找错误；9-非admin用户；10-token错误或超时（（Token 2h内有效）
 * 其余由api自己处理: 2~5-失败；
 */
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
    case 102:
      Message({
        message: '用户名密码错误!',
        type: 'error',
        duration: 3000
      })
      return code
    case 103:
      Message({
        message: '用户名已离职!',
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

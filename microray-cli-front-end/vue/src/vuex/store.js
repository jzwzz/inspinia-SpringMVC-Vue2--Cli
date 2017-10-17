import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isLogin: false,
    user: '',
    currentRole: '',
    activeSubMenuIndex: ''
  },
  mutations: {
    setLoginState (state, isLogin) {
      state.isLogin = isLogin
    },
    updateUserInfo (state, payload) {
      state.user = payload.user
    },
    setCurrentRole (state, role) {
      state.currentRole = role
      state.activeSubMenuIndex = '1-1'
    },
    setActiveSubMenuIndex (state, index) {
      state.activeSubMenuIndex = index
    }
  }
})

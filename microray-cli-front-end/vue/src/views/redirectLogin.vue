<template>

</template>

<script>
  import { LoginStatus } from '../api/api_auth.js'
  import { mapState, mapMutations } from 'vuex'
  import Role2Menu from './role2Menu.json'
  export default {
    mounted() {
      this.checkLogin()
    },
    methods: {
      ...mapMutations({
        setCurrentRole: 'setCurrentRole',
        setLoginState: 'setLoginState',
        updateUserInfo: 'updateUserInfo'
      }),
      checkLogin() {
        const _this = this
        const token = this.$route.params.token
        if (token) {
          _this.$http.headers.common['authorization'] = 'Bearer ' + token
          LoginStatus(token).then((response) => {
            // 存token
            const user = _this.setRoles(response)
            _this.$localStorage.$set('authorization', {
              token: user.token,
              time: new Date().getTime()
            })
            _this.$localStorage.$set('userInfo', user)
            _this.$http.headers.common['authorization'] = 'Bearer ' + user.token
            _this.setLoginState(true)
            _this.updateUserInfo({
              user: user
            })
            _this.$router.push(user.roles[0].menus[0].subMenus[0].path)
          }, (error) => {
            error.msg && this.$alert(error.msg)
          })
        }
      },
      // get user roles Detail from role2Menu.json
      setRoles(user) {
        user.roles = Role2Menu.filter(function (item) {
          return user.commaDelimitedRoleIds.split(',').indexOf(item.roleId) !== -1
        })
        this.initCurrentRole(user)
        return user
      },
      initCurrentRole(user) {
        if (user && user.roles) {
          this.setCurrentRole(user.roles[0])
          this.$localStorage.$set('currentRole', user.roles[0])
          this.$localStorage.$set('activeSubMenuIndex', '1-1')
        }
      }
    }
  }

</script>

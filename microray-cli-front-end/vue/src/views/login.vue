<style>
  .login-box {
    position: fixed;
    left: 50%;
    top: 40%;
    transform: translate(-50%, -50%);
    min-width: 320px;
  }

  .login-box .login-logo {
    width: 30%;
  }

  .login-box .login-title {
    color: #fff;
    font-size: 18px;
    padding: 20px 0 50px;
    font-family: "Microsoft YaHei";
    font-weight: normal;
  }

  .login-box .input-group .form-control {
    border-left: none;
  }
</style>
<template>
  <div>
    <div class="login-box">
      <div class="middle-box text-center animated fadeInDown">
        <div>
          <div>
            <img class="login-logo" src="../assets/img/logo.png">
          </div>
          <h3 class="login-title">微光场景营销运营平台</h3>
          <form class="m-t" role="form" onsubmit="return false">
            <div class="form-group">
              <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                <input type="text" class="form-control" placeholder="账号" required v-model="credentials.name">
              </div>
            </div>
            <div class="form-group">
              <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                <input type="password" class="form-control" placeholder="密码" required v-model="credentials.password">
              </div>
            </div>
            <button type="submit" class="btn btn-primary block full-width m-b" @click="handleLogin">立即登录</button>
          </form>
        </div>
      </div>

    </div>
    <p class="footer back-bor-null"> &copy; 2017 CmbChina all rights reserved </p>
  </div>
</template>

<script>
  import Vue from 'vue'
  import { Login } from '../api/api_auth.js'
  import { mapState, mapMutations } from 'vuex'
  import Role2Menu from './role2Menu.json'

  export default {
    data () {
      return {
        credentials: {
          name: '',
          password: ''
        }
      }
    },
    methods: {
      ...mapMutations({
        setCurrentRole: 'setCurrentRole',
        setLoginState: 'setLoginState',
        updateUserInfo: 'updateUserInfo'
      }),
      handleLogin () {
        const _this = this
        if (this.credentials.name.trim() === '' || this.credentials.password.trim() === '') {
          this.$message({
            message: '用户名和密码不能为空',
            type: 'error'
          })
        } else {
          Login(this.credentials).then((response) => {
            // 存token
            const user = _this.setRoles(response)
            _this.$localStorage.$set('authorization', {
              token: user.token,
              time: new Date().getTime()
            })
            _this.$localStorage.$set('userInfo', user)
            Vue.http.headers.common['authorization'] = 'Bearer  ' + user.token
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
      setRoles (user) {
        user.roles = Role2Menu.filter(function (item) {
          return user.commaDelimitedRoleIds.split(',').indexOf(item.roleId) !== -1
        })
        this.initCurrentRole(user)
        return user
      },
      initCurrentRole (user) {
        if (user && user.roles) {
          this.setCurrentRole(user.roles[0])
          this.$localStorage.$set('currentRole', user.roles[0])
          this.$localStorage.$set('activeSubMenuIndex', '1-1')
        }
      }
    }
  }
</script>

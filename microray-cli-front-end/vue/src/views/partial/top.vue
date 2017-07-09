<template>
  <div class="row border-bottom">
    <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
      <div class="navbar-header">
        <button type="button" class="navbar-minimalize minimalize-styl-2 btn btn-primary" @click="toggleCanvasMenu">
          <i class="fa fa-bars"></i>
        </button>
  
      </div>
      <div class="pull-left" style="line-height:30px;padding:15px 0 0 10px;font-size:18px;">
        XX平台
      </div>
      <ul class="nav navbar-top-links navbar-right">
        <li>
          <span class="m-r-sm text-muted welcome-message">欢迎您 {{admin.userName}}！</span>
        </li>
        <li>
          <a @click.prevent="logout">
            <i class="fa fa-sign-out"></i> 退出
          </a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
export default {
  props: ['admin'],
  methods: {
    ...mapMutations({
      setCurrentRole: 'setCurrentRole',
      setLoginState: 'setLoginState',
      updateUserInfo: 'updateUserInfo'
    }),
    toggleCanvasMenu: function () {
      window.document.body.classList.toggle('mini-navbar')
    },
    logout: function () {
      // clean everything
      this.$localStorage.$set('authorization', '')
      this.$localStorage.$set('userInfo', '')
      this.$localStorage.$set('currentRole', '')
      this.$localStorage.$set('activeSubMenuIndex', '')
      this.setCurrentRole('')
      this.setLoginState(false)
      this.updateUserInfo('')
      this.$router.push('/login')
    }
  }
}
</script>

<style>
  .nav > li > router-link {
    color: #fff;
  }

  body.mini-navbar .nav-header {
    background: none;
  }
</style>
<template>
  <nav class="navbar-default navbar-static-side" role="navigation">
    <div class="sidebar-collapse">
      <ul class="nav">
        <li class="nav-header">
          <div class="dropdown profile-element">
            <img src="../../assets/img/avatar_large.jpg" class="img-circle" width="60">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <span class="clear">
                <span class="block m-t-xs"><strong
                  class="font-bold">[用户:]{{admin.employeeName}}</strong></span>
              <span class="text-muted text-xs block">{{currentRole ? currentRole.roleName : ''}} <b
                class="caret"></b></span>
              </span>
            </a>
            <ul class="dropdown-menu animated fadeInRight m-t-xs">
              <li v-for="item in admin.roles">
                <a @click='roleChange(item.roleId)'>{{item.roleName}}</a>
              </li>
              <li class="divider"></li>
              <li>
                <router-link to='/logout'>退出</router-link>
              </li>
            </ul>
          </div>
          <div class="logo-element">
            YC
          </div>
        </li>
      </ul>
      <ul v-if='currentMenus' class="nav" v-menu>
        <li v-for="item in currentMenus" :class='{active:menuIndex===item.index}'>
          <a>
            <i :class='item.icon'></i>
            <span class="nav-label">{{item.name}}</span>
            <span v-if='item.subMenus' class="fa arrow"></span>
          </a>
          <ul v-if='item.subMenus' class="nav nav-second-level">
            <li v-for="sub in item.subMenus" :class='{active:subMenuIndex===sub.index}' @click=menuChange(sub.index)>
              <router-link :to="sub.path">
                <i :class='sub.icon'></i>
                <span>{{sub.name}}</span>
              </router-link>
            </li>
          </ul>
        </li>
      </ul>

    </div>
  </nav>
</template>

<script>
  import { mapState, mapMutations } from 'vuex'

  export default {
    props: ['admin'],
    data () {
      return {}
    },
    computed: {
      currentRole: function () {
        if (!this.$store.state.currentRole) {
          return this.$localStorage.currentRole
        }
        return this.$store.state.currentRole
      },
      subMenuIndex: function () {
        if (!this.$store.state.activeSubMenuIndex) {
          return this.$localStorage.activeSubMenuIndex
        }
        return this.$store.state.activeSubMenuIndex
      },
      currentMenus: function () {
        if (this.currentRole) {
          return this.currentRole.menus
        }
      },
      menuIndex: function () {
        if (this.subMenuIndex) {
          return this.subMenuIndex.split('-')[0]
        }
      }
    },
    methods: {
      ...mapMutations({
        setCurrentRole: 'setCurrentRole',
        setActiveSubMenuIndex: 'setActiveSubMenuIndex'
      }),
      roleChange (roleId) {
        const role = this.admin.roles.find(function (item) {
          return item.roleId === roleId
        })
        if (role) {
          this.setCurrentRole(role)
          this.$localStorage.$set('currentRole', role)
          this.$router.push(role.menus[0].subMenus[0].path)
        }
      },
      menuChange (index) {
        if (index.indexOf('-') !== -1) {
          this.setActiveSubMenuIndex(index)
          this.$localStorage.$set('activeSubMenuIndex', index)
        } else {
          this.setActiveSubMenuIndex(index + '-1')
          this.$localStorage.$set('activeSubMenuIndex', index + '-1')
        }
      }
    }
  }
</script>

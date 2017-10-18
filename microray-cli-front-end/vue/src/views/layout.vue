<template>
  <div id="wrapper">

    <sidebar :admin="admin"></sidebar>

    <div id="page-wrapper" class="gray-bg">

      <top :admin="admin"></top>

      <router-view></router-view>

      <bottom></bottom>
    </div>

  </div>
</template>

<script>
  import Top from './partial/top'
  import Sidebar from './partial/sidebar'
  import Bottom from './partial/bottom'
  import Role2Menu from './role2Menu.json'

  export default {
    data: function () {
      return {
        admin: this.$store.state.user
      }
    },
    components: {
      top: Top,
      sidebar: Sidebar,
      bottom: Bottom
    }
  }
</script>

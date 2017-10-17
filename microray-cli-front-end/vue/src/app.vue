<template>
  <div v-cloak>
    <router-view></router-view>
    <loading :show="showLoading"></loading>
  </div>
</template>

<script>
  import Vue from 'vue'

  export default {
    data: function () {
      return {
        showLoading: false
      }
    }
  }
</script>
<style>
  [v-cloak] {
    display: none;
  }
</style>

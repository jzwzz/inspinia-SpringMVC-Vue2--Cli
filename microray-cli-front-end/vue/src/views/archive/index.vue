<template>
  <div>
    <bread-crumb :paths="breadcrumbs" :title="title"></bread-crumb>
    <div class=" wrapper wrapper-content animated fadeInRight">
      <div class="row">
        <div class="col-md-12">
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data: function () {
      return {
        title: '补偿批次管理',
        breadcrumbs: [
          {
            name: '运营平台',
            url: '/'
          },
          {
            name: '批次管理',
            url: '/archive/list'
          }
        ]
      }
    }
  }
</script>

<style>

</style>

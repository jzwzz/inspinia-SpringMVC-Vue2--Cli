<template>

  <div>
    <bread-crumb :paths="breadcrumbs" :title="title"></bread-crumb>

    <div class=" wrapper wrapper-content animated fadeInRight">

      <div class="row">

        <div class="col-md-12">

        </div>

      </div>
    </div>

  </div>
</template>

<script>

  export default {

    data: function () {
      return {
        title: '标题',
        breadcrumbs: [
          {
            name: 'xx面板1',
            url: '/user'
          },
          {
            name: 'Dashboard',
            url: '/dashboard'
          }

        ]
      }
    }

  }
</script>

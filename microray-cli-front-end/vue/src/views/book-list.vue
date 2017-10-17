/* eslint-disable no-new */

<template>
  <table id="book-table">
    <thead>
    <tr>
      <td>序号</td>
      <td>书名</td>
      <td>作者</td>
      <td>ISBN</td>
    </tr>
    </thead>
    <tbody>
    <tr v-for="(item, index) in bookList">
      <td>{{index + 1}}</td>
      <td>{{item.name}}</td>
      <td>{{item.author}}</td>
      <td>{{item.ISBN}}</td>
    </tr>
    </tbody>
  </table>
</template>

<script>
  import config from '../config.js'
  import Vue from 'vue'
  import { GetBookList } from '../api/api_book'

  export default ({
    data: function () {
      return {
        bookList: [{name: '', ISBN: '', author: ''}]
      }
    },
    mounted: function () {
      let _this = this
      GetBookList().then(function (data) {
        _this.bookList = data
      })
    }
  })
</script>

<style>

</style>

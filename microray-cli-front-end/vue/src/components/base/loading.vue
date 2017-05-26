<style>

  .loading-mask {
    position: absolute;
    left: 0;
    top: 0;
    z-index: 99999;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.2);
  }

  .loading-mask > div {
    position: absolute !important;
    top: 50% !important;
    left: 50% !important;
    transform: translate(-50%, -50%);
  }

</style>

<template>
  <transition name="fade">
  <div class="loading-mask"  v-show="show">

    <div class="sk-spinner sk-spinner-rotating-plane"></div>
  </div>
  </transition>
</template>

<script>
  export default {
    props: {
      show: {
        default: false
      }
    }
  }
</script>

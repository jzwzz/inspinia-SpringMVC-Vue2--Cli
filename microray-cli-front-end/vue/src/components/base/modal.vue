<template>
  <transition name="slide-fade" mode="in-out">
    <div class="modal" :class="classes">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" @click="close"><span>&times</span><span class="sr-only">Close</span>
            </button>
            <h4 class="modal-title">{{title}}</h4>
          </div>
          <div v-if="opened">
            <slot></slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>

  export default {
    data() {
      return {
        opened: false
      }
    },
    props: {
      large: Boolean,
      closed: Function,
      classes: {
        type: String,
        default: ''
      },
      title: {
        type: String,
        default: ''
      },
      options: {
        type: Object,
        default: () => { return {} }
      }
    },
    mounted() {
      document.getElementsByTagName('body')[0].appendChild(this.$el)
      this.$element = $(this.$el)
      this.$element.modal($.extend({show: false}, this.options))
      this.$element.on('hide.bs.modal', () => {
        this.opened = false
        if (this.closed) {
          this.closed()
        }
      })
    },
    methods: {
      open() {
        this.opened = true
        this.$element.modal('show')
      },
      close() {
        this.$element.modal('hide')
      }
    }
  }
</script>
<style>
  .slide-fade-enter-active {
    transition: all .3s ease;
  }
  .slide-fade-leave-active {
    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }
  .slide-fade-enter, .slide-fade-leave-active {
    transform: translateX(10px);
    opacity: 0;
  }
</style>

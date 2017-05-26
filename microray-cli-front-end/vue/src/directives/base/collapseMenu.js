/* eslint-disable */
export default {
  twoWay: true,
  priority: 1000,
  params: ['options'],
  bind: function (el) {
    // let defaults = {
    //   toggle: true
    // }
    // let settings = $.extend(defaults, el.params.options)
    let $this = $(el)
    // let $toggle = true

    $this.find('li.active').has('ul').children('ul').addClass('collapse in')
    $this.find('li').not('.active').has('ul').children('ul').addClass('collapse')

    $this.find('li').children('a').on('click' + '.collapseMenu', function (e) {
      if ($(this).siblings('ul').length) {
        e.preventDefault()
      }
      $(this).parent('li').toggleClass('active').children('ul').collapse('toggle')
      // if ($toggle) {
        $(this).parent('li').siblings().removeClass('active').children('ul.in').collapse('hide')
     // }
    })
  },

  update (value) {
    // todo
  },
  unbind (el) {
    $(el).off('.collapseMenu')
  }
}

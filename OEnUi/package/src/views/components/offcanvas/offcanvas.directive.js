;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'offcanvas', offcanvas );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  offcanvas.$inject = [];


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function offcanvas() {
    var directive = {
          restrict     : 'EA',
          link         : link,
          templateUrl  : 'views/components/offcanvas/offcanvas.html',
          controller   : 'offcanvasCtrl',
          controllerAs : 'vm'
        };

    return directive;

    function link( scope, element, attrs ) {
      /**
       * Handling offcanvas collapsing functionality
       * by setting the appropriate classes.
       */

      $('.fn-offcanvasCollapse').on('click', function (event) {
        event.preventDefault();

        $('body').toggleClass('js-offcanvas--collapsed')
        $('.c-menu .c-menu').toggleClass('c-menu--pinned c-menu--floated')
        $('.c-offcanvas .is-tapped').removeClass('is-tapped')
        $('.c-offcanvas .is-visible').removeClass('is-visible')
      });



      /**
       * Reset offcanvas classes for small screen sizes
       * and trigger tapping functionality for touch devices.
       */

      function offcanvasTypeSetter() {
        if ( window.matchMedia('(max-width: 799px)').matches ) {
          $('body').removeClass('js-offcanvas--collapsed');
          $('.c-menu .c-menu--floated').addClass('c-menu--pinned')
          $('.c-menu .c-menu').removeClass('c-menu--floated')
          $('body').addClass('js-offcanvas--out');
        } else {
          $('body').removeClass('js-offcanvas--out');
          $('body').removeClass('js-offcanvas--open');
        }
      }

      offcanvasTypeSetter();

      $(window).bind('resize', function () {
        offcanvasTypeSetter();
      });


      /**
       * Handling offcanvas behavior on small screen
       * devices - setting the offcanvas outside of document.
       */

      $('.fn-offcanvasShow').on('click', function (event) {
        event.preventDefault()
        $('body').toggleClass('js-offcanvas--open')
      });
    }
  }

})();

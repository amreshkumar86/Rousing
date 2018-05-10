;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'sidebar', sidebar );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  sidebar.$inject = [];


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function sidebar() {
    var directive = {
          restrict     : 'EA',
          link         : link,
          templateUrl  : 'views/components/sidebar/sidebar.html',
          controller   : 'sidebarCtrl',
          controllerAs : 'vm'
        };

    return directive;

    function link( scope, element, attrs ) {
      $('.fn-sidebarTrigger').on('click', function ( event ) {
        event.preventDefault();
        $('body').toggleClass('js-sidebar-open')
      });
    }
  }

})();

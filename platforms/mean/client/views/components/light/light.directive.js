;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'light', light );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  light.$inject = [];


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function light() {
    var directive = {
          restrict     : 'EA',
          require      : '?ngModel',
          link         : link,
          templateUrl  : 'views/components/light/light.html',
          controller   : 'lightCtrl',
          controllerAs : 'vm',
          scope: {
              lightData: '='
          }
        };

    return directive;

    function link( scope, element, attrs ) {
      // $('.fn-sidebarTrigger').on('click', function ( event ) {
      //   event.preventDefault();
      //   $('body').toggleClass('js-sidebar-open')
      // });
    }
  }

})();

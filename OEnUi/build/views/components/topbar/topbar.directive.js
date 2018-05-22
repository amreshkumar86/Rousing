;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'topbar', topbar );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  topbar.$inject = [];


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function topbar() {
    var directive = {
          restrict     : 'EA',
          link         : link,
          templateUrl  : 'views/components/topbar/topbar.html',
          controller   : 'topbarCtrl',
          controllerAs : 'vm'
        };

    return directive;

    function link( scope, element, attrs ) {}
  }

})();

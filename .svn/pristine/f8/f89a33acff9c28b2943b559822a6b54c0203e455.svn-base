;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'secondaryNavbar', secondaryNavbar );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  secondaryNavbar.$inject = [];


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function secondaryNavbar() {
    var directive = {
          restrict     : 'EA',
          link         : link,
          templateUrl  : 'views/components/secondary-navbar/secondary-navbar.html',
          controller   : 'secondaryNavbarCtrl',
          controllerAs : 'vm'
        };

    return directive;

    function link( scope, element, attrs ) {}
  }

})();

;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut.email' )
    .directive( 'email', email );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  email.$inject = [];


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function email() {
    var directive = {
          restrict     : 'A',
          link         : link,
          controller   : 'emailCtrl',
          controllerAs : 'vm'
        };

    return directive;

    function link( scope, element, attrs ) {}
  }

})();

;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'content', content );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  content.$inject = [];


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function content() {
    var directive = {
          restrict     : 'EA',
          link         : link,
          controller   : 'contentCtrl',
          controllerAs : 'vm'
        };

    return directive;

    function link( scope, element, attrs ) {}
  }

})();

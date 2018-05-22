;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'pageHeader', pageHeader );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  pageHeader.$inject = [];


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function pageHeader() {
    var directive = {
          restrict     : 'EA',
          link         : link,
          templateUrl  : 'views/components/page-header/page-header.html',
          controller   : 'headerCtrl',
          controllerAs : 'vm'
        };

    return directive;

    function link( scope, element, attrs ) {}
  }

})();

;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'sortable', sortable );


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function sortable() {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    function link( scope, element, attrs ) {
      $( element ).sortable( scope.$eval( attrs.sortable ) );
    }
  }

})();

;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'datatable', datatable );


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function datatable() {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    function link( scope, element, attrs ) {
      $( element ).dataTable(  scope.$eval(  attrs.datatable  )  );
    }
  }


})();

;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'masonry', masonry );


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function masonry() {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    function link( scope, element, attrs ) {
      $( element ).masonry( scope.$eval( attrs.masonry ) );
    }
  }

})();

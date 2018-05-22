;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'ionRangeSlider', ionRangeSlider );


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function ionRangeSlider() {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    function link( scope, element, attrs ) {
      $( element ).ionRangeSlider( scope.$eval( attrs.ionRangeSlider ) );
    }
  }

})();

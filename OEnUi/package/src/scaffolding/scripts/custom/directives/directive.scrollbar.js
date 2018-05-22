;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut' )
    .directive( 'perfectScrollbar', perfectScrollbar );


  /**
   * ====================
   * Dependancy Injection
   * ====================
   */

  perfectScrollbar.$inject = [ 'IsMobile' ];


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function perfectScrollbar( IsMobile ) {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    function link( scope, element, attrs ) {
      var isMobile = IsMobile.any;
      !isMobile && $( element ).perfectScrollbar( scope.$eval( attrs.perfectScrollbar ) );
    }
  }

})();

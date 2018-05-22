;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module('ponut')
    .directive('fullCalendar', fullCalendar);


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function fullCalendar() {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    function link(scope, element, attrs) {
      $(element).fullCalendar( scope.$eval( attrs.fullCalendar ) );
    }
  }


})();

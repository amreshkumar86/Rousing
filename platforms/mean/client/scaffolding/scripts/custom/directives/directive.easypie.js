;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module('ponut')
    .directive('easyPieChart', easyPieChart);


  /**
   * ===============
   * Directive Setup
   * ===============
   */

  function easyPieChart() {
    var directive = {
          restrict : 'A',
          link     : link
        };

    return directive;

    function link(scope, element, attrs) {
      $(element).easyPieChart( scope.$eval( attrs.easyPieChart ) );
    }
  }


})();

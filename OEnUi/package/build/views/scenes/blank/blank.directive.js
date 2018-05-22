;(function () {

  'use strict';

  /**
   * =====================
   * Registering Directive
   * =====================
   */

  angular
    .module( 'ponut.blank' )
    .directive('blank', blank)


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  blank.$inject = [ 'BlankService' ];

  /**
   * ===============
   * Directive Setup
   * ===============
   */

   function blank(BlankService) {
     return {
       restrict: 'EA',
       templateUrl: 'views/scenes/blank/login.html',
       replace: true,
       link: link
     };

     function link($scope, $element) {
       $scope.BlankService = new BlankService($scope);
     }
   }

})();

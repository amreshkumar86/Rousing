;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut' )
    .controller( 'headerCtrl', headerCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  headerCtrl.$inject = ['$scope','$rootScope'];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   */

  function headerCtrl($scope,$rootScope) {
    var vm = this;
    $scope.refresh = function() {
    
        $rootScope.$emit("CallParentMethod", {});
    }
}
  

})();

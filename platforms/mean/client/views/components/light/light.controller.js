;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut' )
    .controller( 'lightCtrl', lightCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  lightCtrl.$inject = ['$scope'];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   */

  function lightCtrl($scope) {
    var vm = this;
  }

})();

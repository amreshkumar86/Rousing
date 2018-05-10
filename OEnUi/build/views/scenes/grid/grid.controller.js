;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.grid' )
    .controller( 'gridCtrl', gridCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  gridCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function gridCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Grid';

    var vm = this;
  }

})();

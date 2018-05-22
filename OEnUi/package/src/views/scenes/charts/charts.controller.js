;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.charts' )
    .controller( 'chartsCtrl', chartsCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  chartsCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function chartsCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Charts';

    var vm = this;
  }

})();

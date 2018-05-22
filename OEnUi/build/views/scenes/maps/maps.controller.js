;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.maps' )
    .controller( 'mapsCtrl', mapsCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  mapsCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function mapsCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Maps';

    var vm = this;
  }

})();

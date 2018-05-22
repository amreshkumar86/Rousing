;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.portlets' )
    .controller( 'portletsCtrl', portletsCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  portletsCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function portletsCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Portlets';

    var vm = this;
  }

})();

;(function () {

  'use strict';


  /**
   * ======================
   * Registering Controller
   * ======================
   */

  angular
    .module( 'ponut.buttons' )
    .controller( 'buttonsCtrl', buttonsCtrl );


  /**
   * ===================
   * Dependacy Injection
   * ===================
   */

  buttonsCtrl.$inject = [ '$rootScope' ];


  /**
   * =========================
   * Controller Function Setup
   * =========================
   *
   * [1] : Defining page title.
   *
   */

  function buttonsCtrl( $rootScope ) {
    // [1]
    $rootScope.title = 'Buttons';

    var vm = this;
  }

})();
